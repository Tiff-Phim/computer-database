package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.model.Company;

public class CompanyDAO {


	private static final String SQL_GET_ALL_COMPANIES = "SELECT id, name FROM company";
	private static final String SQL_GET_COMPANY_BY_NAME = "SELECT id, name FROM company WHERE name=?";

	private static Logger logger = Logger.getLogger(CompanyDAO.class.getName());
	
	private static DBConnection dbConnection = DBConnection.getInstance();
	private static CompanyMapper mapper;

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
			ResultSet results = null;
			results = statement.executeQuery(SQL_GET_ALL_COMPANIES);
			while (results.next()) {
				companyList.add(mapper.mapToCompany(results));
			}
		} catch (SQLException e) {
//			logger.log(Level.WARNING, UtilityDAO.FAIL_FIND_OBJECT, e);
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
			preparedStatement.setString(1, companyName);
			ResultSet resultSet = preparedStatement.executeQuery();
			resultSet.next();
			company = mapper.mapToCompany(resultSet);
		} catch (SQLException e) {
//			logger.log(Level.WARNING, UtilityDAO.FAIL_FIND_OBJECT_BY_NAME, e);
		}
		return company;
	}

}
