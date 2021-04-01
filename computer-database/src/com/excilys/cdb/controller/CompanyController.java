package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;

@Controller
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CompanyController {
	
	@Autowired
	CompanyService companyService;
	
	public List<Optional<Company>> getAll() throws SQLException {
		return companyService.getAllCompany();
	}
	
	public Page<Company> getCompanyPaginated(Page<Company> page) throws SQLException {
		return companyService.getCompanyPaginated(page);
	}
	
	public Optional<Company> getCompanyByName(String companyName) throws SQLException {
		return companyService.getCompanyByName(companyName);
	}

}
