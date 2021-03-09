package com.excilys.cdb.blo;

import java.util.List;

import com.excilys.cdb.model.Computer;

public interface ComputerBLO {

	public List<Computer> getInfoComputer();
	public Computer getComputerById(long id);
	public void addComputer(Computer computer);
	public void updateComputer(Computer computer, long id);
	public void deleteComputer(long id);
	
}
