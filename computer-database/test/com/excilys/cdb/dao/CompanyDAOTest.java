package com.excilys.cdb.dao;
import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.Page.FilterAttribute;
import com.excilys.cdb.model.Page.SortingOrder;
import com.excilys.cdb.service.CompanyService;

public class CompanyDAOTest {

	public CompanyDAO companyDAO;
	
	@Before
	public void setup() {
		companyDAO = new CompanyDAO();
	}
	
	@After
	public void tearDown() {
		companyDAO = null;
	}
	
//	@Test
//	public void testFindCompanyByName() throws SQLException {
//		String companyName = "Netronics";
//		Optional<Company> company = companyDAO.findCompanyByName(companyName);		
//		assertEquals("Wrong company name", companyName, company.get().getName());
//	}
	
	@Test
	public void testFindAllCompanies() throws SQLException {
		List<Optional<Company>> companies = new ArrayList<>();
		companies = companyDAO.findAllCompanies();
		assertEquals("Wrong size of companies.", 10, companies.size());
	}
	
	@Test
	public void testGetCompanyPaginated() throws SQLException {
		Page<Company> page = new Page<Company>(10, 1);		
		CompanyService companyService = new CompanyService();
		companyService.getCompanyPaginated(page);
		assertEquals("Wrong page size", 10, page.getSize());
		assertEquals("Wrong page number", 1, page.getNumber());
		assertEquals("Wrong page order", SortingOrder.ASC, page.getOrder());
		assertEquals("Wrong page filter", FilterAttribute.COMPUTER_ID, page.getFilter());
		assertEquals("Wrong page content", 0, page.getContent());
	}

}
