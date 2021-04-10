package com.excilys.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component
public class ComputerRowMapper  implements RowMapper<Computer>{

	private static final String ATTRIBUT_ID = "computer.id";
	private static final String ATTRIBUT_COMPUTER_NAME = "computer.name";
	private static final String ATTRIBUT_INTRODUCED = "computer.introduced";
	private static final String ATTRIBUT_DISCONTINUED = "computer.discontinued";
	private static final String ATTRIBUT_COMPANY_NAME = "company.name";
	private static final String ATTRIBUT_COMPANY_ID = "computer.company_id";
	
	@Override
	public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
		Company company = new Company.CompanyBuilder().setId(rs.getLong(ATTRIBUT_COMPANY_ID))
				.setName(rs.getString(ATTRIBUT_COMPANY_NAME)).build();
		Computer computer = new Computer.ComputerBuilder().setId(rs.getLong(ATTRIBUT_ID))
				.setName(rs.getString(ATTRIBUT_COMPUTER_NAME))
				.setIntroduced(convertToLocalDate(rs.getDate(ATTRIBUT_INTRODUCED)))
				.setDiscontinued(convertToLocalDate(rs.getDate(ATTRIBUT_DISCONTINUED))).setCompany(company)
				.build();
		return computer;
	}
	
	private LocalDate convertToLocalDate(Date date) {
		LocalDate localDate = null;
		if (date != null) {
			localDate = date.toLocalDate();
		}
		return localDate;
	}

}
