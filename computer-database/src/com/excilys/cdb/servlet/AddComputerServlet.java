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

import com.excilys.cdb.dto.AddComputerDTO;
import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.mapper.CompanyMapper;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;
import com.excilys.cdb.servlet.validator.AddComputerValidator;

/**
 * Servlet implementation class AddComputerServlet
 */
@WebServlet("/AddComputerServlet")
public class AddComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private static final String VUE_FORM = "/WEB-INF/views/addComputer.jsp";

	private static final String ATT_COMPUTER_NAME = "computerName";
	private static final String ATT_INTRODUCED_DATE = "introduced";
	private static final String ATT_DISCONTINUED_DATE = "discontinued";
	private static final String ATT_COMPANY_ID = "companyId";
	private static final String ATT_COMPANY_LIST = "companyList";
	private static final String ATT_ERRORS = "errors";

	private CompanyService companyService;
	private ComputerService computerService;
	
	private static CompanyMapper companyMapper = new CompanyMapper();
	private static ComputerMapper computerMapper = new ComputerMapper();

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddComputerServlet() {
		super();
		this.companyService = new CompanyService();
		this.computerService = new ComputerService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<Optional<Company>> daoCompany = new ArrayList<Optional<Company>>();
		try {
			daoCompany = companyService.getAllCompany();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		List<CompanyDTO> companies = new ArrayList<CompanyDTO>();

		for (Optional<Company> company : daoCompany) {
			companies.add(companyMapper.mapToCompanyDTO(company));
		}

		session.setAttribute(ATT_COMPANY_LIST, companies);
		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String computerName = request.getParameter(ATT_COMPUTER_NAME);
		String introduced = request.getParameter(ATT_INTRODUCED_DATE);
		String discontinued = request.getParameter(ATT_DISCONTINUED_DATE);
		String company = request.getParameter(ATT_COMPANY_ID);

		AddComputerDTO computer = new AddComputerDTO(computerName, introduced, discontinued, company);	
		AddComputerValidator validator = new AddComputerValidator();
		Map<String, String> errors = validator.validateComputer(computer);
		if (errors.isEmpty()) {
			computerService.addComputer(computerMapper.mapAddComputerDTOToComputer(computer));
		} else {
			request.setAttribute(ATT_ERRORS, errors);
		}

		response.sendRedirect("/computer-database/DashboardServlet");
	}

}
