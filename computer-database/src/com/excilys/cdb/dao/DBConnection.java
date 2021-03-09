package com.excilys.cdb.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * DBConnection class defines a single connection to the database.
 * 
 * @author Tiffany PHIMMASANE
 * @version 0.1
 */
public class DBConnection {

	private static final String PROP_FILE_NAME = "resources/config/db.properties";
	private static final String PROPERTY_URL = "db.url";
	private static final String PROPERTY_USER = "db.user";
	private static final String PROPERTY_PASSWORD = "db.password";

	private static DBConnection instance = new DBConnection();

	private String url, user, pw;

	/**
	 * Default DBConnection constructor.
	 */
	private DBConnection() {
		try {
			Properties prop = new Properties();
			InputStream stream = this.getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME);
			prop.load(stream);
			this.url = prop.getProperty(PROPERTY_URL);
			this.user = prop.getProperty(PROPERTY_USER);
			this.pw = prop.getProperty(PROPERTY_PASSWORD);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fetches the unique instance of DBConnection.
	 * 
	 * @return instance of DBConnection (unique)
	 */
	synchronized public static DBConnection getInstance() {
		if (instance == null) {
			instance = new DBConnection();
		}
		return instance;
	}

	/**
	 * Fetches the connection to database.
	 * 
	 * @return con the connection to the database
	 * @throws SQLException thrown if connection fails
	 */
	public Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url, user, pw);
	}
	
}