package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerMapper {

	private static final String ATTRIBUT_ID = "computer.id";
	private static final String ATTRIBUT_COMPUTER_NAME = "computer.name";
	private static final String ATTRIBUT_INTRODUCED = "computer.introduced";
	private static final String ATTRIBUT_DISCONTINUED = "computer.discontinued";
	private static final String ATTRIBUT_COMPANY_NAME = "company.name";
	private static final String ATTRIBUT_COMPANY_ID = "computer.company_id";
	
	/**
	 * Makes the correspondence between a line from the database table (a ResultSet)
	 * and a Company bean.
	 * 
	 * @param resultSet line from the database table
	 * @return bean of the Company
	 * @throws SQLException
	 */
	public Optional<Computer> mapToComputer(ResultSet resultSet) throws SQLException {
		Company company = new Company.CompanyBuilder().setId(resultSet.getLong(ATTRIBUT_COMPANY_ID))
				.setName(resultSet.getString(ATTRIBUT_COMPANY_NAME)).build();
		
		Computer computer = new Computer.ComputerBuilder().setId(resultSet.getLong(ATTRIBUT_ID))
				.setName(resultSet.getString(ATTRIBUT_COMPUTER_NAME))
				.setIntroduced(resultSet.getDate(ATTRIBUT_INTRODUCED).toLocalDate())
				.setDiscontinued(resultSet.getDate(ATTRIBUT_DISCONTINUED).toLocalDate())
				.setCompany(company).build();
		
		return Optional.ofNullable(computer);
	}
	
}
