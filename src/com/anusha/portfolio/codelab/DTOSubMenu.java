package com.anusha.portfolio.codelab;

public class DTOSubMenu {
	private String subMenuName;
	private String subMenuIncludeExclude;
	private String subMenuDescription;
	private String topMenuName;
	
	public DTOSubMenu() {
		this.subMenuName = "";
		this.subMenuIncludeExclude = "";
		this.subMenuDescription = "";
		this.topMenuName = "";
	}

	public DTOSubMenu(String subMenuName, String topMenuName) {
		this.subMenuName = subMenuName;
		this.subMenuIncludeExclude = "";
		this.subMenuDescription = "";
		this.topMenuName = topMenuName;
	}

	public DTOSubMenu(String subMenuName, String subMenuIncludeExclude, String subMenuDescription,
			String topMenuName) {
		this.subMenuName = subMenuName;
		this.subMenuIncludeExclude = subMenuIncludeExclude;
		this.subMenuDescription = subMenuDescription;
		this.topMenuName = topMenuName;
	}
	
	public String getSubMenuName() {
		return subMenuName;
	}
	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
	}
	public String getSubMenuIncludeExclude() {
		return subMenuIncludeExclude;
	}
	public void setSubMenuIncludeExclude(String subMenuIncludeExclude) {
		this.subMenuIncludeExclude = subMenuIncludeExclude;
	}
	public String getSubMenuDescription() {
		return subMenuDescription;
	}
	public void setSubMenuDescription(String subMenuDescription) {
		this.subMenuDescription = subMenuDescription;
	}
	public String getTopMenuName() {
		return topMenuName;
	}
	public void setTopMenuName(String topMenuName) {
		this.topMenuName = topMenuName;
	}

}
