package com.excilys.cdb.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.excilys.cdb.service.ComputerService;

/**
 * Servlet implementation class DeleteComputerServlet
 */
@WebServlet("/DeleteComputerServlet")
public class DeleteComputerServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	private static final String VUE_FORM = "/WEB-INF/views/dashboard.jsp";

	private static final String ATT_COMPUTER_SELECTION = "selection";

	private ComputerService computerService;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteComputerServlet() {
		super();
		this.computerService = new ComputerService();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.getServletContext().getRequestDispatcher(VUE_FORM).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Arrays.stream(request.getParameter(ATT_COMPUTER_SELECTION).split(",")).map(Long::valueOf)
				.collect(Collectors.toList())
				.forEach(c -> computerService.deleteComputer(c));
		response.sendRedirect("DashboardServlet");
	}

}
