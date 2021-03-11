package com.excilys.cdb;

import java.sql.SQLException;

import com.excilys.cdb.controller.CLIController;

public class ComputerDatabase {
	
	public static void main(String[] args) throws SQLException {
		new CLIController().run();
	}

}
