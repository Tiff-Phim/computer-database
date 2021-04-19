package com.excilys.cdb.console.controller;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

import org.springframework.stereotype.Controller;

import com.excilys.cdb.console.view.Menu;
import com.excilys.cdb.console.view.MenuCommand;
import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.Page;

@Controller
public class CLIController {

	private static final String PREVIOUS_PAGE = "-";
	private static final String NEXT_PAGE = "+";
	private static final String QUIT_PAGINATED_MENU = "0";

	private final CompanyController companyController;
	private final ComputerController computerController;

	private Menu menu = new Menu();
	private final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private static int pageNumber = 1;
	
	private Scanner sc = new Scanner(System.in);
	
	public CLIController(CompanyController companyController, ComputerController computerController) {
		this.companyController = companyController;
		this.computerController = computerController;
	}
	
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
		MenuCommand menuCommand = MenuCommand.fromCommand(command);
		switch (menuCommand) {
		case LIST_COMPUTER:
			Page<Computer> page = new Page<Computer>(10, pageNumber);
			menu.showComputerPaginated(computerController.getComputerPaginatedByNameFilter(page));
			System.out.println("Please use - or + to navigate through pages ...");
			sc.nextLine();
			String event = sc.nextLine();
			do {
				handlePagination(event, computerController.getComputerPaginatedByNameFilter(page));
				System.out.println("Please use - or + to navigate through pages ...");
				event = sc.nextLine();
			} while (event.length() > 0);
			break;
		case LIST_COMPANY:
			Page<Company> companyPage = new Page<Company>(5, 1);
			menu.showComputerPaginated(companyController.getCompanyPaginated(companyPage));
			System.out.println("Please use - or + to navigate through pages ...");
			sc.nextLine();
//			String event = sc.nextLine();
//			handlePagination(event, companyController.getCompanyPaginated(companyPage));
			break;
		case SHOW_COMPUTER_DETAIL:
			menu.printElement("Please enter an id.");
			long id = sc.nextLong();
			menu.showComputerDetails(computerController.getComputerById(id));
			break;
		case ADD_NEW_COMPUTER:
			computerController.insertComputer(updateComputer());
			System.out.println("Computer added to database.");
			break;
		case UPDATE_COMPUTER:
			menu.printElement("Which computer do you want to update? Enter it's id...");
			long computerId = sc.nextLong();
			computerController.updateComputer(updateComputer(), computerId);
			System.out.println("Computer updated.");
			break;
		case DELETE_COMPUTER:
			sc.nextLine();
			menu.printElement("Which computer do you want to delete? Enter it's id...");
			long computerIdToDelete = sc.nextLong();
			computerController.deleteComputer(computerIdToDelete);
			System.out.println("Computer deleted.");
			break;
		case DELETE_COMPANY:
			sc.nextLine();
			menu.printElement("Which company do you want to delete? Please enter it's id ...");
			long companyIdToDelete = sc.nextLong();
			companyController.deleteCompanyById(companyIdToDelete);
			System.out.println("Company deleted.");
			break;
		case QUIT_CLI:
			System.out.println("Bye");
			sc.close();
			System.exit(0);
		default:
			menu.printElement("Unknown command. Please enter a valid command.");
		}
	}

	private <E> void handlePagination(String event, Page<Computer> page) throws SQLException {
		switch (event) {
		case PREVIOUS_PAGE:
			menu.showComputerPaginated(computerController.getComputerPaginatedByNameFilter(page.getPreviousPage()));
			break;
		case NEXT_PAGE:
			menu.showComputerPaginated(computerController.getComputerPaginatedByNameFilter(page.getNextPage()));
			break;
		case QUIT_PAGINATED_MENU:
			break;
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
				.setIntroduced(LocalDate.parse(introduced, dtf)).setDiscontinued(LocalDate.parse(discontinued, dtf))
				.build();
	}

}