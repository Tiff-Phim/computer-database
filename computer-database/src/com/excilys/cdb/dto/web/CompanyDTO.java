package com.excilys.cdb.dto.web;

import java.io.Serializable;

public class CompanyDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String name;

	public CompanyDTO(String id, String name) {
		this.setId(id);
		this.setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	
}