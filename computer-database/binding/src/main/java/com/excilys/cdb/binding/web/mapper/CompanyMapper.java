package com.excilys.cdb.binding.web.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.web.dto.CompanyDTO;
import com.excilys.cdb.core.Company;

@Component
public class CompanyMapper {

	public CompanyDTO mapToCompanyDTO(Optional<Company> company) {
		if (company.isPresent()) {
			return new CompanyDTO(String.valueOf(company.get().getId()), company.get().getName());
		}
		return null;
	}

}
