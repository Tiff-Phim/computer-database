package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.excilys.cdb.model.Computer;

public class ComputerDAOImpl implements ComputerDAO {

	private static final String ATTRIBUT_ID = "id";
	private static final String ATTRIBUT_NAME = "name";
	private static final String ATTRIBUT_INTRODUCED = "introduced";
	private static final String ATTRIBUT_DISCONTINUED = "discontinued";
	private static final String ATTRIBUT_COMPANY_ID = "company_id";

	private static final String SQL_GET_ALL_COMPUTERS = "SELECT * FROM computer";
	private static final String SQL_GET_COMPUTER_BY_ID = "SELECT (computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id)"
			+ "FROM computer LEFT JOIN company ON company.id = computer.company_id WHERE computer.id=?";
	private static final String SQL_CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE_COMPUTER_BY_ID = "UPDATE computer SET company_id=? WHERE computer.id=?";
	private static final String SQL_DELETE_COMPUTER_BY_ID = "DELETE FROM computer WHERE id=?";

	private static Logger logger = Logger.getLogger(ComputerDAOImpl.class.getName());

	/**
	 * Makes the correspondence between a line from the database table (a ResultSet)
	 * and a Company bean.
	 * 
	 * @param resultSet line from the database table
	 * @return bean of the Company
	 * @throws SQLException
	 */
	protected Computer getComputer(ResultSet resultSet) throws SQLException {
		Computer computer = new Computer();
		computer.setId(resultSet.getInt(ATTRIBUT_ID));
		computer.setName(resultSet.getString(ATTRIBUT_NAME));
		if (!resultSet.getString(ATTRIBUT_INTRODUCED).isEmpty() || !resultSet.getString(ATTRIBUT_DISCONTINUED).isEmpty()) {
			//TODO
			computer.setIntroduced(LocalDate.parse(resultSet.getString(ATTRIBUT_INTRODUCED)));
			computer.setDiscontinued(LocalDate.parse(resultSet.getString(ATTRIBUT_DISCONTINUED)));
		}
		computer.setCompanyId(resultSet.getInt(ATTRIBUT_COMPANY_ID));
		return computer;
	}

	@Override
	public List<Computer> findAllComputers() {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		List<Computer> computerList = new ArrayList<>();
		try {
			connection = DBConnection.getInstance().getConnection();
			preparedStatement = connection.prepareStatement(SQL_GET_ALL_COMPUTERS);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				computerList.add(getComputer(resultSet));
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, UtilityDAO.FAIL_FIND_OBJECT, e);
		} finally {
			UtilityDAO.close(resultSet, preparedStatement, connection);
		}
		return computerList;
	}

	@Override
	public Computer findComputerById(long computerId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		Computer computer = null;
		try {
			connection = DBConnection.getInstance().getConnection();
			preparedStatement = connection.prepareStatement(
					UtilityDAO.initializationPreparedStatement(SQL_GET_COMPUTER_BY_ID, String.valueOf(computerId)),
					Statement.RETURN_GENERATED_KEYS);
			resultSet = preparedStatement.executeQuery();
			resultSet.next();
			computer = getComputer(resultSet);
		} catch (SQLException e) {
			logger.log(Level.WARNING, UtilityDAO.FAIL_FIND_OBJECT_BY_ID, e);
		} finally {
			UtilityDAO.close(resultSet, preparedStatement, connection);
		}
		return computer;
	}

	@Override
	public void createComputer(Computer computer) {
		if (computer != null) {
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			try {
				connection = DBConnection.getInstance().getConnection();
				preparedStatement = connection.prepareStatement(UtilityDAO.initializationPreparedStatement(
						SQL_CREATE_COMPUTER, computer.getId(), computer.getName(), computer.getIntroduced(),
						computer.getDiscontinued(), computer.getCompanyId()), Statement.RETURN_GENERATED_KEYS);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Échec de la création de l'objet, aucune ligne ajoutée dans la table.", e);
			} finally {
				UtilityDAO.close(preparedStatement, connection);
			}
		}
	}

	@Override
	public void updateComputerById(Computer computer, long computerId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DBConnection.getInstance().getConnection();
			preparedStatement = connection.prepareStatement(
					UtilityDAO.initializationPreparedStatement(SQL_UPDATE_COMPUTER_BY_ID, computer.getName(),
							computer.getIntroduced(), computer.getDiscontinued(), computer.getCompanyId(), computerId),
					Statement.RETURN_GENERATED_KEYS);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.WARNING, "ECHEC_MODIFICATION_OBJET");
		} finally {
			UtilityDAO.close(preparedStatement, connection);
		}
	}

	@Override
	public void deleteComputerById(long computerId) {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try {
			connection = DBConnection.getInstance().getConnection();
			preparedStatement = connection.prepareStatement(
					UtilityDAO.initializationPreparedStatement(SQL_DELETE_COMPUTER_BY_ID, computerId),
					Statement.NO_GENERATED_KEYS);
			int status = preparedStatement.executeUpdate();
			if (status == 0) {
				throw new SQLException();
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, "ECHEC_SUPPRESSION_OBJET");
		} finally {
			UtilityDAO.close(preparedStatement, connection);
		}
	}
	
}