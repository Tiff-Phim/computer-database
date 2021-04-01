package com.excilys.cdb.view;

public enum MenuCommand {

	LIST_ALL_COMPUTER(1, "List all computers."), LIST_COMPUTER(11, "List computers paginated view."),
	LIST_ALL_COMPANY(2, "List all companies."), LIST_COMPANY(22, "List companies paginated view."),
	SHOW_COMPUTER_DETAIL(3, "Show computer details."), ADD_NEW_COMPUTER(4, "Add new computer."),
	UPDATE_COMPUTER(5, "Update a computer."), DELETE_COMPUTER(6, "Delete a computer."),
	DELETE_COMPANY(7, "Delete a company."), QUIT_CLI(0, "Quit.");

	public final int value;
	public final String description;

	MenuCommand(int value, String description) {
		this.value = value;
		this.description = description;
	}

	public int getValue() {
		return value;
	}

	public String getDescription() {
		return description;
	}

	public static MenuCommand fromCommand(int command) {
		for (MenuCommand menu : values()) {
			if (menu.getValue() == command) {
				return menu;
			}
		}
		return null;
	}

}
