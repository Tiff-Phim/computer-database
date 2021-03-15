package com.excilys.cdb.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CompanyTest {

	private static final long COMPANY_ID = 12L;
	private static final String COMPANY_NAME = "test";

	@Test
	public void testConstructor() {
		final Company company = new Company.CompanyBuilder().setName(COMPANY_NAME).build();
		assertEquals("Wrong company name", COMPANY_NAME, company.getName());
	}
	
	@Test
	public void testGetCompanyId() {
		final Company company = new Company.CompanyBuilder().setId(COMPANY_ID).setName(COMPANY_NAME).build();
		assertEquals("Got wrong company id", COMPANY_ID, company.getId());
	}
	
	@Test
	public void testSetCompanyId() {
		final Company company = new Company.CompanyBuilder().setId(COMPANY_ID).setName(COMPANY_NAME).build();
		long companyId = 5L;
		company.setId(companyId);
		assertEquals("Wrong sets of company id", companyId, company.getId());
	}
	
	@Test
	public void testGetCompanyName() {
		final Company company = new Company.CompanyBuilder().setId(COMPANY_ID).setName(COMPANY_NAME).build();
		assertEquals("Got wrong company name", COMPANY_NAME, company.getName());
	}
	
	@Test
	public void testSetCompanyName() {
		final Company company = new Company.CompanyBuilder().setId(COMPANY_ID).setName(COMPANY_NAME).build();
		String companyName = "testName";
		company.setName(companyName);
		assertEquals("Wrong sets of company name", companyName, company.getName());
	}
	
	@Test
	public void testToString() {
		final Company company = new Company.CompanyBuilder().setId(COMPANY_ID).setName(COMPANY_NAME).build();
		final String resultatAttendu = "Company [id=" + COMPANY_ID + ", name=" + COMPANY_NAME + "]";
		
		assertEquals("Wrong format for toString method", resultatAttendu, company.toString());
	}

}