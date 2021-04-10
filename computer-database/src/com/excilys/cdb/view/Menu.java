package com.excilys.cdb.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;

public class Menu {

	private static final String START_LINE = ">>";
	private static final String VERTICAL_SEP = "";
	private static final String WELCOME_MESSAGE = "Welcome to Computer-database CLI";
	private static final String CHOOSE_ACTION_MESSAGE = "Please choose and enter command number";

	public void showMenu() {
		System.out.println(WELCOME_MESSAGE);
		System.out.println(CHOOSE_ACTION_MESSAGE);
		CLITable st = new CLITable();
		st.setShowVerticalLines(true);
		st.setHeaders("Value", "Description");
		st.addRow(String.valueOf(MenuCommand.LIST_COMPUTER.value), MenuCommand.LIST_COMPUTER.description);
		st.addRow(String.valueOf(MenuCommand.LIST_COMPANY.value), MenuCommand.LIST_COMPANY.description);
		st.addRow(String.valueOf(MenuCommand.SHOW_COMPUTER_DETAIL.value), MenuCommand.SHOW_COMPUTER_DETAIL.description);
		st.addRow(String.valueOf(MenuCommand.ADD_NEW_COMPUTER.value), MenuCommand.ADD_NEW_COMPUTER.description);
		st.addRow(String.valueOf(MenuCommand.UPDATE_COMPUTER.value), MenuCommand.UPDATE_COMPUTER.description);
		st.addRow(String.valueOf(MenuCommand.DELETE_COMPUTER.value), MenuCommand.DELETE_COMPUTER.description);
		st.addRow(String.valueOf(MenuCommand.DELETE_COMPANY.value), MenuCommand.DELETE_COMPANY.description);
		st.addRow(String.valueOf(MenuCommand.QUIT_CLI.value), MenuCommand.QUIT_CLI.description);
		st.print();
	}

	public void showCompanies(List<Optional<Company>> list) {
		CLITable st = new CLITable();
		st.setShowVerticalLines(true);
		st.setHeaders("Id", "Name");
		for (Optional<Company> company : list) {
			if (company.isPresent()) {
				st.addRow(String.valueOf(company.get().getId()), company.get().getName());
			}
		}
		st.print();
	}

	public void showComputers(List<Optional<Computer>> list) {
		CLITable st = new CLITable();
		st.setShowVerticalLines(true);
		st.setHeaders("Id", "Name", "Introduced date", "Discontinued date", "Company name");
		for (Optional<Computer> computer : list) {
			if (computer.isPresent()) {
				String companyName = "Unknow";
				if (computer.get().getCompany().getId() != 0l) {
					companyName = computer.get().getCompany().getName();
				}
				st.addRow(String.valueOf(computer.get().getId()), computer.get().getName(),
						String.valueOf(computer.get().getIntroduced()),
						String.valueOf(computer.get().getDiscontinued()), companyName);
			}
		}
		st.print();
	}

	public void showComputerDetails(Optional<Computer> computer) {
		CLITable st = new CLITable();
		st.setShowVerticalLines(true);
		st.setHeaders("Id", "Name", "Introduced date", "Discontinued date", "Company name");
		if (computer.isPresent()) {
			st.addRow(String.valueOf(computer.get().getId()), computer.get().getName(),
					String.valueOf(computer.get().getIntroduced()), String.valueOf(computer.get().getDiscontinued()),
					computer.get().getCompany().getName());
		}
		st.print();
	}

	public void printElement(String element) {
		System.out.println(VERTICAL_SEP);
		System.out.println(element);
		System.out.println(START_LINE);
	}
	
	public <E> void showComputerPaginated(Page<E> page) {
		printPage(page);
	}
	
	private <T> void printPage(Page<T> page) {
		System.out.println("Printing page ...");
		List<Optional<Computer>> listComputers = new ArrayList<>();
		for (Optional<T> content : page.getContent()) {
			Optional<Computer> computer = (Optional<Computer>) Optional.ofNullable(content.get());
			listComputers.add(computer);
		}
		showComputers(listComputers);
	}

}
