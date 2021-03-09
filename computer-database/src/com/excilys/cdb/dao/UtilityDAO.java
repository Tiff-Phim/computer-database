package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * DAO utility class defines attributs and methods used by DAO classes.
 * 
 * @author Tiffany PHIMMASANE
 * @version 0.1
 */
public final class UtilityDAO {
	
	private static final String FAIL_CLOSE_CONNECTION = "Echec de la fermeture de la connexion.";
	private static final String FAIL_CLOSE_STATEMENT = "Echec de la fermeture du Statement.";
	private static final String FAIL_CLOSE_RESULTSET = "Echec de la fermeture du ResultSet.";
	
	protected static final String FAIL_FIND_OBJECT = "Echec de la recherche de l'objet.";
	protected static final String FAIL_FIND_OBJECT_BY_ID = "Echec de la recherche de l'objet par id.";
	
	private static Logger logger = Logger.getLogger(UtilityDAO.class.getName());

	/**
	 * Initialization of a prepared statement.
	 * 
	 * @param sql the sql request
	 * @param objects the list of objects to be inserted in the query
	 * @return preparedStatement the initialized prepared statement
	 */
	protected static String initializationPreparedStatement(String sql, Object... objects) {
		String[] listeSQL = (sql + " ").split("\\?");
		StringBuilder newSQL = new StringBuilder(listeSQL[0]);
		for (int i = 0; i < objects.length; i++) {
			newSQL.append("\"" + objects[i] + "\"" + listeSQL[i + 1]);
		}
		return newSQL.toString().replaceAll("\"null\"", "null");
	}

	/**
	 * Closes the connection to database.
	 * 
	 * @param connection to close
	 */
	protected static void close(Connection connection) {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				logger.log(Level.WARNING, FAIL_CLOSE_CONNECTION + e.getMessage(), e);
			}
		}
	}

	/**
	 * Closes the statement to database.
	 * 
	 * @param statement to close
	 */
	protected static void close(Statement statement) {
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				logger.log(Level.WARNING, FAIL_CLOSE_STATEMENT + e.getMessage(), e);
			}
		}
	}

	/**
	 * Closes the resultSet to database.
	 * 
	 * @param resultSet to close
	 */
	protected static void close(ResultSet resultSet) {
		if (resultSet != null) {
			try {
				resultSet.close();
			} catch (SQLException e) {
				logger.log(Level.WARNING, FAIL_CLOSE_RESULTSET + e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 
	 * @param statement
	 * @param connection
	 */
	protected static void close(Statement statement, Connection connection) {
		close(statement);
		close(connection);
	}
	
	/**
	 * 
	 * @param resultSet
	 * @param statement
	 * @param connection
	 */
	protected static void close(ResultSet resultSet, Statement statement, Connection connection) {
		close(resultSet);
		close(statement);
		close(connection);
	}
}