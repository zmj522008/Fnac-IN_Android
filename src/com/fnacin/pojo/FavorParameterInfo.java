package com.fnacin.pojo;

import java.io.Serializable;

public class FavorParameterInfo implements Serializable{
	public  final static String SER_KEY = "com.fnac.pojo.favorparameterinfo";
	private String article_id;
	private String type;
	public String getArticle_id() {
		return article_id;
	}
	public void setArticle_id(String article_id) {
		this.article_id = article_id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "FavorParameterInfo [article_id=" + article_id + ", type="
				+ type + "]";
	}
	 
	 

}
