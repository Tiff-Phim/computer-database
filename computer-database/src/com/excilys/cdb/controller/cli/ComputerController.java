package com.excilys.cdb.controller.cli;

import java.util.Optional;

import org.springframework.stereotype.Controller;

import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.ComputerService;

@Controller
public class ComputerController {

	private final ComputerService computerService;
	
	public ComputerController(ComputerService computerService) {
		this.computerService = computerService;
	}
	
	public Long getTotal() {
		return this.computerService.getTotal();
	}
	
	public Optional<Computer> getComputerById(long id) {
		return computerService.getComputerById(id);
	}
	
	public Page<Computer> getComputerPaginatedByNameFilter(Page<Computer> page, String filter) {
		return computerService.getComputerPaginatedByNameFilter(page, filter);
	}
	
	public Page<Computer> getComputerPaginatedByNameFilter(Page<Computer> page) {
		return computerService.getComputerPaginatedByNameFilter(page);
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
