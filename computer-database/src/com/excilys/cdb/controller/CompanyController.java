package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.blo.CompanyBLO;
import com.excilys.cdb.blo.CompanyBLOImpl;
import com.excilys.cdb.model.Company;

public class CompanyController {
	
	CompanyBLO companyBLOService = new CompanyBLOImpl();
	
	public List<Company> getAll() throws SQLException {
		return companyBLOService.getAllCompany();
	}

}
