package com.excilys.cdb.model;

import java.time.LocalDate;

/**
 * Computer class defines a computer object, name, date when it was introduced
 * and eventually the date when it was discontinued, and the manufacturer.
 * 
 * @author Tiffany PHIMMASANE
 * @version 0.3
 */
public class Computer {

	private long id;

	public void setIntroduced(LocalDate introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(LocalDate discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	private String name;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;

	/**
	 * Constructs a new Computer with its name, date introduced, date discontinued
	 * and companyId.
	 * 
	 * @param id
	 * @param name
	 * @param introduced
	 * @param discontinued
	 * @param company
	 */
	private Computer(long id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
		this.id = id;
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.company = company;
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
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Fetches the computer date of introduction.
	 * 
	 * @return introduced date of introduction
	 */
	public LocalDate getIntroduced() {
		return introduced;
	}

	/**
	 * Fetches the computer date when it was discontinued.
	 * 
	 * @return discontinued date the computer was discontinued
	 */
	public LocalDate getDiscontinued() {
		return discontinued;
	}

	/**
	 * Fetches the associated company.
	 * 
	 * @return company associated to the computer
	 */
	public Company getCompany() {
		return company;
	}
	
	public Long getCompanyId() {
		if (company != null && (company.getId() != 0)) {
			return company.getId();
		}
		return null;
	}

	public static class ComputerBuilder {
		
		private long id;
		private String name;
		private LocalDate introduced;
		private LocalDate discontinued;
		private Company company;
		
		/**
		 * Sets the computer id.
		 * 
		 * @param id of the computer
		 */
		public ComputerBuilder setId(long id) {
			this.id = id;
			return this;
		}
		
		/**
		 * Sets the computer name.
		 * 
		 * @param name of the computer
		 */
		public ComputerBuilder setName(String name) {
			this.name = name;
			return this;
		}
		
		/**
		 * Sets the computer date introduction.
		 * 
		 * @param introduced date of introduction
		 */
		public ComputerBuilder setIntroduced(LocalDate introduced) {
			this.introduced = introduced;
			return this;
		}
		
		/**
		 * Sets the computer date when it was discontinued.
		 * 
		 * @param discontinued date the computer was discontinued
		 */
		public ComputerBuilder setDiscontinued(LocalDate discontinued) {
			this.discontinued = discontinued;
			return this;
		}
		
		/**
		 * Sets the company.
		 * 
		 * @param company associated to the computer
		 */
		public ComputerBuilder setCompany(Company company) {
			this.company = company;
			return this;
		}
		
		public Computer build() {
			return new Computer(id, name, introduced, discontinued, company);
		}
		
	}

	/**
	 * Generates a textual representation of a Computer object.
	 * 
	 * @return computer textual representation
	 */
	@Override
	public String toString() {
		return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
				+ ", company=" + company + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((company == null) ? 0 : company.hashCode());
		result = prime * result + ((discontinued == null) ? 0 : discontinued.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((introduced == null) ? 0 : introduced.hashCode());
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
		Computer other = (Computer) obj;
		if (company == null) {
			if (other.company != null) {
				return false;
			}
		} else if (!company.equals(other.company)) {
			return false;
		}
		if (discontinued == null) {
			if (other.discontinued != null) {
				return false;
			}
		} else if (!discontinued.equals(other.discontinued)) {
			return false;
		}
		if (id != other.id) {
			return false;
		}
		if (introduced == null) {
			if (other.introduced != null) {
				return false;
			}
		} else if (!introduced.equals(other.introduced)) {
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
