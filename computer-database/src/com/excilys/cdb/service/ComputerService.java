package com.excilys.cdb.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

@Service
public class ComputerService {

	private final ComputerDAO computerDAO;
	
	public ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}
	
	public Long getTotal() {
		return this.computerDAO.getTotalComputers();
	}

	public Optional<Computer> getComputerById(long id) {
		return computerDAO.findComputerById(id);
	}
	
	public Page<Computer> getComputerPaginatedByNameFilter(Page<Computer> page, String filter) {
		return computerDAO.getComputerPaginatedByNameFilter(page, filter);
	}
	
	public Page<Computer> getComputerPaginatedByNameFilter(Page<Computer> page) {
		return computerDAO.getComputerPaginatedByNameFilter(page);
	}

	public void addComputer(Computer computer) {
		computerDAO.createComputer(computer);
	}

	public void updateComputer(Computer computer, long id) {
		computerDAO.updateComputerById(computer, id);		
	}

	public void deleteComputer(long id) {
		computerDAO.deleteComputerById(id);
	}

}

