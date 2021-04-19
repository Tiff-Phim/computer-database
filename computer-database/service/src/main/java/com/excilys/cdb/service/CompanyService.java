package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Page;
import com.excilys.cdb.persistence.CompanyDAO;

@Service
public class CompanyService {

	private final CompanyDAO companyDAO;
	
	public CompanyService(CompanyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}
	
	public List<Optional<Company>> getAllCompany() throws DataAccessException {
		return companyDAO.findAllCompanies();
	}
	
	public Page<Company> getCompanyPaginated(Page<Company> page) throws DataAccessException {
		return companyDAO.getCompanyPaginated(page);
	}
	
	public void deleteCompanyById(long id) throws DataAccessException {
		companyDAO.deleteCompanyById(id);
	}

}
