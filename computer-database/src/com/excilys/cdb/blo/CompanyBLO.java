package com.excilys.cdb.blo;

import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.model.Company;

public interface CompanyBLO {

	public List<Company> getAllCompany()  throws SQLException;
	
}