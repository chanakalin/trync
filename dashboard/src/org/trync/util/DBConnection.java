package org.trync.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DBConnection {

	private InitialContext intialcontext = null;
	private DataSource datasource = null;
	private Connection connection = null;
	
	private String dataSourceName = "TryncPoolDs";

	//super
	public DBConnection(){
		try {
			intialcontext = new InitialContext();
			//System.out.println("Opening Datasource " +dataSourceName);
			datasource = (DataSource) intialcontext.lookup(dataSourceName);
		} catch (NamingException e) {
			e.printStackTrace();
		}
		try {
			connection = datasource.getConnection();
			connection.setAutoCommit(false); //default auto commit false
		} catch (Exception err) {
			err.printStackTrace();
		}
	}
	
	//commit
	public void commit(){
		try {
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//close
	public void close() throws SQLException{
		//System.out.println("Closing database connection from datasource");
		connection.close();
	}
	
	//autocommit
	public void setAutoCommit(boolean autocommit){
		try {
			connection.setAutoCommit(autocommit);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//get connection
	public Connection getConnection(){
		return connection;
	}
}
