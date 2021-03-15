package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;

public class CompanyController {
	
	CompanyService companyService = new CompanyService();
	
	public List<Optional<Company>> getAll() throws SQLException {
		return companyService.getAllCompany();
	}
	
	public Optional<Company> getCompanyByName(String companyName) throws SQLException {
		return companyService.getCompanyByName(companyName);
	}

}
