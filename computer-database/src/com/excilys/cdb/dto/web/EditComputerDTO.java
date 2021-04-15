package com.excilys.cdb.dto.web;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.springframework.format.annotation.DateTimeFormat;

public class EditComputerDTO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@NotNull
	public String id;
	@NotEmpty(message = "{computer.name.required}")
	@Pattern(regexp = "^[a-zA-Z].*", message = "{computer.name.invalid}")
	public String name;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public String introduced;
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	public String discontinued;
	public String companyId;
	
	public EditComputerDTO() {
	}
	
	public EditComputerDTO(String computerId, String computerName, String introducedDate, String discontinuedDate,
			String companyId) {
		this.id = computerId;
		this.name = computerName;
		this.introduced = introducedDate;
		this.discontinued = discontinuedDate;
		this.companyId = companyId;
	}

	public String getId() {
		return id;
	}
	
	public String getName() {
		return name;
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

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setIntroduced(String introduced) {
		this.introduced = introduced;
	}

	public void setDiscontinued(String discontinued) {
		this.discontinued = discontinued;
	}

	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	
}
