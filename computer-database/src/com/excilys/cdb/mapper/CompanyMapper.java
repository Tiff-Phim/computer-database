package com.excilys.cdb.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.web.CompanyDTO;
import com.excilys.cdb.model.Company;

@Component
public class CompanyMapper {

	public CompanyDTO mapToCompanyDTO(Optional<Company> company) {
		if (company.isPresent()) {
			return new CompanyDTO(String.valueOf(company.get().getId()), company.get().getName());
		}
		return null;
	}

}
