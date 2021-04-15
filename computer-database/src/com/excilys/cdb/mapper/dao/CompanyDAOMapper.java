package com.excilys.cdb.mapper.dao;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.dao.CompanyEntity;
import com.excilys.cdb.model.Company;

@Component
public class CompanyDAOMapper {

	public Company mapToCompany(CompanyEntity companyEntity) {
		return new Company.CompanyBuilder().setId(companyEntity.getId()).setName(companyEntity.getName()).build();
	}

}
