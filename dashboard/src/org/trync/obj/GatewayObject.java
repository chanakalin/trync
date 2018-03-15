package org.trync.obj;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.jboss.logging.Logger;
import org.trync.util.DBConnection;

public class GatewayObject {
	private static final Logger LOGGER = Logger.getLogger(GatewayObject.class);

	private int gwid = 0;
	private String gwname = "";
	private String accname = "";
	private String connectionIp = "";
	private String connectionInterfaces = "";
	private String displayName = "";
	private String serialNumber = "";
	private String modelId = "";
	private boolean status = false;

	// gwid
	public void setGwId(int gwid) {
		this.gwid = gwid;
	}

	public int getGwId() {
		return this.gwid;
	}

	// gwname
	public void setGwName(String gwname) {
		this.gwname = gwname;
	}

	public String getGwName() {
		return this.gwname;
	}

	// accname
	public void setAccName(String accname) {
		this.accname = accname;
	}

	public String getAccName() {
		return this.accname;
	}

	// connectionIp
	public void setConnectionIp(String connectionIp) {
		this.connectionIp = connectionIp;
	}

	public String getConnectionIp() {
		return this.connectionIp;
	}

	// connectionInterfaces
	public void setConnectionInterfaces(String connectionInterfaces) {
		this.connectionInterfaces = connectionInterfaces;
	}

	public String getConnectionInterfaces() {
		return this.connectionInterfaces;
	}

	// diaplayName
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return this.displayName;
	}

	// serialNumber
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getSerialNumber() {
		return this.serialNumber;
	}

	// modelId
	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getModelId() {
		return this.modelId;
	}

	// status
	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean getStatus() {
		return this.status;
	}

	// construct
	public GatewayObject() {
	}

	// with id
	public GatewayObject(int gwid) {
		DBConnection dbcon = new DBConnection();
		try {
			CallableStatement funCall = dbcon.getConnection().prepareCall("{ ? = call fn_mst_gw_get( ? , ? , ? ) }");
			funCall.setInt(1, gwid);
			funCall.setString(2, null);
			funCall.setBoolean(3, true);
			funCall.registerOutParameter(4, Types.OTHER);
			funCall.execute();
			ResultSet results = (ResultSet) funCall.getObject(4);
			while (results.next()) {
				setGwId(results.getInt("gwid"));
				setGwName(results.getString("gwname"));
				setDisplayName(results.getString("display_name"));
				setAccName(results.getString("accname"));
				setConnectionIp(results.getString("connection_ip"));
				setConnectionInterfaces(results.getString("connection_interface"));
				setSerialNumber(results.getString("serial_number"));
				setModelId(results.getString("model_id"));
				setStatus(results.getBoolean("status"));
			}
			results.close();
			funCall.close();
			dbcon.close();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
	}
	//with name
	public GatewayObject(String gwname) {
		DBConnection dbcon = new DBConnection();
		try {
			CallableStatement funCall = dbcon.getConnection().prepareCall("{ ? = call fn_mst_gw_get( ? , ? , ? ) }");
			funCall.setInt(1, 0);//will not considor
			funCall.setString(2, gwname);
			funCall.setBoolean(3, true);
			funCall.registerOutParameter(4, Types.OTHER);
			funCall.execute();
			ResultSet results = (ResultSet) funCall.getObject(4);
			while (results.next()) {
				setGwId(results.getInt("gwid"));
				setGwName(results.getString("gwname"));
				setDisplayName(results.getString("display_name"));
				setAccName(results.getString("accname"));
				setConnectionIp(results.getString("connection_ip"));
				setConnectionInterfaces(results.getString("connection_interface"));
				setSerialNumber(results.getString("serial_number"));
				setModelId(results.getString("model_id"));
				setStatus(results.getBoolean("status"));
			}
			results.close();
			funCall.close();
			dbcon.close();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
			LOGGER.error(e.getCause());
			LOGGER.error(e.getSQLState());
		}
	}

	// birth
	public boolean giveBirth() {
		boolean retBool = false;
		//
		DBConnection dbcon = new DBConnection();
		try {
			PreparedStatement funCall = dbcon.getConnection()
					.prepareStatement("SELECT fn_mst_gw_birth( ? , ? , ? , ? , ? , ? , ? ) AS gwid");
			funCall.setString(1, getGwName());
			funCall.setString(2, getAccName());
			funCall.setString(3, getDisplayName());
			funCall.setString(4, getConnectionIp());
			funCall.setString(5, getConnectionInterfaces());
			funCall.setString(6, getSerialNumber());
			funCall.setString(7, getModelId());
			ResultSet results = funCall.executeQuery();
			while (results.next()) {
				setGwId(results.getInt("gwid"));
			}
			results.close();
			funCall.close();
			dbcon.commit();
			dbcon.close();

			if (getGwId() > 0) {
				LOGGER.info("Gateway birth : displayname : " + getDisplayName() + ", accname : " + getAccName());
				retBool = true;// return success
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}

		// return
		return retBool;
	}
	
	//update
	public boolean update() {
		boolean retBool = false;
		//
		DBConnection dbcon = new DBConnection();
		try {
			PreparedStatement funCall = dbcon.getConnection()
					.prepareStatement("SELECT fn_mst_gw_update( ? , ? , ? , ? , ? , ? ) AS gwid");
			funCall.setInt(1, getGwId());
			funCall.setString(2, getConnectionIp());
			funCall.setString(3, getConnectionInterfaces());
			funCall.setString(4, getSerialNumber());
			funCall.setString(5, getModelId());
			funCall.setBoolean(6, getStatus());
			ResultSet results = funCall.executeQuery();
			while (results.next()) {
				setGwId(results.getInt("gwid"));
			}
			results.close();
			funCall.close();
			dbcon.commit();
			dbcon.close();

			if (getGwId() > 0) {
				LOGGER.info("Gateway updated : displayname : " + getDisplayName() + ", gatewayname : " + getDisplayName());
				retBool = true;// return success
			}
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}

		// return
		return retBool;
	}

}
