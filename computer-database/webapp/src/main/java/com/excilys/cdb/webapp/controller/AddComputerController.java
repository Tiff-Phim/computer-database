package com.excilys.cdb.webapp.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.binding.validator.ComputerValidator;
import com.excilys.cdb.binding.web.dto.AddComputerDTO;
import com.excilys.cdb.binding.web.dto.CompanyDTO;
import com.excilys.cdb.binding.web.mapper.CompanyMapper;
import com.excilys.cdb.binding.web.mapper.ComputerMapper;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

@Controller
@RequestMapping("/addComputer")
public class AddComputerController {

	private static final String VIEW_FORM = "addComputer";
	
	private static final String ATT_COMPANY_LIST = "companyList";
	private static final String ATT_COMPUTER = "computer";

	private final ComputerService computerService;
	private final CompanyService companyService;
	private final ComputerMapper computerMapper;
	private final CompanyMapper companyMapper;
	private final ComputerValidator validator;

	public AddComputerController(ComputerService computerService, CompanyService companyService,
			ComputerMapper computerMapper, CompanyMapper companyMapper, ComputerValidator validator) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.companyMapper = companyMapper;
		this.validator = validator;
	}

	@GetMapping
	public ModelAndView getAddComputer() {
		ModelAndView mv = new ModelAndView(VIEW_FORM);
		mv.addObject(ATT_COMPUTER, new AddComputerDTO());
		addCompanyList(mv);
		return mv;
	}

	@PostMapping
	public ModelAndView postAddComputer(@ModelAttribute(ATT_COMPUTER) @Valid AddComputerDTO computer, BindingResult br) {
		ModelAndView mv = new ModelAndView(VIEW_FORM);
		validator.validate(computer, br);
		if(!br.hasErrors()) {
			computerService.addComputer(computerMapper.mapAddComputerDTOToComputer(computer));
			return new ModelAndView("redirect:/dashboard");
		}
		addCompanyList(mv);
		return mv;
	}

	private void addCompanyList(ModelAndView mv) {
		List<CompanyDTO> companies = companyService.getAllCompany().stream().map(c -> companyMapper.mapToCompanyDTO(c))
				.collect(Collectors.toList());
		mv.addObject(ATT_COMPANY_LIST, companies);
	}

}
