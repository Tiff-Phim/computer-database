package com.excilys.cdb.dto;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class AddComputerDTO {

	@NotEmpty(message = "{computer.name.required}")
	@Pattern(regexp = "^[a-zA-Z].*", message = "{computer.name.invalid}")
	private String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String introduced;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private String discontinued;
	private String companyId;

	public AddComputerDTO() {
	}

	public AddComputerDTO(String name, String introduced, String discontinued, String companyId) {
		this.name = name;
		this.introduced = introduced;
		this.discontinued = discontinued;
		this.companyId = companyId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIntroduced() {
		return introduced;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public String getDiscontinued() {
		return discontinued;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public String getCompanyId() {
		return companyId;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}

}
