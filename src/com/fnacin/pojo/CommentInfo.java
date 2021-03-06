package com.fnacin.pojo;

import java.io.Serializable;

public class CommentInfo implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5712617296853058858L;
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	private String id;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	private String name;
	private String date;
	private String content;
	private String image;
	@Override
	public String toString() {
		return "commentInfo [name=" + name + ", date=" + date + ", content="
				+ content + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
