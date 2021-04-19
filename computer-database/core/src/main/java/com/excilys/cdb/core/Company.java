package com.excilys.cdb.core;

/**
 * Company class defines a company object with its name.
 * 
 * @author Tiffany PHIMMASANE
 * @version 0.2
 */
public class Company {

	private long id;
	private String name;

	/**
	 * Construct a new company with its id and name.
	 * 
	 * @param id of the company
	 * @param name of the company
	 */
	private Company(long id, String name) {
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
	 * @param name  of the company
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public static class CompanyBuilder {
		
		private long id;
		private String name;
		
		public CompanyBuilder setId(long id) {
			this.id = id;
			return this;
		}
		
		public CompanyBuilder setName(String name) {
			this.name = name;
			return this;
		}
		
		public Company build() {
			return new Company(id, name);
		}
	}
	
	@Override
	public String toString() {
		return "Company [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Company other = (Company) obj;
		if (id != other.id) {
			return false;
		}
		if (name == null) {
			if (other.name != null) {
				return false;
			}
		} else if (!name.equals(other.name)) {
			return false;
		}
		return true;
	}
	
}
