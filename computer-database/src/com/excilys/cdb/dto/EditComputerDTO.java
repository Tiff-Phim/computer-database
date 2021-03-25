package com.excilys.cdb.dto;

public class EditComputerDTO {
	
	public String computerId;
	public String computerName;
	public String introduced;
	public String discontinued;
	public String companyId;
	
	public EditComputerDTO(String computerId, String computerName, String introducedDate, String discontinuedDate,
			String companyId) {
		this.computerId = computerId;
		this.computerName = computerName;
		this.introduced = introducedDate;
		this.discontinued = discontinuedDate;
		this.companyId = companyId;
	}

	public String getComputerId() {
		return computerId;
	}
	
	public String getComputerName() {
		return computerName;
	}
	
	public String getIntroduced() {
		return introduced;
	}
	
	public String getDiscontinued() {
		return discontinued;
	}
	
	public String getCompanyId() {
		return companyId;
	}
	
}
