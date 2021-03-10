package com.excilys.cdb.dao;

import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.model.Company;

public interface CompanyDAO {

	List<Company> findAllCompanies() throws SQLException;
	Company findCompanyByName(String companyName) throws SQLException;
	
}