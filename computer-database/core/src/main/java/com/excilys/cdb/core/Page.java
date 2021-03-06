package com.excilys.cdb.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Page<E> class defines a page object.
 * 
 * @author Tiffany PHIMMASANE
 * @version 0.2
 *
 * @param <E> element
 */
public class Page<E> {

	private static final int OFFSET = 5;
	public static final int DEFAULT_PAGE_NUMBER = 1;
	public static final int DEFAULT_PAGE_SIZE = 10;
	public static final FilterAttribute DEFAULT_PAGE_FILTER = FilterAttribute.COMPUTER_ID;
	public static final SortingOrder DEFAULT_SORTING_ORDER = SortingOrder.ASC;

	private int size;
	private int number;
	private SortingOrder order;
	private FilterAttribute filter;
	private List<Optional<E>> content;
	private Page<E> nextPage;
	private Page<E> previousPage;

	public enum SortingOrder {
		ASC, DESC
	}

	public enum FilterAttribute {
		COMPANY_NAME("company"), COMPUTER_NAME("name"), COMPUTER_ID("id"), COMPUTER_INTRODUCED(
				"introduced"), COMPUTER_DISCONTINUED("discontinued");

		private String attribute;

		FilterAttribute(String attribute) {
			this.attribute = attribute;
		}

		public void setAttribute(String attribute) {
			this.attribute = attribute;
		}

		public String getAttribute() {
			return this.attribute;
		}
	}

	public Page(int size, int number) {
		content = new ArrayList<>();
		if (size < 0) {
			this.size = DEFAULT_PAGE_SIZE;
		} else {
			this.size = size;
		}
		this.order = DEFAULT_SORTING_ORDER;
		this.filter = DEFAULT_PAGE_FILTER;
		this.number = number;
	}

	public Page(int size, int number, SortingOrder order, FilterAttribute filter) {
		this.size = size;
		this.number = number;
		this.order = order;
		this.filter = filter;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public SortingOrder getOrder() {
		return order;
	}

	public void setOrder(SortingOrder order) {
		this.order = order;
	}

	public FilterAttribute getFilter() {
		return filter;
	}

	public void setFilter(FilterAttribute filter) {
		this.filter = filter;
	}

	public List<Optional<E>> getContent() {
		return content;
	}

	public void setContent(List<Optional<E>> elementList) {
		this.content = elementList;
	}

	public Page<E> getNextPage() {
		if (this.nextPage == null) {
			this.nextPage = new Page<>(this.size, this.number + 1);
		}
		return nextPage;
	}

	public void setNextPage(Page<E> nextPage) {
		this.nextPage = nextPage;
	}

	public Page<E> getPreviousPage() {
		Page<E> previousPage = this;
		if (this.previousPage == null) {
			if (number > 1) {
				previousPage = new Page<>(this.size, this.number - 1);
				this.previousPage = previousPage;
			}
		} else {
			previousPage = this.previousPage;
		}
		return previousPage;
	}

	public void setPreviousPage(Page<E> previousPage) {
		this.previousPage = previousPage;
	}
	
	public List<Integer> listPageNumbers(int pageNumber, int totalPages) {
		int start = pageNumber - (OFFSET / 2);
		start = Math.max(start, 1);
		int end = start + OFFSET - 1;
		if (end > totalPages) {
			end = totalPages;
			start = end - OFFSET + 1;
		}
		return IntStream.rangeClosed(start, end).boxed().collect(Collectors.toList());
	}
	
	public static class PageBuilder {
		
		private int size = Page.DEFAULT_PAGE_SIZE;
		private int number = Page.DEFAULT_PAGE_NUMBER;
		private SortingOrder order = Page.DEFAULT_SORTING_ORDER;
		private FilterAttribute filter = Page.DEFAULT_PAGE_FILTER;
		
		public PageBuilder setSize(int size) {
			this.size = size;
			return this;
		}
		
		public PageBuilder setNumber(int number) {
			this.number = number;
			return this;
		}
		
		public PageBuilder setOrder(SortingOrder order) {
			this.order = order;
			return this;
		}
		
		public PageBuilder setFilter(FilterAttribute filter) {
			this.filter = filter;
			return this;
		}
		
		public Page build() {
			return new Page(size, number, order, filter);
		}
	}

	@Override
	public String toString() {
		return "Page [size=" + size + ", number=" + number + ", order=" + order + ", filter=" + filter + ", content="
				+ content + ", nextPage=" + nextPage + ", previousPage=" + previousPage + "]";
	}
	
}