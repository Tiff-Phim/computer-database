package com.excilys.cdb.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;

@Service
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CompanyService {

	CompanyDAO companyDAO = new CompanyDAO();
	
	public List<Optional<Company>> getAllCompany() throws SQLException {
		return companyDAO.findAllCompanies();
	}
	
	public Page<Company> getCompanyPaginated(Page<Company> page) throws SQLException {
		return companyDAO.getCompanyPaginated(page);
	}

	public Optional<Company> getCompanyByName(String companyName) throws SQLException {
		return companyDAO.findCompanyByName(companyName);
	}

}
