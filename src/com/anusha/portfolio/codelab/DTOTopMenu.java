package com.anusha.portfolio.codelab;

public class DTOTopMenu {
	private String topMenuName;
	private String topMenuIncludeExclude;
	private String topMenuDescription;
	
	public DTOTopMenu() {
		this.topMenuName = "";
		this.topMenuIncludeExclude = "";
		this.topMenuDescription = "";
	}

	public DTOTopMenu(String topMenuName) {
		this.topMenuName = topMenuName;
		this.topMenuIncludeExclude = "";
		this.topMenuDescription = "";
	}

	public DTOTopMenu(String topMenuName, String topMenuIncludeExclude,
			String topMenuDescription) {
		this.topMenuName = topMenuName;
		this.topMenuIncludeExclude = topMenuIncludeExclude;
		this.topMenuDescription = topMenuDescription;
	}
	
	public String getTopMenuName() {
		return topMenuName;
	}
	public void setTopMenuName(String topMenuName) {
		this.topMenuName = topMenuName;
	}
	public String getTopMenuIncludeExclude() {
		return topMenuIncludeExclude;
	}
	public void setTopMenuIncludeExclude(String topMenuIncludeExclude) {
		this.topMenuIncludeExclude = topMenuIncludeExclude;
	}
	public String getTopMenuDescription() {
		return topMenuDescription;
	}
	public void setTopMenuDescription(String topMenuDescription) {
		this.topMenuDescription = topMenuDescription;
	}
	
}