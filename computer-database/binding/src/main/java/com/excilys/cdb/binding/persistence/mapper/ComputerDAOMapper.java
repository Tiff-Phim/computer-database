package com.excilys.cdb.binding.persistence.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.persistence.dto.CompanyEntity;
import com.excilys.cdb.binding.persistence.dto.ComputerEntity;
import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Computer;

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

	public ComputerEntity mapToComputer(Computer computer) {
		CompanyEntity company = null;
		if (computer.getCompanyId() != null) {
			company = new CompanyEntity(computer.getCompany().getId(), computer.getCompany().getName());
		}
		return new ComputerEntity(computer.getId(), computer.getName(), computer.getIntroduced(), computer.getDiscontinued(), company) ;
	}	

}
