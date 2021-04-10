package com.excilys.cdb.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.excilys.cdb.mapper.ComputerRowMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Repository
public class ComputerDAO {

	private static final String SQL_GET_TOTAL = "SELECT COUNT(*) FROM computer";

	private static final String SQL_GET_COMPUTER_BY_ID = "SELECT computer.id, computer.name, computer.introduced, computer.discontinued,"
			+ " computer.company_id, company.name FROM computer LEFT JOIN company ON company.id = computer.company_id WHERE computer.id = :id";

	private static final String SQL_CREATE_COMPUTER = "INSERT INTO computer (name, introduced, discontinued, company_id) VALUES (:name, :introduced, :discontinued, :companyId)";

	private static final String SQL_UPDATE_COMPUTER_BY_ID = "UPDATE computer SET computer.name = :name, computer.introduced = :introduced, computer.discontinued = :discontinued"
			+ ", computer.company_id = :companyId WHERE computer.id = :id";

	private static final String SQL_DELETE_COMPUTER_BY_ID = "DELETE FROM computer WHERE id = :id";

	private static final String SQL_SEARCH_COMPUTER_BY_NAME_FILTER = "SELECT computer.id, computer.name, company.name, computer.introduced, computer.discontinued, computer.company_id"
			+ " FROM computer LEFT JOIN company ON company.id = computer.company_id";

	private static final String SQL_SEARCH_BY_COMPUTER_AND_COMPANY_NAMES = " WHERE (computer.name LIKE :search) OR (company.name LIKE :search)";
	private static final String SQL_ORDER_BY_AND_OFFSET = " ORDER BY :filter :order LIMIT :size OFFSET :offset";

	private static Logger logger = LoggerFactory.getLogger(ComputerDAO.class);

	private final JdbcTemplate jdbcTemplate;
	private final NamedParameterJdbcTemplate namedParamJdbcTemplate;
	private final ComputerRowMapper mapper;

	public ComputerDAO(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParamJdbcTemplate,
			ComputerRowMapper mapper) {
		this.jdbcTemplate = jdbcTemplate;
		this.namedParamJdbcTemplate = namedParamJdbcTemplate;
		this.mapper = mapper;
	}

	/**
	 * Get total of computers.
	 * 
	 * @return total
	 */
	public int getTotalComputers() {
		logger.debug("Counting total of computers ...");
		return jdbcTemplate.queryForObject(SQL_GET_TOTAL, Integer.class);
	}

	/**
	 * Find a computer by its id.
	 * 
	 * @return computer details
	 * @throws SQLException
	 */
	public Optional<Computer> findComputerById(long computerId) {
		logger.debug("Searching for computer with id " + computerId);
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", computerId);
		Computer computer = namedParamJdbcTemplate.queryForObject(SQL_GET_COMPUTER_BY_ID, parameters, mapper);
		return Optional.ofNullable(computer);
	}

	/**
	 * List computers by pages with name filter. The filter is on both computer and
	 * company names.
	 * 
	 * @param page
	 * @param filter
	 * @return page of computers
	 */
	public Page<Computer> getComputerPaginatedByNameFilter(Page<Computer> page, String search) {
		logger.debug("Searching computers like " + search);
		String query = (SQL_SEARCH_COMPUTER_BY_NAME_FILTER + SQL_SEARCH_BY_COMPUTER_AND_COMPANY_NAMES
				+ SQL_ORDER_BY_AND_OFFSET).replaceFirst(":filter", page.getFilter().getAttribute())
						.replaceFirst(":order", page.getOrder().name());
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("size", page.getSize())
				.addValue("offset", (page.getNumber() - 1) * page.getSize()).addValue("search", "%" + search + "%");
		List<Computer> computerList = namedParamJdbcTemplate.query(query, parameters, mapper);
		page.setContent(computerList.stream().map(Optional::ofNullable).collect(Collectors.toList()));
		return page;
	}

	/**
	 * List computers by pages.
	 * 
	 * @param page
	 * @return page of computers
	 */
	public Page<Computer> getComputerPaginatedByNameFilter(Page<Computer> page) {
		String query = SQL_SEARCH_COMPUTER_BY_NAME_FILTER
				+ SQL_ORDER_BY_AND_OFFSET.replaceFirst(":filter", page.getFilter().getAttribute())
						.replaceFirst(":order", page.getOrder().name());
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("size", page.getSize()).addValue("offset",
				(page.getNumber() - 1) * page.getSize());
		List<Computer> computerList = namedParamJdbcTemplate.query(query, parameters, mapper);
		page.setContent(computerList.stream().map(Optional::ofNullable).collect(Collectors.toList()));
		return page;
	}

	/**
	 * Creates a new computer and store it in the database.
	 * 
	 * @throws SQLException
	 */
	public void createComputer(Computer computer) {
		if (computer != null) {
			logger.debug("Creating computer " + computer);
			SqlParameterSource parameters = new BeanPropertySqlParameterSource(computer);
			namedParamJdbcTemplate.update(SQL_CREATE_COMPUTER, parameters);
			logger.info("Computer succesfully added to the database.");
		}
	}

	/**
	 * Updates a computer identified by its id.
	 * 
	 * @param computer   the updated computer
	 * @param computerId the id of the computer to update
	 * @throws SQLException
	 */
	public void updateComputerById(Computer computer, long id) {
		logger.debug("Updating computer with id " + id);
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(computer);
		namedParamJdbcTemplate.update(SQL_UPDATE_COMPUTER_BY_ID, parameters);
		logger.info("Computer succesfully updated.");
	}

	/**
	 * Deletes a computer by specifying its id.
	 * 
	 * @param computerId the id of the computer to delete
	 * @throws SQLException
	 */
	public void deleteComputerById(long computerId) {
		logger.debug("Deleting computer with id " + computerId);
		SqlParameterSource parameters = new MapSqlParameterSource().addValue("id", computerId);
		namedParamJdbcTemplate.update(SQL_DELETE_COMPUTER_BY_ID, parameters);
	}

}