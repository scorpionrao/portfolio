package com.anusha.portfolio.codelab;

public class ExceptionSubMenu  extends RuntimeException {
	
	private static String title = "SubMenuException:";
	
	public ExceptionSubMenu() {
		super();
	}
	
	public ExceptionSubMenu(String message) {
		super(title + message);
	}
	
	public ExceptionSubMenu(Throwable cause) {
		super(title + cause);
	}
	
	public ExceptionSubMenu(String message, Throwable cause) {
		super(title + message, cause);
	}
}