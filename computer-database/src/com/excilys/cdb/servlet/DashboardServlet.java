package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.Page.FilterAttribute;
import com.excilys.cdb.model.Page.SortingOrder;
import com.excilys.cdb.service.ComputerService;

/**
 * Servlet implementation class ListComputerServlet
 */
@Component
@WebServlet("/DashboardServlet")
public class DashboardServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String VUE_FORM = "/WEB-INF/views/dashboard.jsp";

	private static final int OFFSET = 5;

	private static final String ATT_COMPUTER_TOTAL = "computerTotal";
	private static final String ATT_COMPUTER_PAGE = "computerPage";
	private static final String ATT_PAGE_NUMBER = "pageNumber";
	private static final String ATT_PAGE_SIZE = "pageSize";
	private static final String ATT_PAGE_TOTAL = "pageTotal";
	private static final String ATT_LIST_PAGE_NUMBERS = "listPageNumbers";
	private static final String ATT_SEARCH = "search";
	
	private static final String ATT_SORT_FILTER = "filter";
	private static final String ATT_SORT_ORDER = "order";

	@Autowired
	private ComputerService computerService;
	
	@Autowired
	private ComputerMapper mapper;
	private int pageSize = 10;
	
	private static Logger logger = LoggerFactory.getLogger(DashboardServlet.class);
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this, config.getServletContext());
		super.init(config);
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
		} catch (NumberFormatException e) {
			logger.warn("Parameter " + request.getParameter("page") + " couldn't be parsed to an int.", e);
			pageNumber = 1;
		}
		
		List<Optional<Computer>> page = new ArrayList<Optional<Computer>>();
		String search = request.getParameter(ATT_SEARCH);
		if (search != null) {
			page = computerService.getComputerPaginatedByNameFilter(new Page<Computer>(pageSize, pageNumber), search).getContent();
		} else {
			page = computerService.getComputerPaginated(new Page<Computer>(pageSize, pageNumber)).getContent();
		}
		
		List<Integer> pageNumbers = listPageNumbers(pageNumber, computers.size() / pageSize + 1);

		session.setAttribute(ATT_PAGE_TOTAL, String.valueOf(totalPages / pageSize + 1));
		session.setAttribute(ATT_COMPUTER_TOTAL, totalPages);
		session.setAttribute(ATT_PAGE_NUMBER, pageNumber);
		session.setAttribute(ATT_COMPUTER_PAGE, mapper.mapListComputerToListComputerDTO(page));
		session.setAttribute(ATT_LIST_PAGE_NUMBERS, pageNumbers);

		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Page<Computer> computerPage = new Page<Computer>(pageSize, 1);
		setSortingOrder(request, computerPage);
		setFilterAttribute(request, computerPage);
		List<Optional<Computer>> page = computerService.getComputerPaginated(computerPage).getContent();
		request.setAttribute(ATT_COMPUTER_PAGE, mapper.mapListComputerToListComputerDTO(page));
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
	
	private void setSortingOrder(HttpServletRequest request, Page<Computer> page) {
		if (Page.DEFAULT_SORTING_ORDER.equals(request.getSession().getAttribute(ATT_SORT_ORDER))) {
			request.getSession().setAttribute(ATT_SORT_ORDER, SortingOrder.DESC);
			page.setOrder(SortingOrder.DESC);
		} else {
			request.getSession().setAttribute(ATT_SORT_ORDER, SortingOrder.ASC);
			page.setOrder(SortingOrder.ASC);
		}
	}
	
	private void setFilterAttribute(HttpServletRequest request, Page<Computer> page) {
		if(request.getParameter(ATT_SORT_FILTER) != null) {
			page.setFilter(FilterAttribute.valueOf((String) request.getParameter(ATT_SORT_FILTER)));
		} else {
			page.setFilter(Page.DEFAULT_PAGE_FILTER);
		}
	}

}
