package org.trync.rest;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.logging.Logger;
import org.trync.util.DBConnection;

@Path("/gateway")
public class Gateway {
	@Context
	private HttpServletRequest httpRequest;
	private static final Logger LOGGER = Logger.getLogger(Gateway.class);

	// load gateway list
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("")
	public Response fetchGatewayList() {
		JSONArray gatewayArray = new JSONArray();
		JSONObject gatewayObject = new JSONObject();
		String strGid = "";
		if ((strGid = (String) httpRequest.getSession().getAttribute("gid")) != null) {
			if (Integer.valueOf(strGid) < 3) {// only admin and super admin
				DBConnection dbcon = new DBConnection();
				try {
					CallableStatement funCall = dbcon.getConnection()
							.prepareCall("{ ? = call fn_mst_gw_get() }");
					funCall.registerOutParameter(1, Types.OTHER);
					funCall.execute();
					ResultSet results = (ResultSet) funCall.getObject(1);
					while (results.next()) {
						JSONObject currentGateway = new JSONObject();
						currentGateway.append("gwid",results.getInt("gwid"));
						currentGateway.append("gwname",results.getString("gwname"));
						currentGateway.append("display_name",results.getString("display_name"));
						currentGateway.append("accname",results.getString("accname"));
						currentGateway.append("connection_ip",results.getString("connection_ip"));
						currentGateway.append("connection_interface",results.getString("connection_interface"));
						currentGateway.append("serial_number",results.getString("serial_number"));
						currentGateway.append("model_id",results.getString("model_id"));
						currentGateway.append("status",results.getBoolean("status"));
						gatewayArray.put(currentGateway);
					}
					//append to gateways object
					gatewayObject.append("gateways", gatewayArray);
					results.close();
					funCall.close();
					dbcon.close();
				} catch (SQLException | JSONException e) {
					LOGGER.error(e.getMessage());
					LOGGER.error(e.getCause());
				}
			}else {
				try {
					gatewayObject.append("error", "You are not autherized");
					return Response.status(401).entity(gatewayObject.toString()).build();
				} catch (JSONException e) {
					LOGGER.error(e.getMessage());
				}
			}

		} else {
			try {
				gatewayObject.append("error", "You are not autherized");
				return Response.status(401).entity(gatewayObject.toString()).build();
			} catch (JSONException e) {
				LOGGER.error(e.getMessage());
			}
		}
		return Response.ok(gatewayObject.toString()).build();
	}

}
