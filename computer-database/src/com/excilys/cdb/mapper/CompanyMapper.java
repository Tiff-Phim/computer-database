package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.excilys.cdb.model.Company;

public class CompanyMapper {
	
	private static final String ATTRIBUT_ID = "id";
	private static final String ATTRIBUT_NAME = "name";

	/**
	 * Makes the correspondence between a line from the database table (a ResultSet)
	 * and a Company bean.
	 * 
	 * @param resultSet line from the database table
	 * @return bean of the Company
	 * @throws SQLException
	 */
	public Optional<Company> mapToCompany(ResultSet resultSet) throws SQLException {
		return Optional.ofNullable(new Company.CompanyBuilder().setId(resultSet.getLong(ATTRIBUT_ID))
				.setName(resultSet.getString(ATTRIBUT_NAME)).build());
	}
	
}