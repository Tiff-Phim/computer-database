package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.excilys.cdb.model.Computer;

public class CLIController {
	
	private CompanyController companyController = new CompanyController();
	private ComputerController computerController = new ComputerController();
	
	private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private Scanner sc = new Scanner(System.in);

	
	/**
	 * Run CLIController.
	 * 
	 * @throws SQLException
	 */
	public void run() throws SQLException {
		while (true) {
			displayMenu();
			int command = sc.nextInt();
			processCommand(command);
		}
	}

	/**
	 * Display the menu with its options.
	 */
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

	private void processCommand(int command) throws SQLException {
		switch (command) {
		case 1:
			System.out.println(computerController.getAll().toString());
			break;
		case 2:
			System.out.println(companyController.getAll().toString());
			break;
		case 3:
			System.out.println("Please enter an id.");
			long id = sc.nextLong();
			System.out.println(computerController.getComputerById(id));
			break;
		case 4:
			computerController.insertComputer(updateComputer());
			System.out.println("Computer added to database.");
			break;
		case 5:
			System.out.println("Which computer do you want to update ? Enter it's id...");
			long computerId = sc.nextLong();
			computerController.updateComputer(updateComputer(), computerId);
			System.out.println("Computer updated.");
			break;
		case 6:
			sc.nextLine();
			System.out.println("Which computer do you want to delete ? Enter it's id...");
			long computerIdToDelete = sc.nextLong();
			computerController.deleteComputer(computerIdToDelete);
			System.out.println("Computer deleted.");
			break;
		case 0:
			System.out.println("Bye");
			sc.close();
			System.exit(0);
		default:
			System.out.println("Unknown command. Please enter a valid command.");
		}
	}
	
	/**
	 * Update a computer (update a field or create a computer).
	 * 
	 * @return computer to update or create
	 * @throws SQLException
	 */
	private Computer updateComputer() throws SQLException {
		Computer computer = new Computer();
		System.out.println("Please enter company name :");
		sc.nextLine();
		String name = sc.nextLine();
		computer.setCompanyId(companyController.getCompanyByName(name).getId());
		System.out.println("Please enter computer name :");
		String computerName = sc.nextLine();
		computer.setName(computerName);
		System.out.println("Please enter computer introduced date with the following format: yyyy-MM-dd");
		String introduced = sc.nextLine();
		computer.setIntroduced(LocalDate.parse(introduced, dtf));
		System.out.println("Please enter computer discontinued date with the following format: yyyy-MM-dd");
		String discontinued = sc.nextLine();
		if (!discontinued.isEmpty()) {
			computer.setIntroduced(LocalDate.parse(discontinued, dtf));
		}
		return computer;
	}

}
