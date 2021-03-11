package com.excilys.cdb.model;

/**
 * Company class defines a company object with its name.
 * 
 * @author Tiffany PHIMMASANE
 * @version 0.1
 */
public class Company {

	private long id;
	private String name;

	/**
	 * Default Company constructor.
	 */
	public Company() {
		super();
	}

	/**
	 * Construct a new company with its name.
	 * 
	 * @param name of the company
	 */
	public Company(String name) {
		super();
		this.name = name;
	}
	
	/**
	 * Construct a new company with its id and name.
	 * 
	 * @param id of the company
	 * @param name of the company
	 */
	public Company(long id, String name) {
		super();
		this.id = id;
		this.name = name;
	}
	
	/**
	 * Fetches the company id.
	 * 
	 * @return id of the company
	 */
	public long getId() {
		return id;
	}

	/**
	 * Sets the company id.
	 * 
	 * @param id of the company
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * Fetches the company name.
	 * 
	 * @return name of the company
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the company name.
	 * 
	 * @param name of the company
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Generates a textual representation of a Company object.
	 * 
	 * @return company textual representation
	 */
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}
	
}
