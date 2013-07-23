package com.fnacin.pojo;

import java.io.Serializable;

public class CategoryInfo implements Serializable{
	/**
	 * @Fields serialVersionUID 
	 */
	private static final long serialVersionUID = 5041785802511121149L;
	public  final static String SER_KEY = "com.fnac.pojo.categoryinfo";
	
	// ID
	private String categoryId;
	// Display content
	private String category;
	// Preference flag
	private String preferenceFlag;
	
	public String getPreferenceFlag() {
		return preferenceFlag;
	}
	public void setPreferenceFlag(String preferenceFlag) {
		this.preferenceFlag = preferenceFlag;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}
