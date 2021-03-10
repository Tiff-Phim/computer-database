package com.excilys.cdb.blo;

import java.util.List;

import com.excilys.cdb.dao.ComputerDAO;
import com.excilys.cdb.dao.ComputerDAOImpl;
import com.excilys.cdb.model.Computer;

public class ComputerBLOImpl implements ComputerBLO {

	ComputerDAO computerDAO = new ComputerDAOImpl();
	
	@Override
	public List<Computer> getInfoComputer() {
		return computerDAO.findAllComputers();
	}

	@Override
	public Computer getComputerById(long id) {
		return computerDAO.findComputerById(id);
	}

	@Override
	public void addComputer(Computer computer) {
		computerDAO.createComputer(computer);
	}

	@Override
	public void updateComputer(Computer computer, long id) {
		computerDAO.updateComputerById(computer, id);		
	}

	@Override
	public void deleteComputer(long id) {
		computerDAO.deleteComputerById(id);
	}

}
