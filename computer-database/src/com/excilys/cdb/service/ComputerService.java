package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public class ComputerService {

	ComputerDAO computerDAO = new ComputerDAO();
	
	public List<Optional<Computer>> getInfoComputer() {
		return computerDAO.findAllComputers();
	}
	
	public Page<Computer> getComputerPaginated(Page<Computer> page) {
		return computerDAO.getComputerPaginated(page);
	}

	public Optional<Computer> getComputerById(long id) {
		return computerDAO.findComputerById(id);
	}
	
	public Optional<Computer> getComputerByName(String name) {
		return computerDAO.findComputerByName(name);
	}
	
	public Page<Computer> getComputerPaginatedByNameFilter(Page<Computer> page, String filter) {
		return computerDAO.getComputerPaginatedByNameFilter(page, filter);
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

