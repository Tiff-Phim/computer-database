package com.excilys.cdb.servlet.validator;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import com.excilys.cdb.dto.AddComputerDTO;

public class AddComputerValidator {
	
	private static final String COMPUTER_NAME_FORMAT = "^[a-zA-Z].*";
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	
	private static final String ATT_COMPUTER_NAME = "computerName";
	private static final String ATT_DISCONTINUED_DATE = "discontinued";
	private static final String ATT_COMPANY_ID = "companyId";
	
	public Map<String, String> validateComputer(AddComputerDTO computer) {
		Map<String, String> errors = new HashMap<String, String>();
		if (!nameValidator(computer.getName())) {
			errors.put(ATT_COMPUTER_NAME, "The name specified should begin with a letter.");
		}
		if (!dateValidator(computer.getIntroduced(), computer.getDiscontinued())) {
			errors.put(ATT_DISCONTINUED_DATE, "The discontinued date can't be before the introduced date.");
		}
		if (!companyValidator(computer.getCompanyId())) {
			errors.put(ATT_COMPANY_ID, "Invalid company. Please select a valid company.");
		}
		return errors;
	}
	
	private boolean nameValidator(String computerName) {
		if (computerName != null && computerName.matches(COMPUTER_NAME_FORMAT)) {
			return true;
		}
		return false;
	}
	
	private boolean dateValidator(String introduced, String discontinued) {
		if (introduced != null && discontinued != null) {
			LocalDate dateDiscontinued = LocalDate.parse(discontinued, DateTimeFormatter.ofPattern(DATE_FORMAT));
			LocalDate dateIntroduced = LocalDate.parse(introduced, DateTimeFormatter.ofPattern(DATE_FORMAT));
			if (dateIntroduced.isBefore(dateDiscontinued)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean companyValidator(String companyId) {
		if (companyId != null) {
			int company = Integer.parseInt(companyId);
			if (company <= 0) {
				return false;
			}
		}
		return true;
	}

}
