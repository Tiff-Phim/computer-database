package com.excilys.cdb.binding.web.mapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.excilys.cdb.binding.web.dto.AddComputerDTO;
import com.excilys.cdb.binding.web.dto.ComputerDTO;
import com.excilys.cdb.binding.web.dto.EditComputerDTO;
import com.excilys.cdb.core.Company;
import com.excilys.cdb.core.Computer;

@Component
public class ComputerMapper {

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
	
	public ComputerDTO mapToComputerDTO(Computer computer) {
		if (computer != null) {
			return new ComputerDTO(computer.getId(), computer.getName(),
					computer.getIntroduced() != null ? computer.getIntroduced().toString() : "Unknown",
					computer.getDiscontinued() != null ? computer.getDiscontinued().toString() : "Unknown",
					computer.getCompany().getName() != null ? computer.getCompany().getName() : "Unknown");
		}
		return null;
	}

	public List<ComputerDTO> mapListComputerToListComputerDTO(List<Optional<Computer>> computers) {
		List<ComputerDTO> listComputer = computers.stream()
				.map(c -> new ComputerDTO(c.get().getId(), c.get().getName(),
						c.get().getIntroduced() != null ? c.get().getIntroduced().toString() : null,
						c.get().getDiscontinued() != null ? c.get().getDiscontinued().toString() : null,
						c.get().getCompany().getName() != null ? c.get().getCompany().getName() : null))
				.collect(Collectors.toList());
		return listComputer;
	}

	public Computer mapAddComputerDTOToComputer(AddComputerDTO computerDTO) {
		Company company = new Company.CompanyBuilder().build();
		if (computerDTO.getCompanyId() != null) {
			company = new Company.CompanyBuilder().setId(Long.valueOf(computerDTO.getCompanyId())).build();
		}

		return new Computer.ComputerBuilder().setName(computerDTO.getName())
				.setIntroduced(
						computerDTO.getIntroduced() == null ? null : LocalDate.parse(computerDTO.getIntroduced()))
				.setDiscontinued(
						computerDTO.getDiscontinued() == null ? null : LocalDate.parse(computerDTO.getDiscontinued()))
				.setCompany(company).build();
	}

	public Computer mapEditComputerDTOToComputer(EditComputerDTO computerDTO) {
		Company company = new Company.CompanyBuilder().build();
		if (computerDTO.getCompanyId() != null) {
			company = new Company.CompanyBuilder().setId(Long.valueOf(computerDTO.getCompanyId())).build();
		}

		return new Computer.ComputerBuilder().setId(Long.valueOf(computerDTO.getId()))
				.setName(computerDTO.getName())
				.setIntroduced(computerDTO.getIntroduced().isEmpty() ? null : LocalDate.parse(computerDTO.getIntroduced()))
				.setDiscontinued(computerDTO.getDiscontinued().isEmpty() ? null : LocalDate.parse(computerDTO.getDiscontinued()))
				.setCompany(company).build();
	}

	public EditComputerDTO mapComputerToEditComputerDTO(Optional<Computer> computer) {
		if (computer.isPresent()) {
			return new EditComputerDTO(String.valueOf(computer.get().getId()), computer.get().getName(),
					computer.get().getIntroduced() != null ? computer.get().getIntroduced().toString() : "",
					computer.get().getDiscontinued() != null ? computer.get().getDiscontinued().toString() : "",
					String.valueOf(computer.get().getCompany().getId()));
		}
		return null;
	}

}