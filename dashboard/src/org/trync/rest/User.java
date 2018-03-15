package org.trync.rest;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jboss.logging.Logger;
import org.trync.obj.UserObject;

import javax.ws.rs.core.MediaType;

@Path("/user")
public class User {
	@Context
	private HttpServletRequest httpRequest;
	private static final Logger LOGGER = Logger.getLogger(User.class);

	// user login
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("login")
	public Response login(String json) {
		JSONObject userCredential = new JSONObject();
		JSONObject loggedUser = new JSONObject();
		org.trync.obj.UserObject loginUser = new org.trync.obj.UserObject();

		try {
			userCredential = new JSONObject(json);
			if (loginUser.login(userCredential.getString("email"), userCredential.getString("password"))) {
				httpRequest.getSession().setAttribute("uid", String.valueOf(loginUser.getUid()));
				httpRequest.getSession().setAttribute("gid", String.valueOf(loginUser.getGid()));
				// if enabled
				if (loginUser.getEnabled()) {
					loggedUser.append("uid", loginUser.getUid());
					loggedUser.append("gid", String.valueOf(loginUser.getGid()));
					loggedUser.append("fullname", loginUser.getFullname());
					return Response.ok(loggedUser.toString()).build();
				} else {// disabled
					loggedUser.append("error", "Your account has been disabled");
					return Response.status(401).entity(loggedUser.toString()).build();
				}
			} else {// error in login
				loggedUser.append("error", "Invalid email or password");
				return Response.status(401).entity(loggedUser.toString()).build();
			}

		} catch (JSONException e) {
			LOGGER.error(e.getMessage());
		}

		return Response.ok().build();
	}

	// check is logged using session
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("islogged")
	public Response isUserLogged() {
		JSONObject loggedUser = new JSONObject();
		String strUid = "";
		if ((strUid = (String) httpRequest.getSession().getAttribute("uid")) != null) {
			UserObject curUser = new UserObject(Integer.valueOf(strUid));
			if (curUser.getEnabled()) {
				try {
					loggedUser.append("uid", curUser.getUid());
					loggedUser.append("gid", curUser.getGid());
					loggedUser.append("fullname", curUser.getFullname());
				} catch (JSONException e) {
					LOGGER.error(e.getMessage());
				}
			} else {
				try {
					loggedUser.append("uid", 0);
				} catch (JSONException e) {
					LOGGER.error(e.getMessage());
				}
			}
		}
		return Response.ok(loggedUser.toString()).build();
	}

	// logout
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("logout")
	public Response userLogout() {
		JSONObject logoutUser = new JSONObject();
		httpRequest.getSession().removeAttribute("uid");
		httpRequest.getSession().removeAttribute("gid");
		httpRequest.getSession().invalidate();
		try {
			logoutUser.append("logout", "success");
		} catch (JSONException e) {
			LOGGER.error(e.getMessage());
		}
		return Response.ok(logoutUser.toString()).build();
	}

	// update current users password
	@PUT
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("updatepassword")
	public Response updateUserPassword(String json) {
		// permission check
		String updaterUid = (String) httpRequest.getSession().getAttribute("uid");
		String updaterUlevel = (String) httpRequest.getSession().getAttribute("ulevel");
		JSONObject user = new JSONObject();
		JSONObject newUser = null;
		// if userid > 0
		if (Integer.parseInt(updaterUid) > 0) {
			try {
				// create and fetch
				newUser = new JSONObject(json);
				UserObject updUser = new UserObject(Integer.valueOf(updaterUid));
				try {
					updUser.setPassword(newUser.getString("password"));
					// save user
					if (updUser.save(Integer.parseInt(updaterUid))) {// save
																		// with
																		// admins
																		// uid
						newUser.append("uid", updUser.getUid());
						return Response.ok(newUser.toString()).build();
					} else {
						newUser = new JSONObject();
						newUser.append("error", updUser.getError());
						return Response.status(401).entity(updUser.getError()).build();
					}

				} catch (JSONException e) {
					LOGGER.error(e.getMessage());
				}
			} catch (JSONException e1) {
				LOGGER.error(e1.getMessage());
			}

		}
		// return JSON Object
		return Response.ok(user.toString()).build();
	}

}
