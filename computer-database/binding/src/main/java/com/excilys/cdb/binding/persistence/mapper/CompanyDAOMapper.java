package com.excilys.cdb.binding.persistence.mapper;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.persistence.dto.CompanyEntity;
import com.excilys.cdb.core.Company;

@Component
public class CompanyDAOMapper {

	public Company mapToCompany(CompanyEntity companyEntity) {
		return new Company.CompanyBuilder().setId(companyEntity.getId()).setName(companyEntity.getName()).build();
	}

}
