package com.excilys.cdb.validator;

import java.time.LocalDate;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.excilys.cdb.dto.web.AddComputerDTO;
import com.excilys.cdb.dto.web.EditComputerDTO;

@Component
public class ComputerValidator implements Validator {

	private static final String COMPUTER_NAME_FORMAT = "^[a-zA-Z].*";
	
	@Override
	public boolean supports(Class<?> clazz) {
		return AddComputerDTO.class.equals(clazz) || EditComputerDTO.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "computer.name.required", "default.required");
		if (target instanceof AddComputerDTO) {
			AddComputerDTO computerDto = (AddComputerDTO) target;
			nameValidator(errors, computerDto.getName());
			dateValidator(errors, computerDto.getIntroduced(), computerDto.getDiscontinued());
			companyValidator(errors, computerDto.getCompanyId());
		} else {
			EditComputerDTO computerDto = (EditComputerDTO) target;
			nameValidator(errors, computerDto.getName());
			dateValidator(errors, computerDto.getIntroduced(), computerDto.getDiscontinued());
			companyValidator(errors, computerDto.getCompanyId());
		}
	}
	
	private void nameValidator(Errors errors, String name) {
		if (name != null && !name.matches(COMPUTER_NAME_FORMAT)) {
			errors.rejectValue("name", "computer.name.invalid", "default.invalid");
		}
	}
	
	private void dateValidator(Errors errors, String start, String end) {
		if (!start.isEmpty() && start != null) {
			LocalDate dateIntroduced = LocalDate.parse(start);
			if (!end.isEmpty() && end != null) {
				LocalDate dateDiscontinued = LocalDate.parse(end);
				if (dateIntroduced.isAfter(dateDiscontinued)) {
					errors.rejectValue("discontinued", "computer.discontinued.invalid", "default.invalid");
				}
			}
		}
	}
	
	private void companyValidator(Errors errors, String companyId) {
		if (companyId != null) {
			int company = Integer.parseInt(companyId);
			if (company < 0) {
				errors.rejectValue("companyId", "computer.company.invalid", "default.invalid");
			}
		}
	}

}
