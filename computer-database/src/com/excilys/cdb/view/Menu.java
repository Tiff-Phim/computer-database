package com.excilys.cdb.view;

import java.sql.SQLException;

public class Menu {

	enum MenuCommand {
		LIST_ALL_COMPUTER(1),
		LIST_ALL_COMPANY(2),
		SHOW_COMPUTER_DETAIL(3),
		ADD_NEW_COMPUTER(4),
		UPDATE_COMPUTER(5),
		DELETE_COMPUTER(6),
		QUIT_CLI(0);
		
		private int value;
		
		MenuCommand(int value) {
			this.value = value;
		}
		
		public int getValue() {
			return value;
		}
	}
	
	private void processCommand(MenuCommand command) throws SQLException {
		switch (command) {
		case LIST_ALL_COMPUTER:
//			System.out.println(computerController.getAll().toString());
			break;
		case LIST_ALL_COMPANY:
//			System.out.println(companyController.getAll().toString());
			break;
		case SHOW_COMPUTER_DETAIL:
			System.out.println("Please enter an id.");
//			long id = sc.nextLong();
//			System.out.println(computerController.getComputerById(id));
			break;
		case ADD_NEW_COMPUTER:
//			computerController.insertComputer(updateComputer());
			System.out.println("Computer added to database.");
			break;
		case UPDATE_COMPUTER:
			System.out.println("Which computer do you want to update ? Enter it's id...");
//			long computerId = sc.nextLong();
//			computerController.updateComputer(updateComputer(), computerId);
			System.out.println("Computer updated.");
			break;
		case DELETE_COMPUTER:
//			sc.nextLine();
			System.out.println("Which computer do you want to delete ? Enter it's id...");
//			long computerIdToDelete = sc.nextLong();
//			computerController.deleteComputer(computerIdToDelete);
			System.out.println("Computer deleted.");
			break;
		case QUIT_CLI:
			System.out.println("Bye");
//			sc.close();
			System.exit(0);
		default:
			System.out.println("Unknown command. Please enter a valid command.");
		}
	}
	
	private void displayMenu() {
		System.out.println("Welcome to Computer-database CLI");
		System.out.println("Please choose and enter command number");
		System.out.println("1-List the computers stored in the database");
		System.out.println("2-List the companies stored in the database");
		System.out.println("3-Show details for the selected computer");
		System.out.println("4-Add a new computer to the database");
		System.out.println("5-Update the selected computer");
		System.out.println("6-Delete the selected computer");
		System.out.println("0-Quit the computer-database CLI");
	}
}
