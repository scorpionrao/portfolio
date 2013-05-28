package com.anusha.portfolio.codelab;

public class ExceptionTopMenu  extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static String title = "TopMenuException:";
	
	public ExceptionTopMenu() {
		super();
	}
	
	public ExceptionTopMenu(String message) {
		super(title + message);
	}
	
	public ExceptionTopMenu(Throwable cause) {
		super(title + cause);
	}
	
	public ExceptionTopMenu(String message, Throwable cause) {
		super(title + message, cause);
	}
}