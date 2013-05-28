package com.anusha.portfolio.codelab;

public class DTOPicture {
	private String pictureName;
	private String pictureIncludeExclude;
	private String pictureDescription;
	private String subMenuName;
	
	public DTOPicture() {
		this.pictureName = "";
		this.pictureIncludeExclude = "";
		this.pictureDescription = "";
		this.subMenuName = "";
	}

	public DTOPicture(String pictureName, String subMenuName) {
		this.pictureName = pictureName;
		this.pictureIncludeExclude = "";
		this.pictureDescription = "";
		this.subMenuName = subMenuName;
	}

	public DTOPicture(String pictureName, String pictureIncludeExclude, String pictureDescription,
			String subMenuName) {
		this.pictureName = pictureName;
		this.pictureIncludeExclude = pictureIncludeExclude;
		this.pictureDescription = pictureDescription;
		this.subMenuName = subMenuName;
	}

	public String getPictureName() {
		return pictureName;
	}

	public void setPictureName(String pictureName) {
		this.pictureName = pictureName;
	}

	public String getPictureIncludeExclude() {
		return pictureIncludeExclude;
	}

	public void setPictureIncludeExclude(String pictureIncludeExclude) {
		this.pictureIncludeExclude = pictureIncludeExclude;
	}

	public String getPictureDescription() {
		return pictureDescription;
	}

	public void setPictureDescription(String pictureDescription) {
		this.pictureDescription = pictureDescription;
	}

	public String getSubMenuName() {
		return subMenuName;
	}

	public void setSubMenuName(String subMenuName) {
		this.subMenuName = subMenuName;
	}
	
	
}
