package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.ComputerService;

public class ComputerController {

	ComputerService computerService = new ComputerService();
	
	public List<Optional<Computer>> getAll() throws SQLException {
		return computerService.getInfoComputer();
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
