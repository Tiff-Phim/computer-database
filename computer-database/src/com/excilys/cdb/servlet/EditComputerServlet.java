package com.excilys.cdb.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.dto.EditComputerDTO;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.servlet.validator.ComputerValidator;

/**
 * Servlet implementation class EditComputerServlet
 */
@WebServlet("/EditComputerServlet")
public class EditComputerServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;
    
	private static final String VUE_FORM = "/WEB-INF/views/editComputer.jsp";
	
	private static final String ATT_COMPUTER_ID = "computerId";
	private static final String ATT_COMPUTER_NAME = "computerName";
	private static final String ATT_INTRODUCED_DATE = "introduced";
	private static final String ATT_DISCONTINUED_DATE = "discontinued";
	private static final String ATT_COMPANY_ID = "companyId";
	private static final String ATT_COMPANY_LIST = "companyList";
	private static final String ATT_ERRORS = "errors";
	
	private static final String ATT_COMPUTER = "computer";
	
	private static Logger logger = LoggerFactory.getLogger(EditComputerServlet.class);

	private static CompanyMapper companyMapper = new CompanyMapper();
	private static ComputerMapper computerMapper = new ComputerMapper();
	private CompanyService companyService;
	private ComputerService computerService;
	
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditComputerServlet() {
        super();
        this.companyService = new CompanyService();
		this.computerService = new ComputerService();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		listCompanies(session);
		
		if (request.getParameter(ATT_COMPUTER_ID) != null) {
			long computerId = Long.valueOf(request.getParameter(ATT_COMPUTER_ID));
			
			Optional<Computer> computer = computerService.getComputerById(computerId);			
			session.setAttribute(ATT_COMPUTER, computerMapper.mapComputerToEditComputerDTO(computer));
		}
		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String computerId = request.getParameter(ATT_COMPUTER_ID);
		String computerName = request.getParameter(ATT_COMPUTER_NAME);
		String introduced = request.getParameter(ATT_INTRODUCED_DATE);
		String discontinued = request.getParameter(ATT_DISCONTINUED_DATE);
		String company = request.getParameter(ATT_COMPANY_ID);

		EditComputerDTO computer = new EditComputerDTO(computerId, computerName, introduced, discontinued, company);	
		ComputerValidator validator = new ComputerValidator();
		Map<String, String> errors = validator.validateComputer(computer);
		
		if (errors.isEmpty()) {
			computerService.updateComputer(computerMapper.mapEditComputerDTOToComputer(computer), Long.valueOf(computerId));
		} else {
			request.setAttribute(ATT_ERRORS, errors);
		}
		response.sendRedirect("DashboardServlet");
	}
	
	private void listCompanies(HttpSession session) {
		List<Optional<Company>> daoCompany = new ArrayList<Optional<Company>>();
		try {
			daoCompany = companyService.getAllCompany();
		} catch (SQLException e) {
			logger.error("Failed to get list of companies, due to:", e);
		}
		List<CompanyDTO> companies = new ArrayList<CompanyDTO>();
		for (Optional<Company> company : daoCompany) {
			companies.add(companyMapper.mapToCompanyDTO(company));
		}
		session.setAttribute(ATT_COMPANY_LIST, companies);
	}

}
