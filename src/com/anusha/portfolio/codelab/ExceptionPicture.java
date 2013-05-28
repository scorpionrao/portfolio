package com.anusha.portfolio.codelab;

public class ExceptionPicture  extends RuntimeException {
	
	private static String title = "PictureException:";
	
	public ExceptionPicture() {
		super();
	}
	
	public ExceptionPicture(String message) {
		super(title + message);
	}
	
	public ExceptionPicture(Throwable cause) {
		super(title + cause);
	}
	
	public ExceptionPicture(String message, Throwable cause) {
		super(title + message, cause);
	}
}