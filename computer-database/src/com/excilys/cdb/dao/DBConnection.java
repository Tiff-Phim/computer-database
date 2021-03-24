package com.excilys.cdb.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zaxxer.hikari.HikariDataSource;

/**
 * DBConnection class defines a single connection to the database.
 * 
 * @author Tiffany PHIMMASANE
 * @version 0.3
 */
public class DBConnection {
	
	private static final String PROP_FILE_NAME = "config/db.properties";
	private static final String PROPERTY_URL = "db.url";
	private static final String PROPERTY_USER = "db.user";
	private static final String PROPERTY_PASSWORD = "db.password";
	private static final String PROPERTY_DRIVER = "db.driver";

	private static DBConnection instance = new DBConnection();
	private static Logger logger = LoggerFactory.getLogger(DBConnection.class);
	
	private HikariDataSource dataSource = new HikariDataSource();

	/**
	 * Default DBConnection constructor.
	 */
	private DBConnection() {		
		try {
			Properties prop = new Properties();
			InputStream stream = this.getClass().getClassLoader().getResourceAsStream(PROP_FILE_NAME);
			prop.load(stream);
			dataSource.setDriverClassName(prop.getProperty(PROPERTY_DRIVER));
			dataSource.setJdbcUrl(prop.getProperty(PROPERTY_URL));
			dataSource.setUsername(prop.getProperty(PROPERTY_USER));
			dataSource.setPassword(prop.getProperty(PROPERTY_PASSWORD));
		} catch (IOException e) {
			logger.error("Couldn't connect to database.", e);
		}
	}

	/**
	 * Fetches the unique instance of DBConnection.
	 * 
	 * @return instance of DBConnection (unique)
	 */
	public static synchronized DBConnection getInstance() {
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
		return dataSource.getConnection();
	}
}