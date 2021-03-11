package com.excilys.cdb.dao;

import java.util.List;

import com.excilys.cdb.model.Computer;

/**
 * This is an interface for ComputerDAO.
 * 
 * @author Tiffany PHIMMASANE
 * @version 0.1
 */
public interface ComputerDAO {
	
	List<Computer> findAllComputers();
	Computer findComputerById(long computerId);
	void createComputer(Computer computer);
	void updateComputerById(Computer computer, long computerId);
	void deleteComputerById(long computerId);
	
}