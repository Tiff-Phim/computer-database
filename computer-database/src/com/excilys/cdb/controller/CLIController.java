package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.util.Scanner;

public class CLIController {
	
	private CompanyController companyController = new CompanyController();
	private ComputerController computerController = new ComputerController();

	private Scanner sc = new Scanner(System.in);

	public void run() throws SQLException {
		while (true) {
			displayMenu();
			int command = sc.nextInt();
			processCommand(command);
		}
	}

	private void processCommand(int command) throws SQLException {
		switch (command) {
		case 1:
			System.out.println(computerController.getAll().toString());
			break;
		case 2:
			System.out.println(companyController.getAll().toString());
			break;
		case 3:
			long id = sc.nextLong();
			System.out.println(computerController.getComputerById(id));
			break;
		case 4:
			// TODO create a computer and add it to database
			break;
		case 5:
			// TODO update computer
			break;
		case 6:
			// TODO delete computer
			break;
		case 0:
			System.out.println("Bye");
			sc.close();
			System.exit(1);
		default:
			sc.close();
			return;
		}
	}

	private void displayMenu() {
		System.out.println("Welcome to Computer-database CLI");
		System.out.println("Please choose and enter command number");
		System.out.println("1	List the computers stored in the database");
		System.out.println("2	List the companies stored in the database");
		System.out.println("3	Show details for the selected computer");
		System.out.println("4	Add a new computer to the database");
		System.out.println("5	Update the selected computer");
		System.out.println("6	Delete the selected computer");
		System.out.println("0	Quit the computer-database CLI");
	}

}
