package com.excilys.cdb.webapp.api;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.excilys.cdb.binding.validator.ComputerValidator;
import com.excilys.cdb.binding.web.dto.AddComputerDTO;
import com.excilys.cdb.binding.web.dto.ComputerDTO;
import com.excilys.cdb.binding.web.dto.EditComputerDTO;
import com.excilys.cdb.binding.web.mapper.ComputerMapper;
import com.excilys.cdb.core.Computer;
import com.excilys.cdb.core.Page;
import com.excilys.cdb.exception.InvalidComputerException;
import com.excilys.cdb.service.ComputerService;

@RestController
@RequestMapping("/api/computers")
public class ComputerController {

	private final ComputerService computerService;
	private final ComputerMapper mapper;
	private final ComputerValidator validator;

	public ComputerController(ComputerService computerService, ComputerMapper mapper, ComputerValidator validator) {
		this.computerService = computerService;
		this.mapper = mapper;
		this.validator = validator;
	}

	@GetMapping("/count")
	public Long getCount() {
		return computerService.getTotal();
	}
	
	@GetMapping
	public List<ComputerDTO> getComputers() {
		return computerService.getAllComputers().stream().map(c -> mapper.mapToComputerDTO(c)).collect(Collectors.toList());
	}
	
	@GetMapping("/{id}")
	public ComputerDTO getComputer(@PathVariable long id) {
		return mapper.mapToComputerDTO(computerService.getComputerById(id));
	}

	@GetMapping("/page")
	public List<ComputerDTO> getPage(@RequestParam(required = false, value = "index", defaultValue = "0") int index,
			@RequestParam(required = false) String search) {
		Page<Computer> page = null;
		if (search == null) {
			page = computerService.getComputerPaginatedByNameFilter(new Page<Computer>(Page.DEFAULT_PAGE_SIZE, index));
		} else {
			page = computerService.getComputerPaginatedByNameFilter(new Page<Computer>(Page.DEFAULT_PAGE_SIZE, index), search);
		}
		return page.getContent().stream().map(c -> mapper.mapToComputerDTO(c)).collect(Collectors.toList());
	}

	@GetMapping("/page/{index}")
	public List<ComputerDTO> getPageByIndex(@PathVariable int index,
			@RequestParam(required = false) String search) {
		Page<Computer> page = null;
		if (search == null) {
			page = computerService.getComputerPaginatedByNameFilter(new Page<Computer>(Page.DEFAULT_PAGE_SIZE, index));
		} else {
			page = computerService.getComputerPaginatedByNameFilter(new Page<Computer>(Page.DEFAULT_PAGE_SIZE, index), search);
		}
		return page.getContent().stream().map(c -> mapper.mapToComputerDTO(c)).collect(Collectors.toList());
	}
	
	@GetMapping("/page/{search}")
	public List<ComputerDTO> getPageByIndexAndSearch(@PathVariable String search) {
		Page<Computer> page = computerService.getComputerPaginatedByNameFilter(new Page<Computer>(Page.DEFAULT_PAGE_SIZE, Page.DEFAULT_PAGE_NUMBER), search);
		return page.getContent().stream().map(c -> mapper.mapToComputerDTO(c)).collect(Collectors.toList());
	}

	@PostMapping
	public AddComputerDTO addComputer(@RequestBody AddComputerDTO computer, BindingResult bindingResult) {
		validator.validate(computer, bindingResult);		
		if (!bindingResult.hasErrors()) {
			computerService.addComputer(mapper.mapAddComputerDTOToComputer(computer));
		} else {
			List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			throw new InvalidComputerException(errors);
		}
		return computer;
	}

	@PutMapping("/{id}")
	public EditComputerDTO updateComputer(@PathVariable long id, @RequestBody @Valid EditComputerDTO computer, BindingResult bindingResult) {
		validator.validate(computer, bindingResult);
		if (!bindingResult.hasErrors()) {
			computerService.updateComputer(mapper.mapEditComputerDTOToComputer(computer), id);
		} else {
			List<String> errors = bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
			throw new InvalidComputerException(errors);
		}
		return computer;
	}

	@DeleteMapping("/{id}")
	public void deleteComputer(@PathVariable long id) {
		computerService.deleteComputer(id);
	}

}
