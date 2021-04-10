package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import com.excilys.cdb.model.Company;

@Component
public class CompanyRowMapper implements RowMapper<Company> {

	private static final String ATTRIBUT_ID = "id";
	private static final String ATTRIBUT_NAME = "name";

	@Override
	public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Company.CompanyBuilder().setId(rs.getLong(ATTRIBUT_ID)).setName(rs.getString(ATTRIBUT_NAME)).build();
	}

}
