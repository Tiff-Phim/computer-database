package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.model.Company;

public class CompanyService {

	CompanyDAO companyDAO = new CompanyDAO();
	
	public List<Optional<Company>> getAllCompany() throws SQLException {
		return companyDAO.findAllCompanies();
	}

	public Optional<Company> getCompanyByName(String companyName) throws SQLException {
		return companyDAO.findCompanyByName(companyName);
	}

}
