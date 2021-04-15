package com.excilys.cdb.controller.web;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.excilys.cdb.mapper.ComputerMapper;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.model.Page;
import com.excilys.cdb.model.Page.FilterAttribute;
import com.excilys.cdb.model.Page.SortingOrder;
import com.excilys.cdb.service.ComputerService;

@Controller
@SessionAttributes({ "pageSize", "filter", "order" })
@RequestMapping("/dashboard")
public class DashboardController {

	private static final String VIEW_FORM = "dashboard";

	private static final String ATT_COMPUTER_TOTAL = "computerTotal";
	private static final String ATT_COMPUTER_PAGE = "computerPage";
	private static final String ATT_PAGE_NUMBER = "pageNumber";
	private static final String ATT_PAGE_TOTAL = "pageTotal";
	private static final String ATT_LIST_PAGE_NUMBERS = "listPageNumbers";

	private static final String ATT_PAGE_SIZE = "pageSize";
	private static final String ATT_SORT_FILTER = "filter";
	private static final String ATT_SORT_ORDER = "order";

	private final ComputerService computerService;
	private final ComputerMapper mapper;

	public DashboardController(ComputerService computerService, ComputerMapper mapper) {
		this.computerService = computerService;
		this.mapper = mapper;
	}

	@GetMapping
	public ModelAndView getDashboard(@RequestParam(required = false) Integer page,
			@RequestParam(required = false) String search, @SessionAttribute(required = false) Integer pageSize,
			@SessionAttribute(required = false) FilterAttribute filter,
			@SessionAttribute(required = false) SortingOrder order) {
		ModelAndView mv = new ModelAndView(VIEW_FORM);
		setSessionAttributes(mv, pageSize, filter, order);
		if (page == null) {
			page = 1;
		}
		Page<Computer> computerPage = new Page<Computer>((int) mv.getModel().get(ATT_PAGE_SIZE), page);
		addPageIndexes(mv, computerPage, getTotal(mv));
		addPage(mv, computerPage, search);
		return mv;
	}

	@PostMapping
	public ModelAndView postDashboard(@RequestParam(required = false) Integer page, @RequestParam(required = false) String selection,
			@RequestParam(required = false) Integer pageSize, @RequestParam(required = false) FilterAttribute filter,
			@SessionAttribute(required = false) SortingOrder order) {
		ModelAndView mv = new ModelAndView(VIEW_FORM);
		setSessionAttributes(mv, pageSize, filter, order);
		deleteSelection(selection);
		if (page == null) {
			page = 1;
		}
		Page<Computer> computerPage = new Page<Computer>((int) mv.getModel().get(ATT_PAGE_SIZE), page);
		setPageSize(computerPage, pageSize);
		computerPage.setFilter((FilterAttribute) mv.getModel().get(ATT_SORT_FILTER));
		computerPage.setOrder(order);
		addPageIndexes(mv, computerPage, getTotal(mv));
		addPage(mv, computerPage, null);
		return mv;
	}

	private void setPageSize(Page<Computer> computerPage, Integer pageSize) {
		if (pageSize != null) {
			computerPage.setSize(pageSize);
		}
	}

	private String setSessionAttributes(ModelAndView mv, Integer pageSize, FilterAttribute filter, SortingOrder order) {
		if (pageSize == null) {
			mv.addObject(ATT_PAGE_SIZE, Page.DEFAULT_PAGE_SIZE);
		} else {
			mv.addObject(ATT_PAGE_SIZE, pageSize);
		}
		if (order == null) {
			mv.addObject(ATT_SORT_ORDER, Page.DEFAULT_SORTING_ORDER);
		}
		if (filter == null) {
			mv.addObject(ATT_SORT_FILTER, Page.DEFAULT_PAGE_FILTER);
		} else {
			mv.addObject(ATT_SORT_FILTER, filter);
			flipOrder(mv, order);
		}
		return "dashboard";
	}

	private void flipOrder(ModelAndView mv, SortingOrder order) {
		if (order == SortingOrder.ASC) {
			mv.addObject(ATT_SORT_ORDER, SortingOrder.DESC);
		} else {
			mv.addObject(ATT_SORT_ORDER, SortingOrder.ASC);
		}
	}

	private int getTotal(ModelAndView mv) {
		mv.addObject(ATT_COMPUTER_TOTAL, this.computerService.getTotal());
		return this.computerService.getTotal().intValue();
	}

	private void addPageIndexes(ModelAndView mv, Page<Computer> computerPage, int total) {
		int page = computerPage.getNumber();
		List<Integer> pageNumbers = computerPage.listPageNumbers(page, total / computerPage.getSize() + 1);
		computerPage.setNumber(page);
		mv.addObject(ATT_LIST_PAGE_NUMBERS, pageNumbers);
		mv.addObject(ATT_PAGE_NUMBER, page);
		mv.addObject(ATT_PAGE_TOTAL, total / computerPage.getSize() + 1);
	}

	private void addPage(ModelAndView mv, Page<Computer> page, String search) {
		if (search != null) {
			mv.addObject(ATT_COMPUTER_PAGE, mapper.mapListComputerToListComputerDTO(
					this.computerService.getComputerPaginatedByNameFilter(page, search).getContent()));
		} else {
			mv.addObject(ATT_COMPUTER_PAGE, mapper.mapListComputerToListComputerDTO(
					this.computerService.getComputerPaginatedByNameFilter(page).getContent()));
		}
	}

	private void deleteSelection(String selection) {
		if (selection != null) {
			Arrays.stream(selection.split(",")).map(Long::valueOf).collect(Collectors.toList())
					.forEach(c -> computerService.deleteComputer(c));
		}
	}

}
