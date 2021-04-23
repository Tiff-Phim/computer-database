package com.excilys.cdb.service;

import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.springframework.stereotype.Service;

import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.Page;
import com.excilys.cdb.exception.ComputerNotFoundException;
import com.excilys.cdb.persistence.ComputerDAO;

@Service
public class ComputerService {

	private final ComputerDAO computerDAO;

	public ComputerService(ComputerDAO computerDAO) {
		this.computerDAO = computerDAO;
	}

	public Long getTotal() {
		return computerDAO.getTotalComputers();
	}

	public List<Optional<Computer>> getAllComputers() {
		return computerDAO.findAllComputers();
	}

	public Optional<Computer> getComputerById(long id) {
		try {
			return computerDAO.findComputerById(id);
		} catch (NoResultException ex) {
			throw new ComputerNotFoundException(String.valueOf(id));
		}
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
		try {
			computerDAO.deleteComputerById(id);
		} catch (NoResultException ex) {
			throw new ComputerNotFoundException(String.valueOf(id));
		}
	}

}
