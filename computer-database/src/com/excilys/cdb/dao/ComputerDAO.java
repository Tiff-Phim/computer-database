package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;

public class ComputerDAO {

	private static final String SQL_GET_ALL_COMPUTERS = "SELECT id, name, introduced, discontinued, company_id FROM computer";
	private static final String SQL_GET_COMPUTER_BY_ID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued, computer.company_id"
			+ " FROM computer LEFT JOIN company ON company.id = computer.company_id WHERE computer.id=?";
	private static final String SQL_CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
//	private static final String SQL_UPDATE_COMPUTER_BY_ID = "UPDATE computer SET company_id=? WHERE computer.id=?"; //TODO
	private static final String SQL_UPDATE_COMPUTER_BY_ID = "UPDATE computer SET computer.name=?, computer.introduced=?, computer.discontinued=?"
			+ ", computer.company_id=? WHERE computer.id=?";
	private static final String SQL_DELETE_COMPUTER_BY_ID = "DELETE FROM computer WHERE id=?";

	private static Logger logger = Logger.getLogger(ComputerDAO.class.getName());

	private DBConnection dbConnection = DBConnection.getInstance();
	private static ComputerMapper mapper;

	/**
	 * Lists all computers present in database.
	 * 
	 * @return computers all the computers stored in database
	 * @throws SQLException
	 */
	public List<Optional<Computer>> findAllComputers() {
		List<Optional<Computer>> computerList = new ArrayList<>();
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_ALL_COMPUTERS)) {
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				computerList.add(mapper.mapToComputer(resultSet));
			}
		} catch (SQLException e) {
//			logger.log(Level.WARNING, UtilityDAO.FAIL_FIND_OBJECT, e);
		}
		return computerList;
	}

	/**
	 * Find a computer by its id.
	 * 
	 * @return computer details
	 * @throws SQLException
	 */
	public Optional<Computer> findComputerById(long computerId) {
		Optional<Computer> computer = Optional.empty();
		try (Connection con = dbConnection.getConnection();
				PreparedStatement prepStatement = con.prepareStatement(SQL_GET_COMPUTER_BY_ID)) {
			prepStatement.setLong(1, computerId);
			ResultSet resultSet = prepStatement.executeQuery();
			resultSet.next();
			computer = mapper.mapToComputer(resultSet);
		} catch (SQLException e) {
//			logger.log(Level.WARNING, UtilityDAO.FAIL_FIND_OBJECT_BY_ID, e);
		}
		return computer;
	}

	/**
	 * Creates a new computer and store it in the database.
	 * 
	 * @throws SQLException
	 */
	public void createComputer(Computer computer) {
		if (computer != null) {
			try (Connection connection = dbConnection.getConnection();
					PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_COMPUTER)) {
				initPreparedStatement(preparedStatement, computer);
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				logger.log(Level.WARNING, "Échec de la création de l'objet, aucune ligne ajoutée dans la table.", e);
			}
		}
	}

	/**
	 * Updates a computer identified by its id.
	 * 
	 * @param computer   the updated computer
	 * @param computerId the id of the computer to update
	 * @throws SQLException
	 */
	public void updateComputerById(Computer computer, long computerId) {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_COMPUTER_BY_ID)) {
			// TODO

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.log(Level.WARNING, "ECHEC_MODIFICATION_OBJET");
		}
	}

	/**
	 * Deletes a computer by specifying its id.
	 * 
	 * @param computerId the id of the computer to delete
	 * @throws SQLException
	 */
	public void deleteComputerById(long computerId) {
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_COMPUTER_BY_ID)) {
			preparedStatement.setLong(1, computerId);
			int status = preparedStatement.executeUpdate();
			if (status == 0) {
				throw new SQLException();
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, "ECHEC_SUPPRESSION_OBJET");
		}
	}

	/**
	 * Init a prepared statement for a computer.
	 * 
	 * @param preparedStatement
	 * @param computer
	 * @throws SQLException
	 */
	private void initPreparedStatement(PreparedStatement preparedStatement, Computer computer) throws SQLException {
		preparedStatement.setNString(1, computer.getName());
		initPreparedStatementDate(preparedStatement, computer.getIntroduced(), 2);
		initPreparedStatementDate(preparedStatement, computer.getDiscontinued(), 3);
		preparedStatement.setLong(4, computer.getCompany().getId());
	}

	/**
	 * Init date for preparedStatement.
	 * 
	 * @param preparedStatement
	 * @param date
	 * @param pos
	 * @throws SQLException
	 */
	private void initPreparedStatementDate(PreparedStatement preparedStatement, LocalDate date, int pos)
			throws SQLException {
		if (date != null) {
			preparedStatement.setDate(pos, Date.valueOf(date));
		} else {
			preparedStatement.setNull(pos, Types.DATE);
		}
	}

}
