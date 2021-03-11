package com.excilys.cdb.model;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Computer class defines a computer object, name, date when it was introduced
 * and eventually the date when it was discontinued, and the manufacturer.
 * 
 * @author Tiffany PHIMMASANE
 * @version 0.1
 */
public class Computer {

	private long id;
	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private long companyId;

	/**
	 * Default Computer constructor.
	 */
	public Computer() {
		super();
	}

	/**
	 * Constructs a new Computer with its name, date introduced, date discontinued
	 * and companyId.
	 * 
	 * @param id
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param companyId
	 */
	public Computer(long id, String name, LocalDate introduced, LocalDate discontinued, long companyId) {
		super();
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}

	/**
	 * Fetches the computer id.
	 * 
	 * @return if of the computer
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the computer id.
	 * 
	 * @param id of the computer
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Fetches the name of the computer.
	 * 
	 * @return name of the computer
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the computer name.
	 * 
	 * @param name of the computer
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Fetches the computer date of introduction.
	 * 
	 * @return introduced date of introduction
	 */
	public Optional<LocalDate> getIntroduced() {
		return Optional.ofNullable(introduced);
	}

	/**
	 * Sets the computer date introduction.
	 * 
	 * @param introduced date of introduction
	 */
	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	/**
	 * Fetches the computer date when it was discontinued.
	 * 
	 * @return discontinued date the computer was discontinued
	 */
	public Optional<LocalDate> getDiscontinued() {
		return Optional.ofNullable(discontinued);
	}

	/**
	 * Sets the computer date when it was discontinued.
	 * 
	 * @param discontinued date the computer was discontinued
	 */
	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	/**
	 * Fetches the company identification.
	 * 
	 * @return companyId identification of the company
	 */
	public long getCompanyId() {
		return companyId;
	}

	/**
	 * Sets the company identification.
	 * 
	 * @param companyId identification of the company
	 */
	public void setCompanyId(long companyId) {
		this.companyId = companyId;
	}

	/**
	 * Generates a textual representation of a Computer object.
	 * 
	 * @return computer textual representation
	 */
	@Override
	public String toString() {
		return "Computer [name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", companyId=" + companyId + "]";
	}
	
}
