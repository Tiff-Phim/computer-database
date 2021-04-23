package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Page;
import com.excilys.cdb.exception.CompanyNotFoundException;
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
	
	public Optional<Company> getCompanyById(long id) {
		try {
			return companyDAO.findCompanyById(id);
		} catch (NoResultException ex) {
			throw new CompanyNotFoundException(String.valueOf(id));
		}
	}
	
	public Page<Company> getCompanyPaginated(Page<Company> page) throws DataAccessException {
		return companyDAO.getCompanyPaginated(page);
	}
	
	public void deleteCompanyById(long id) throws DataAccessException {
		try {
			companyDAO.deleteCompanyById(id);
		} catch (NoResultException ex) {
			throw new CompanyNotFoundException(String.valueOf(id));
		}
	}

}
