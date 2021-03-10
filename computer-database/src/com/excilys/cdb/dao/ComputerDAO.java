package com.excilys.cdb.dao;

import java.util.List;

import com.excilys.cdb.model.Computer;

public interface ComputerDAO {
	
	List<Computer> findAllComputers();
	Computer findComputerById(long computerId);
	void createComputer(Computer computer);
	void updateComputerById(Computer computer, long computerId);
	void deleteComputerById(long computerId);
	
}