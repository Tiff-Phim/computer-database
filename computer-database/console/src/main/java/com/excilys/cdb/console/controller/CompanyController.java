package com.excilys.cdb.console.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Page;
import com.excilys.cdb.service.CompanyService;

@Controller
public class CompanyController {

	private final CompanyService companyService;
	
	public CompanyController(CompanyService companyService) {
		this.companyService = companyService;
	}
	
	public List<Optional<Company>> getAll() throws SQLException {
		return companyService.getAllCompany();
	}
	
	public Page<Company> getCompanyPaginated(Page<Company> page) throws SQLException {
		return companyService.getCompanyPaginated(page);
	}
	
	public void deleteCompanyById(long id) throws SQLException {
		companyService.deleteCompanyById(id);
	}

}
