package com.excilys.cdb.blo;

import java.util.List;

import com.excilys.cdb.model.Computer;

public interface ComputerBLO {

	List<Computer> getInfoComputer();
	Computer getComputerById(long id);
	void addComputer(Computer computer);
	void updateComputer(Computer computer, long id);
	void deleteComputer(long id);
	
}
