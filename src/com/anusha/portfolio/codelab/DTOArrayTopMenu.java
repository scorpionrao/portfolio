package com.anusha.portfolio.codelab;

import java.io.Serializable;

public class DTOArrayTopMenu implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private DTOTopMenu[] topMenus;

	public DTOArrayTopMenu() {}
	
	public DTOTopMenu[] getTopMenus() {
		return topMenus;
	}

	public void setTopMenus(DTOTopMenu[] topMenus) {
		this.topMenus = topMenus;
	}
	
	

}
