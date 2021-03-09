package com.excilys.cdb.dao;

import java.util.List;

import com.excilys.cdb.model.Computer;

public interface ComputerDAO {

	public List<Computer> findAllComputers();
	public Computer findComputerById(long computerId);
	public void createComputer(Computer computer);
	public void updateComputerById(Computer computer, long computerId);
	public void deleteComputerById(long computerId);
	
}