package com.excilys.cdb.mapper.dao;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.dao.ComputerEntity;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

@Component
public class ComputerDAOMapper {
	
	public Computer mapToComputer(ComputerEntity computerEntity) {
		Company company = new Company.CompanyBuilder().build();
		if (computerEntity.getCompany() != null) {
			company = new Company.CompanyBuilder().setId(computerEntity.getCompany().getId())
					.setName(computerEntity.getCompany().getName()).build();
		}
		Computer computer = new Computer.ComputerBuilder().setId(computerEntity.getId())
				.setName(computerEntity.getName())
				.setIntroduced(computerEntity.getIntroduced())
				.setDiscontinued(computerEntity.getDiscontinued()).setCompany(company)
				.build();
		return computer;
	}	

}
