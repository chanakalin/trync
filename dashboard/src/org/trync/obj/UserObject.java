package org.trync.obj;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import org.jboss.logging.Logger;
import org.trync.util.DBConnection;

public class UserObject {
	private static final Logger LOGGER = Logger.getLogger(UserObject.class);

	private String errorMsg = "";
	private boolean isNewUser = true;

	private int uid = 0;
	private String email = "";
	private String fullname = "";
	private int gid = 0;
	private String password = "";
	private boolean enabled = false;

	/**
	 * Beans start
	 */
	// uid
	public int getUid() {
		return this.uid;
	}

	public void setUid(int uid) {
		if (isNewUser) {
			this.uid = uid;
		}
	}

	// email
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		if (isNewUser) {
			this.email = email;
		}
	}

	// fullname
	public String getFullname() {
		return this.fullname;
	}

	public void setFname(String fullname) {
		this.fullname = fullname;
	}

	// gid
	public int getGid() {
		return this.gid;
	}

	public void setGid(int gid) {
		this.gid = gid;
	}

	// enabled
	public boolean getEnabled() {
		return this.enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	// password
	public void setPassword(String password) {
		this.password = password;
	}

	// error
	public String getError() {
		return this.errorMsg;
	}

	/**
	 * Beans end
	 */

	// new user
	public UserObject() {
	}

	// existing user
	public UserObject(int uid) {
		isNewUser = false;
		DBConnection dbcon = new DBConnection();
		try {
			CallableStatement funCall = dbcon.getConnection().prepareCall("{ ? = call fn_mst_user_get( ? ) }");
			funCall.setInt(1, uid);
			funCall.registerOutParameter(2, Types.OTHER);
			funCall.execute();
			ResultSet results = (ResultSet) funCall.getObject(2);
			while (results.next()) {
				this.uid = results.getInt(1);
				this.fullname = results.getString(2);
				this.email = results.getString(3);
				this.gid = results.getInt(4);
				this.enabled = results.getBoolean(7);
				this.isNewUser = false;
			}
			results.close();
			funCall.close();
			dbcon.close();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}
	}

	// login
	public boolean login(String email, String password) {
		boolean retval = false;

		DBConnection dbcon = new DBConnection();
		try {
			CallableStatement funCall = dbcon.getConnection().prepareCall("{ ? = call fn_mst_user_login( ? , ? ) }");
			funCall.setString(1, email);
			funCall.setString(2, password);
			funCall.registerOutParameter(3, Types.OTHER);
			funCall.execute();
			ResultSet results = (ResultSet) funCall.getObject(3);
			if (results != null) {
				while (results.next()) {
					this.uid = results.getInt(1);
					this.fullname = results.getString(2);
					this.email = results.getString(3);
					this.gid = results.getInt(4);
					this.enabled = results.getBoolean(7);
					this.isNewUser = false;
					retval = true;// logged successfully
				}
				results.close();
			}
			funCall.close();
			dbcon.commit();
			dbcon.close();
		} catch (SQLException e) {
			LOGGER.error(e.getMessage());
		}

		// return
		return retval;
	}

	public boolean save(int updatingUserUID) {
		boolean retBool = false;

		// if new
		if (isNewUser) {// add new user
			DBConnection dbcon = new DBConnection();
			try {
				PreparedStatement funCall = dbcon.getConnection()
						.prepareStatement("SELECT fn_mst_user_create( ? , ? , CAST( ? AS chkpass) , ? , ? ) AS newuid");
				// funCall.registerOutParameter(1, Types.OTHER);
				funCall.setString(1, this.email);
				funCall.setString(2, this.fullname);
				funCall.setString(3, this.password);
				funCall.setInt(4, this.gid);
				funCall.setInt(5, updatingUserUID);
				ResultSet results = funCall.executeQuery();
				while (results.next()) {
					uid = results.getInt("newuid");
				}
				results.close();
				funCall.close();
				dbcon.commit();
				dbcon.close();

				if (uid > 0) {
					LOGGER.info("Created " + email + " with UID " + uid);
					retBool = true;// return success
				}
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
				errorMsg = e.getMessage();
			}
		} else {// update user
			DBConnection dbcon = new DBConnection();
			try {
				PreparedStatement funCall = dbcon.getConnection()
						.prepareStatement("SELECT fn_mst_user_update( ? , ? , ? , ? , ? , ?)");
				funCall.setInt(1, this.uid);
				funCall.setString(2, this.fullname);
				funCall.setString(3, this.password);
				funCall.setInt(4, this.gid);
				funCall.setBoolean(5, this.enabled);
				funCall.setInt(6, updatingUserUID);
				if (funCall.execute()) {
					LOGGER.info("Updated UID " + uid);
					funCall.close();
					dbcon.commit();
					dbcon.close();
					retBool = true;// return success
				}
			} catch (SQLException e) {
				LOGGER.error(e.getMessage());
				errorMsg = e.getMessage();
			}
		}
		// return
		return retBool;
	}

}
