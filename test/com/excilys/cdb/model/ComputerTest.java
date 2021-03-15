package com.excilys.cdb.model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;

import org.junit.Test;

public class ComputerTest {

	private static final long COMPUTER_ID = 5L;
	private static final String COMPUTER_NAME = "test";
	private static final LocalDate COMPUTER_INTRODUCED = LocalDate.of(2000, 01, 22);
	private static final LocalDate COMPUTER_DISCONTINUED = LocalDate.of(2014, 05, 12);
	private static final long COMPANY_ID = 3L;
	private static final String COMPANY_NAME = "test";
	private static final Company COMPANY = new Company.CompanyBuilder().setId(COMPANY_ID).setName(COMPANY_NAME).build();
	
	@Test
	public void testConstructor() {
		final Computer computer = new Computer.ComputerBuilder().setId(COMPUTER_ID).setName(COMPUTER_NAME).setIntroduced(COMPUTER_INTRODUCED)
				.setDiscontinued(COMPUTER_DISCONTINUED).setCompany(COMPANY).build();
		assertEquals("Wrong computer id", COMPUTER_ID, computer.getId());
		assertEquals("Wrong computer name", COMPUTER_NAME, computer.getName());
		assertEquals("Wrong computer introduced date", COMPUTER_INTRODUCED, computer.getIntroduced());
		assertEquals("Wrong computer dicontinued date", COMPUTER_DISCONTINUED, computer.getDiscontinued());
		assertEquals("Wrong computer company id", COMPANY_ID, computer.getCompany());
	}
	
	@Test
	public void testGetComputerId() {
		final Computer computer = new Computer.ComputerBuilder().setId(COMPUTER_ID).setName(COMPUTER_NAME).setIntroduced(COMPUTER_INTRODUCED)
				.setDiscontinued(COMPUTER_DISCONTINUED).setCompany(COMPANY).build();
		assertEquals("Got wrong computer id", COMPUTER_ID, computer.getId());
	}
	
	@Test
	public void testSetComputerId() {
		final Computer computer = new Computer.ComputerBuilder().setId(COMPUTER_ID).setName(COMPUTER_NAME).setIntroduced(COMPUTER_INTRODUCED)
				.setDiscontinued(COMPUTER_DISCONTINUED).setCompany(COMPANY).build();
		long computerId = 3L;
		computer.setId(computerId);
		assertEquals("Wrong sets of computer id", computerId, computer.getId());
	}
	
	@Test
	public void testGetComputerName() {
		final Computer computer = new Computer.ComputerBuilder().setId(COMPUTER_ID).setName(COMPUTER_NAME).setIntroduced(COMPUTER_INTRODUCED)
				.setDiscontinued(COMPUTER_DISCONTINUED).setCompany(COMPANY).build();
		assertEquals("Got wrong computer name", COMPUTER_NAME, computer.getName());
	}
	
	@Test
	public void testSetComputerName() {
		final Computer computer = new Computer.ComputerBuilder().setId(COMPUTER_ID).setName(COMPUTER_NAME).setIntroduced(COMPUTER_INTRODUCED)
				.setDiscontinued(COMPUTER_DISCONTINUED).setCompany(COMPANY).build();
		String computerName = "testName";
		computer.setName(computerName);
		assertEquals("Wrong sets of company id", computerName, computer.getName());
	}
	
	@Test
	public void testGetComputerIntroduced() {
		final Computer computer = new Computer.ComputerBuilder().setId(COMPUTER_ID).setName(COMPUTER_NAME).setIntroduced(COMPUTER_INTRODUCED)
				.setDiscontinued(COMPUTER_DISCONTINUED).setCompany(COMPANY).build();
		assertEquals("Got wrong computer introduced date", COMPUTER_INTRODUCED, computer.getIntroduced().get());
	}
	
	@Test
	public void testSetComputerIntroduced() {
		final Computer computer = new Computer.ComputerBuilder().setId(COMPUTER_ID).setName(COMPUTER_NAME).setIntroduced(COMPUTER_INTRODUCED)
				.setDiscontinued(COMPUTER_DISCONTINUED).setCompany(COMPANY).build();
		final LocalDate introduced = LocalDate.of(2015, 10, 02);
		computer.setIntroduced(introduced);
		assertEquals("Got wrong computer introduced date", introduced, computer.getIntroduced().get());
	}
	
	@Test
	public void testGetComputerDiscontinued() {
		final Computer computer = new Computer.ComputerBuilder().setId(COMPUTER_ID).setName(COMPUTER_NAME).setIntroduced(COMPUTER_INTRODUCED)
				.setDiscontinued(COMPUTER_DISCONTINUED).setCompany(COMPANY).build();
		assertEquals("Got wrong computer discontinued date", COMPUTER_DISCONTINUED, computer.getDiscontinued().get());
	}
	
	@Test
	public void testSetComputerDiscontinued() {
		final Computer computer = new Computer.ComputerBuilder().setId(COMPUTER_ID).setName(COMPUTER_NAME).setIntroduced(COMPUTER_INTRODUCED)
				.setDiscontinued(COMPUTER_DISCONTINUED).setCompany(COMPANY).build();
		final LocalDate dicontinued = LocalDate.of(2018, 8, 07);
		computer.setDiscontinued(dicontinued);
		assertEquals("Got wrong computer discontinued date", dicontinued, computer.getDiscontinued());
	}
	
	@Test
	public void testGetCompanyId() {
		final Computer computer = new Computer.ComputerBuilder().setId(COMPUTER_ID).setName(COMPUTER_NAME).setIntroduced(COMPUTER_INTRODUCED)
				.setDiscontinued(COMPUTER_DISCONTINUED).setCompany(COMPANY).build();
		assertEquals("Got wrong computer company id", COMPANY_ID, computer.getCompany());
	}
	
	@Test
	public void testSetCompanyId() {
		final Computer computer = new Computer.ComputerBuilder().setId(COMPUTER_ID).setName(COMPUTER_NAME).setIntroduced(COMPUTER_INTRODUCED)
				.setDiscontinued(COMPUTER_DISCONTINUED).setCompany(COMPANY).build();
		Company newCompany = new Company.CompanyBuilder().setId(COMPANY_ID).setName(COMPANY_NAME).build();
		computer.setCompany(newCompany);
		assertEquals("Wrong sets of computer company id", company, computer.getCompany());
	}

}