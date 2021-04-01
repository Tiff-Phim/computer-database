package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@Controller
public class ComputerController {

	@Autowired
	ComputerService computerService;
	
	public List<Optional<Computer>> getAll() throws SQLException {
		return computerService.getInfoComputer();
	}
	
	public Page<Computer> getComputerPaginated(Page<Computer> page) throws SQLException {
		return computerService.getComputerPaginated(page);
	}
	
	public Optional<Computer> getComputerById(long id) {
		return computerService.getComputerById(id);
	}
	
	public void insertComputer(Computer computer) {
		computerService.addComputer(computer);
	}
	
	public void updateComputer(Computer computer, long id) {
		computerService.updateComputer(computer, id);
	}
	
	public void deleteComputer(long id) {
		computerService.deleteComputer(id);
	}
	
}
