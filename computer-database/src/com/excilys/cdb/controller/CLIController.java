package com.excilys.cdb.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.view.Menu;

public class CLIController {

	private CompanyController companyController = new CompanyController();
	private ComputerController computerController = new ComputerController();

	private Menu menu = new Menu();
	private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	private Scanner sc = new Scanner(System.in);

	/**
	 * Run CLIController.
	 * 
	 * @throws SQLException
	 */
	public void run() throws SQLException {
		while (true) {
			menu.showMenu();
			int command = sc.nextInt();
			processCommand(command);
		}
	}

	private void processCommand(int command) throws SQLException {
		switch (command) {
		case 1:
			menu.showComputers(computerController.getAll());
			break;
		case 2:
			menu.showCompanies(companyController.getAll());
			break;
		case 3:
			menu.printElement("Please enter an id.");
			long id = sc.nextLong();
			menu.showComputerDetails(computerController.getComputerById(id));
			break;
		case 4:
			computerController.insertComputer(updateComputer());
			System.out.println("Computer added to database.");
			break;
		case 5:
			menu.printElement("Which computer do you want to update ? Enter it's id...");
			long computerId = sc.nextLong();
			 computerController.updateComputer(updateComputer(), computerId);
			System.out.println("Computer updated.");
			break;
		case 6:
			sc.nextLine();
			menu.printElement("Which computer do you want to delete ? Enter it's id...");
			long computerIdToDelete = sc.nextLong();
			computerController.deleteComputer(computerIdToDelete);
			System.out.println("Computer deleted.");
			break;
		case 0:
			System.out.println("Bye");
			sc.close();
			System.exit(0);
		default:
			menu.printElement("Unknown command. Please enter a valid command.");
		}
	}

	/**
	 * Update a computer (update a field or create a computer).
	 * 
	 * @return computer to update or create
	 * @throws SQLException
	 */
	private Computer updateComputer() throws SQLException {
		menu.printElement("Please enter company id :");
		sc.nextLine();
		Long companyId = sc.nextLong();
		sc.nextLine();
		Company company = new Company.CompanyBuilder().setId(companyId).build();
		menu.printElement("Please enter computer name :");
		String computerName = sc.nextLine();
		menu.printElement("Please enter computer introduced date with the following format: yyyy-MM-dd");
		String introduced = sc.nextLine();
		System.out.println("Please enter computer discontinued date with the following format: yyyy-MM-dd");
		String discontinued = sc.nextLine();
		return new Computer.ComputerBuilder().setCompany(company).setName(computerName)
				.setIntroduced(LocalDate.parse(introduced, dtf)).setDiscontinued(LocalDate.parse(discontinued, dtf)).build();
	}

}