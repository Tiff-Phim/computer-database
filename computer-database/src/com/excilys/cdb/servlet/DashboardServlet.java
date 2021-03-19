package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.service.CompanyService;
import com.excilys.cdb.service.ComputerService;

/**
 * Servlet implementation class ListComputerServlet
 */
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String VUE_FORM = "/WEB-INF/views/dashboard.jsp";
	
	private static final int OFFSET = 5;

	private static final String ATT_COMPUTER_TOTAL = "computerTotal";
	private static final String ATT_COMPUTER_LIST = "listComputer";
	private static final String ATT_COMPUTER_NAME = "computerName";
	private static final String ATT_COMPUTER_ID = "computerId";
	private static final String ATT_COMPUTER_INTRODUCED = "introducedDate";
	private static final String ATT_COMPUTER_DISCONTINUED = "discontinuedDate";
	private static final String ATT_COMPANY_NAME = "companyName";

	private static final String ATT_COMPUTER_PAGE = "computerPage";
	private static final String ATT_PAGE_NUMBER = "pageNumber";
	private static final String ATT_PAGE_TOTAL = "pageTotal";
	private static final String ATT_PAGE_SIZE = "pageSize";
	
	private static final String ATT_LIST_PAGE_NUMBERS = "listPageNumbers";

	private ComputerService computerService;
	private CompanyService companyService;

	private static ComputerMapper mapper = new ComputerMapper();
	private int pageSize = 10;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DashboardServlet() {
		super();
		this.computerService = new ComputerService();
		this.companyService = new CompanyService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		List<ComputerDTO> computers = getComputerList();
		
		int pageNumber;
		int totalPages = computers.size();
		if (request.getParameter(ATT_PAGE_SIZE) != null) {
			pageSize = Integer.parseInt(request.getParameter(ATT_PAGE_SIZE));
		}
		
		try {
			pageNumber = Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException numberFormatExceptoin) {
			pageNumber = 1;
		}
		
		List<Integer> pageNumbers = listPageNumbers(pageNumber, computers.size() / pageSize + 1);

		session.setAttribute(ATT_PAGE_TOTAL, String.valueOf(totalPages / pageSize + 1));
		session.setAttribute(ATT_COMPUTER_LIST, computers);
		session.setAttribute(ATT_COMPUTER_TOTAL, totalPages);
		request.setAttribute(ATT_PAGE_NUMBER, pageNumber);
		request.setAttribute(ATT_COMPUTER_PAGE,
				computerService.getComputerPaginated(new Page<Computer>(pageSize, pageNumber)).getContent());
		request.setAttribute(ATT_LIST_PAGE_NUMBERS, pageNumbers);

		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private List<ComputerDTO> getComputerList() {
		List<Optional<Computer>> daoComputers = new ArrayList<Optional<Computer>>();
		daoComputers = computerService.getInfoComputer();

		List<ComputerDTO> computers = new ArrayList<ComputerDTO>();
		for (Optional<Computer> computer : daoComputers) {
			computers.add(mapper.mapToComputerDTO(computer));
		}
		return computers;
	}

	private List<Integer> listPageNumbers(int pageNumber, int totalPages) {
		int start = pageNumber - (OFFSET / 2);
		start = Math.max(start, 1);
		int end = start + OFFSET - 1;
		if (end > totalPages) {
			end = totalPages;
			start = end - OFFSET + 1;
		}
		return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
	}

}
