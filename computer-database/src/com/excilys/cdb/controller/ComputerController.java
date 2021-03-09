package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.List;

import com.excilys.cdb.blo.ComputerBLO;
import com.excilys.cdb.blo.ComputerBLOImpl;
import com.excilys.cdb.model.Computer;

public class ComputerController {

	ComputerBLO computerBLOService = new ComputerBLOImpl();
	
	public List<Computer> getAll() throws SQLException {
		return computerBLOService.getInfoComputer();
	}
	
	public Computer getComputerById(long id) {
		return computerBLOService.getComputerById(id);
	}
	
	public void insertComputer(Computer computer) {
		computerBLOService.addComputer(computer);
	}
	
	public void updateComputer(Computer computer, long id) {
		computerBLOService.updateComputer(computer, id);
	}
	
	public void deleteComputer(long id) {
		computerBLOService.deleteComputer(id);
	}
	
}
