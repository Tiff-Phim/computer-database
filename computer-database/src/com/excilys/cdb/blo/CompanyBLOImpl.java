package com.excilys.cdb.blo;

import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.dao.CompanyDAO;
import com.excilys.cdb.dao.CompanyDAOImpl;
import com.excilys.cdb.model.Company;

public class CompanyBLOImpl implements CompanyBLO {

	CompanyDAO companyDAO = new CompanyDAOImpl();
	
	@Override
	public List<Company> getAllCompany() throws SQLException {
		return companyDAO.findAllCompanies();
	}

}