package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public class ComputerDAO {

	private static final String SQL_GET_ALL_COMPUTERS = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued,"
			+ " computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = computer.company_id";
	private static final String SQL_GET_PAGE = "SELECT computer.id, computer.name, company.name, computer.introduced, computer.discontinued,"
			+ " computer.company_id FROM computer LEFT JOIN company ON company.id = computer.company_id ORDER BY SORT_BY SORT_ORDER LIMIT ? OFFSET ?";
	private static final String SQL_GET_COMPUTER_BY_ID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued,"
			+ " computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = computer.company_id WHERE computer.id=?";
	private static final String SQL_GET_COMPUTER_BY_NAME = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued,"
			+ " computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = computer.company_id WHERE computer.name=?";
	private static final String SQL_CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (?, ?, ?, ?)";
	private static final String SQL_UPDATE_COMPUTER_BY_ID = "UPDATE computer SET computer.name=?, computer.introduced=?, computer.discontinued=?"
			+ ", computer.company_id=? WHERE computer.id=?";
	private static final String SQL_DELETE_COMPUTER_BY_ID = "DELETE FROM computer WHERE id=?";

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	private static DBConnection dbConnection = DBConnection.getInstance();
	private static ComputerMapper mapper = new ComputerMapper();

	/**
	 * Lists all computers present in database.
	 * 
	 * @return computers all the computers stored in database
	 * @throws SQLException
	 */
	public List<Optional<Computer>> findAllComputers() {
		List<Optional<Computer>> computerList = new ArrayList<>();
		try (Connection connection = dbConnection.getConnection(); Statement statement = connection.createStatement()) {
			logger.debug("ComputerDAO: getting all computers ...");
			ResultSet resultSet = statement.executeQuery(SQL_GET_ALL_COMPUTERS);
			while (resultSet.next()) {
				computerList.add(mapper.mapToComputer(resultSet));
			}
		} catch (SQLException e) {
			logger.error("Error occur in ComputerDAO, could not get computers.", e);
		}
		return computerList;
	}

	/**
	 * List computers with pages.
	 * 
	 * @param page
	 * @return page of computers
	 */
	public Page<Computer> getComputerPaginated(Page<Computer> page) {
		List<Optional<Computer>> computerList = new ArrayList<>();
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement preparedStatement = connection
						.prepareStatement(SQL_GET_PAGE.replaceFirst("SORT_ORDER", page.getOrder().name())
								.replaceFirst("SORT_BY", page.getFilter().getAttribute()))) {
			logger.debug("ComputerDAO: getting all computers paginated ...");
			preparedStatement.setInt(1, page.getSize());
			preparedStatement.setInt(2, (page.getNumber() - 1) * page.getSize());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				computerList.add(mapper.mapToComputer(resultSet));
				page.setContent(computerList);
			}
		} catch (SQLException e) {
			logger.error("Error occur in ComputerDAO, could not get computer pages.", e);
		}
		return page;
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
			logger.debug("ComputerDAO: getting computer by id ...");
			prepStatement.setLong(1, computerId);
			ResultSet resultSet = prepStatement.executeQuery();
			resultSet.next();
			computer = mapper.mapToComputer(resultSet);
		} catch (SQLException e) {
			logger.error("Error occur in ComputerDAO, could not get computer by id.", e);
		}
		return computer;
	}

	/**
	 * Find computer by name.
	 * 
	 * @param name of the computer to find
	 * @return computer
	 */
	public Optional<Computer> findComputerByName(String name) {
		Optional<Computer> computer = Optional.empty();
		try (Connection con = dbConnection.getConnection();
				PreparedStatement prepStatement = con.prepareStatement(SQL_GET_COMPUTER_BY_NAME)) {
			logger.debug("ComputerDAO: getting computer by name ...");
			prepStatement.setString(1, name);
			ResultSet resultSet = prepStatement.executeQuery();
			resultSet.next();
			computer = mapper.mapToComputer(resultSet);
		} catch (SQLException e) {
			logger.error("Error occur in ComputerDAO, could not get computer by name.", e);
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
				logger.debug("ComputerDAO: creating new computer ...");
				initPreparedStatement(preparedStatement, computer);
				preparedStatement.setString(1, computer.getName());
				if (computer.getIntroduced() != null) {
					preparedStatement.setDate(2, Date.valueOf(computer.getIntroduced()));
				}
				if (computer.getDiscontinued() != null) {
					preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinued()));
				}
				if (computer.getCompany().getId() != 0l) {
					preparedStatement.setLong(4, computer.getCompany().getId());
				} else {
					preparedStatement.setString(4, null);
				}
				preparedStatement.executeUpdate();
			} catch (SQLException e) {
				logger.error("Error occur in ComputerDAO, could not create computer, nothing added to database.", e);
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
			logger.debug("ComputerDAO: updating computer ...");
			preparedStatement.setString(1, computer.getName());
			preparedStatement.setDate(2, Date.valueOf(computer.getIntroduced()));
			preparedStatement.setDate(3, Date.valueOf(computer.getDiscontinued()));

			if (computer.getCompany().getId() != 0l) {
				preparedStatement.setLong(4, computer.getCompany().getId());
			} else {
				preparedStatement.setString(4, null);
			}
			preparedStatement.setLong(5, computerId);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			logger.error("Error occur in ComputerDAO, could not update computer.", e);
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
			logger.debug("ComputerDAO: deleting computer ...");
			preparedStatement.setLong(1, computerId);
			int status = preparedStatement.executeUpdate();
			if (status == 0) {
				throw new SQLException();
			}
		} catch (SQLException e) {
			logger.error("Error occur in ComputerDAO, could not delete computer.", e);
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