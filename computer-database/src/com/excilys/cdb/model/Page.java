package com.excilys.cdb.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Page<E> class defines a page object.
 * 
 * @author Tiffany PHIMMASANE
 * @version 0.1
 *
 * @param <E> element
 */
public class Page<E> {

	private static final int DEFAULT_PAGE_SIZE = 10;
	private static final int DEFAULT_PAGE_NUMBER = 1;
	private static final SortingOrder DEFAULT_SORTING_ORDER = SortingOrder.ASC;
	
	private int size;
	private int number;
	private SortingOrder order;
	private List<Optional<E>> content;
	private Page<E> nextPage;
	private Page<E> previousPage;
	
	enum SortingOrder {
		ASC,
		DESC
	}
	
	public Page(int size, int number) {
		content = new ArrayList<>();
		this.size = size;
		this.number = number;
	}
	
//	public Page(int size, int number, SortingOrder order, List<E> content, Page<E> nextPage, Page<E> previousPage) {
//		this.size = size;
//		this.number = number;
//		this.order = order;
//		this.content = content;
//		this.nextPage = nextPage;
//		this.previousPage = previousPage;
//	}
	
//	public Page(List<E> content, Page<E> nextPage, Page<E> previousPage) {
//		super();
//		this.size = DEFAULT_PAGE_SIZE;
//		this.number = DEFAULT_PAGE_NUMBER;
//		this.order = DEFAULT_SORTING_ORDER;
//		this.content = content;
//		this.nextPage = nextPage;
//		this.previousPage = previousPage;
//	}

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
	
	public List<Optional<E>> getContent() {
		return content;
	}
	
	public void setContent(List<Optional<E>> content) {
		this.content = content;
	}
	
	public Page<E> getNextPage() {
		return nextPage;
	}
	
	public void setNextPage(Page<E> nextPage) {
		this.nextPage = nextPage;
	}
	
	public Page<E> getPreviousPage() {
		return previousPage;
	}
	
	public void setPreviousPage(Page<E> previousPage) {
		this.previousPage = previousPage;
	}
	
}