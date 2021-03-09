package com.excilys.cdb.dao;

import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.model.Company;

public interface CompanyDAO {

	public List<Company> findAllCompanies() throws SQLException ;
	
}