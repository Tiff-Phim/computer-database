package com.excilys.cdb.webapp.api;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.binding.web.dto.CompanyDTO;
import com.excilys.cdb.binding.web.mapper.CompanyMapper;
import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Page;
import com.excilys.cdb.service.CompanyService;

@RestController
@RequestMapping("/api/companies")
public class CompanyController {

	private final CompanyService companyService;
	private final CompanyMapper mapper;

	public CompanyController(CompanyService companyService, CompanyMapper mapper) {
		this.companyService = companyService;
		this.mapper = mapper;
	}

	@GetMapping
	public List<CompanyDTO> getCompanies() {
		return companyService.getAllCompany().stream().map(c -> mapper.mapToCompanyDTO(c)).collect(Collectors.toList());
	}
	
	@GetMapping("/page")
	public List<CompanyDTO> getPage(@RequestParam(required = false, value = "index", defaultValue = "0") int index) {
		Page<Company> page = companyService.getCompanyPaginated(new Page<Company>(Page.DEFAULT_PAGE_SIZE, index));
		return page.getContent().stream().map(c -> mapper.mapToCompanyDTO(c)).collect(Collectors.toList());
	}
	
	@GetMapping("/page/{index}")
	public List<CompanyDTO> getPageByIndex(@PathVariable int index) {
		Page<Company> page = companyService.getCompanyPaginated(new Page<Company>(Page.DEFAULT_PAGE_SIZE, index));
		return page.getContent().stream().map(c -> mapper.mapToCompanyDTO(c)).collect(Collectors.toList());
	}

	@DeleteMapping("/{id}")
	public void deleteCompany(@PathVariable long id) {
		companyService.deleteCompanyById(id);
	}
}
