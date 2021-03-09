package com.excilys.cdb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.excilys.cdb.model.Company;

public class CompanyDAOImpl implements CompanyDAO {

	private static final String ATTRIBUT_ID = "id";
	private static final String ATTRIBUT_NAME = "name";

	private static final String SQL_GET_ALL_COMPANIES = "SELECT * FROM company";
	
	private static Logger logger = Logger.getLogger(CompanyDAOImpl.class.getName());

	/**
	 * Makes the correspondence between a line from the database table (a ResultSet)
	 * and a Company bean.
	 * 
	 * @param resultSet line from the database table
	 * @return bean of the Company
	 * @throws SQLException
	 */
	protected Company getCompany(ResultSet resultSet) throws SQLException {
		Company company = new Company();
		company.setId(resultSet.getInt(ATTRIBUT_ID));
		company.setName(resultSet.getString(ATTRIBUT_NAME));
		return company;
	}
	
	@Override
	public List<Company> findAllCompanies() throws SQLException {
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet results = null;
		List<Company> companyList = new ArrayList<>();
		try {
			connection = DBConnection.getInstance().getConnection();
			preparedStatement = connection.prepareStatement(SQL_GET_ALL_COMPANIES);
			results = preparedStatement.executeQuery();
			while(results.next()) {
				companyList.add(getCompany(results));
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING, UtilityDAO.FAIL_FIND_OBJECT, e);
		} finally {
			UtilityDAO.close(results, preparedStatement, connection);
		}
		return companyList;
	}

}