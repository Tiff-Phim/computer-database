package com.excilys.cdb.mapper;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Optional;

import com.excilys.cdb.dto.AddComputerDTO;
import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.dto.EditComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerMapper {

	private static final String ATTRIBUT_ID = "computer.id";
	private static final String ATTRIBUT_COMPUTER_NAME = "computer.name";
	private static final String ATTRIBUT_INTRODUCED = "computer.introduced";
	private static final String ATTRIBUT_DISCONTINUED = "computer.discontinued";
	private static final String ATTRIBUT_COMPANY_NAME = "company.name";
	private static final String ATTRIBUT_COMPANY_ID = "computer.company_id";

	/**
	 * Makes the correspondence between a line from the database table (a ResultSet)
	 * and a Company bean.
	 * 
	 * @param resultSet line from the database table
	 * @return bean of the Company
	 * @throws SQLException
	 */
	public Optional<Computer> mapToComputer(ResultSet resultSet) throws SQLException {
		Company company = new Company.CompanyBuilder().setId(resultSet.getLong(ATTRIBUT_COMPANY_ID))
				.setName(resultSet.getString(ATTRIBUT_COMPANY_NAME)).build();

		Computer computer = new Computer.ComputerBuilder().setId(resultSet.getLong(ATTRIBUT_ID))
				.setName(resultSet.getString(ATTRIBUT_COMPUTER_NAME))
				.setIntroduced(convertToLocalDate(resultSet.getDate(ATTRIBUT_INTRODUCED)))
				.setDiscontinued(convertToLocalDate(resultSet.getDate(ATTRIBUT_DISCONTINUED))).setCompany(company)
				.build();

		return Optional.ofNullable(computer);
	}

	public Computer mapToComputer(ComputerDTO computer) {
		Company company = new Company.CompanyBuilder().setId(Long.valueOf(computer.getCompanyName())).build();

		return new Computer.ComputerBuilder().setName(computer.getName())
				.setIntroduced(LocalDate.parse(computer.getIntroduced()))
				.setDiscontinued(LocalDate.parse(computer.getDiscontinued())).setCompany(company).build();
	}

	public ComputerDTO mapToComputerDTO(Optional<Computer> computer) {
		if (computer.isPresent()) {
			return new ComputerDTO(computer.get().getId(), computer.get().getName(),
					computer.get().getIntroduced() != null ? computer.get().getIntroduced().toString() : "Unknown",
					computer.get().getDiscontinued() != null ? computer.get().getDiscontinued().toString() : "Unknown",
					computer.get().getCompany().getName() != null ? computer.get().getCompany().getName() : "Unknown");
		}
		return null;
	}

	public Computer mapAddComputerDTOToComputer(AddComputerDTO computerDTO) {
		Company company = new Company.CompanyBuilder().setId(Long.valueOf(computerDTO.getCompanyId())).build();
		
		return new Computer.ComputerBuilder().setName(computerDTO.getName())
				.setIntroduced(computerDTO.getIntroduced().isEmpty() ? null : LocalDate.parse(computerDTO.getIntroduced()))
				.setDiscontinued(computerDTO.getDiscontinued().isEmpty() ? null : LocalDate.parse(computerDTO.getDiscontinued()))
				.setCompany(company).build();
	}
	
	public Computer mapEditComputerDTOToComputer(EditComputerDTO computerDTO) {
		Company company = new Company.CompanyBuilder().setId(Long.valueOf(computerDTO.getCompanyId())).build();

		return new Computer.ComputerBuilder().setName(computerDTO.getComputerName())
				.setIntroduced(LocalDate.parse(computerDTO.getIntroduced()))
				.setDiscontinued(LocalDate.parse(computerDTO.getDiscontinued())).setCompany(company).build();
	}

	private LocalDate convertToLocalDate(Date date) {
		LocalDate localDate = null;
		if (date != null) {
			localDate = date.toLocalDate();
		}
		return localDate;
	}

}