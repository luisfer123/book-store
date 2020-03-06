package com.book.store.exceptions;

public class PageNumberNotValidException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	public PageNumberNotValidException() {
		super("The number page required is not valid!");
	}

}
