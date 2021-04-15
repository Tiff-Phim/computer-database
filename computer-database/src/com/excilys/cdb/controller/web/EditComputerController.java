package com.excilys.cdb.controller.web;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.dto.web.CompanyDTO;
import com.excilys.cdb.dto.web.EditComputerDTO;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.validator.ComputerValidator;

@Controller
@RequestMapping("/editComputer")
public class EditComputerController {

	private static final String VIEW_FORM = "editComputer";

	private static final String ATT_COMPANY_LIST = "companyList";
	private static final String ATT_COMPUTER = "computer";
	private static final String ATT_COMPUTER_FORM = "computerToEdit";

	private final ComputerService computerService;
	private final CompanyService companyService;
	private final ComputerMapper computerMapper;
	private final CompanyMapper companyMapper;
	private final ComputerValidator validator;

	public EditComputerController(ComputerService computerService, CompanyService companyService,
			ComputerMapper computerMapper, CompanyMapper companyMapper, ComputerValidator validator) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.computerMapper = computerMapper;
		this.companyMapper = companyMapper;
		this.validator = validator;
	}

	@GetMapping
	public ModelAndView getEditComputer(@RequestParam long computerId) {
		ModelAndView mv = new ModelAndView(VIEW_FORM);
		mv.addObject(ATT_COMPUTER_FORM, new EditComputerDTO());
		addCompanyList(mv);
		if (computerId != 0) {
			Optional<Computer> computer = computerService.getComputerById(computerId);
			mv.addObject(ATT_COMPUTER, computerMapper.mapComputerToEditComputerDTO(computer));
		}
		return mv;
	}

	@PostMapping
	public ModelAndView postEditComputer(@ModelAttribute(ATT_COMPUTER_FORM) @Valid EditComputerDTO computer, BindingResult br) {
		ModelAndView mv = new ModelAndView(VIEW_FORM);
		validator.validate(computer, br);
		if(!br.hasErrors()) {
			this.computerService.updateComputer(this.computerMapper.mapEditComputerDTOToComputer(computer), Long.valueOf(computer.getId()));
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
