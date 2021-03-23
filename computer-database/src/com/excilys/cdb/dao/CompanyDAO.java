package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

public class CompanyDAO {

	private static final String SQL_GET_ALL_COMPANIES = "SELECT id, name FROM company";
	private static final String SQL_GET_COMPANY_BY_NAME = "SELECT id, name FROM company WHERE name=?";
	private static final String SQL_GET_COMPANY_PAGE = "SELECT id, name FROM company ORDER BY id LIMIT ? OFFSET ?";

	private static Logger logger = LoggerFactory.getLogger(CompanyDAO.class);
	
	private static DBConnection dbConnection = DBConnection.getInstance();
	private static CompanyMapper mapper = new CompanyMapper();

	/**
	 * Lists all companies present in database.
	 * 
	 * @return companies all the companies stored in database
	 * @throws SQLException
	 */
	public List<Optional<Company>> findAllCompanies() throws SQLException {
		List<Optional<Company>> companyList = new ArrayList<>();
		try (Connection con = dbConnection.getConnection();
				Statement statement = con.createStatement()) {
			logger.debug("CompanyDAO: getting all companies ...");
			ResultSet results = null;
			results = statement.executeQuery(SQL_GET_ALL_COMPANIES);
			while (results.next()) {
				companyList.add(mapper.mapToCompany(results));
			}
		} catch (SQLException e) {
			logger.error("Error occur in ComputerDAO, could not get companies.", e);
		}
		return companyList;
	}

	/**
	 * Show details for the company selected with its name.
	 * 
	 * @return company details
	 * @throws SQLException
	 */
	public Optional<Company> findCompanyByName(String companyName) throws SQLException {
		Optional<Company> company = Optional.empty();
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_COMPANY_BY_NAME)) {
			logger.debug("CompanyDAO: getting company by name ...");
			preparedStatement.setString(1, companyName);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			company = mapper.mapToCompany(resultSet);
		} catch (SQLException e) {
			logger.error("Error occur in ComputerDAO, could not get company by name.", e);
		}
		return company;
	}
	
	/**
	 * List companies present in database, paginated.
	 * 
	 * @param page
	 * @return page with list of companies
	 */
	public Page<Company> getCompanyPaginated(Page<Company> page) throws SQLException {
		ArrayList<Optional<Company>> companyList = new ArrayList<>();
		try (Connection connection = dbConnection.getConnection();
				PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_COMPANY_PAGE)) {
			logger.debug("CompanyDAO: getting company by page ...");
			preparedStatement.setInt(1, page.getSize());
			preparedStatement.setInt(2, (page.getNumber() - 1) * page.getSize());
			ResultSet resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				companyList.add(mapper.mapToCompany(resultSet));
				page.setContent(companyList);
			}
		} catch (SQLException e) {
			logger.error("Error occur in ComputerDAO, could not get company pages.", e);
		}
		return page;
	}

}