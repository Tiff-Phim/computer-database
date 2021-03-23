package com.excilys.cdb.dao;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.Page.FilterAttribute;
import com.excilys.cdb.model.Page.SortingOrder;

public class ComputerDAOTest {

	public ComputerDAO computerDAO;

	@Before
	public void setup() {
		computerDAO = new ComputerDAO();
	}

	@After
	public void tearDown() {
		computerDAO = null;
	}

	@Test
	public void testFindCompanyByName() throws SQLException {
		String computerName = "CM-5e";
		Optional<Computer> computer = computerDAO.findComputerByName(computerName);
		assertEquals("Wrong computer name", computerName, computer.get().getName());
	}

	@Test
	public void testFindComputerById() {
		long computerId = 19L;
		Optional<Computer> computer = computerDAO.findComputerById(computerId);
		assertEquals("Wrong computer id", computerId, computer.get().getId());
	}

	@Test
	public void testFindAllComputers() {
		List<Optional<Computer>> computers = new ArrayList<>();
		computers = computerDAO.findAllComputers();
		assertEquals("Wrong size of computers.", 25, computers.size());
	}

	@Test
	public void testGetComputerPaginated() {
		Page<Computer> page = new Page<Computer>(3, 1);
		List<Optional<Computer>> content = new ArrayList<Optional<Computer>>();
		Computer computer1 = new Computer.ComputerBuilder().setId(1l).setName("MacBook Pro 15.4 inch")
				.setCompany(new Company.CompanyBuilder().setId(1l).setName("Apple Inc.").build()).build();
		Computer computer2 = new Computer.ComputerBuilder().setId(2l).setName("CM-2a")
				.setCompany(new Company.CompanyBuilder().setId(2l).setName("Thinking Machines").build()).build();
		Computer computer3 = new Computer.ComputerBuilder().setId(3l).setName("CM-200")
				.setCompany(new Company.CompanyBuilder().setId(2l).setName("Thinking Machines").build()).build();
		content.add(Optional.of(computer1));
		content.add(Optional.of(computer2));
		content.add(Optional.of(computer3));
		computerDAO.getComputerPaginated(page);
		assertEquals("Wrong page size", 3, page.getSize());
		assertEquals("Wrong page number", 1, page.getNumber());
		assertEquals("Wrong page order", SortingOrder.ASC, page.getOrder());
		assertEquals("Wrong page filter", FilterAttribute.COMPUTER_ID, page.getFilter());
		assertEquals("Wrong page content", content, page.getContent());
	}

	@Test
	public void testUpdateComputerById() {
		Computer newComputer = computerDAO.findComputerById(1l).orElseThrow(null);
		newComputer.setName("testName");
		computerDAO.updateComputerById(newComputer, 1l);
		assertEquals("Wrong name value after update", newComputer, computerDAO.findComputerById(1l));
	}

	@Test
	public void testCreateComputer() {
		LocalDate introduced = LocalDate.now();
		Computer computer = new Computer.ComputerBuilder().setId(26l).setName("test").setIntroduced(introduced).build();
		computerDAO.createComputer(computer);
		assertEquals("Wrong computer", computer, computerDAO.findComputerById(26l));
	}

	@Test
	public void testDeleteComputerById() {
		LocalDate introduced = LocalDate.now();
		Computer computer = new Computer.ComputerBuilder().setId(26l).setName("test").setIntroduced(introduced).build();
		computerDAO.createComputer(computer);
		assertEquals("Wrong computer", computer, computerDAO.findComputerById(26l));
		
		computerDAO.deleteComputerById(26l);
		assertEquals("Computer not deleted", true, computerDAO.findComputerById(26l).isPresent());
	}

}
