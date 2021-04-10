package com.excilys.cdb.mapper;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

@Component
public class CompanyMapper {
	
	/**
	 * Makes the correspondence Company to CompanyDTO.
	 * 
	 * @param company
	 * @return
	 */
	public CompanyDTO mapToCompanyDTO(Optional<Company> company) {
		if (company.isPresent()) {
			return new CompanyDTO(String.valueOf(company.get().getId()), company.get().getName());
		}
		return null;
	}
	
}
