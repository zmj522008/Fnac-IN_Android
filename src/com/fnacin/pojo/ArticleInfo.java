package com.fnacin.pojo;

import java.io.Serializable;
import java.util.List;

public class ArticleInfo implements Serializable {
	public final static String SER_KEY = "com.fnac.pojo.articleinfo";

	/**
	 * 
	 */
	private static final long serialVersionUID = 8769298741882051310L;
	private String id;
	private String theme;
	private String themeId;
	private String nb_article;
	private String category;
	private String categoryId;
	private String title;
	private String type;
	private String typeId;
	private String date_begin_publish;
	private String date_posted;
	private String date_end_publish;
	private String url_media;
	private String accroche;
	private String content;

	private String nb_comment;
	private String name_comment;

	private String date_deposit;
	private String comment_content;

	public String getNb_article() {
		return nb_article;
	}

	public void setNb_article(String nb_article) {
		this.nb_article = nb_article;
	}

	public String getThemeId() {
		return themeId;
	}

	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}


	public String getTypeId() {
		return typeId;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}


	public String getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}

	private String url_image;

	private String fav_date;
	private String favor;
	private String nb_like;

	public List<CommentInfo> getCommentsList() {
		return commentsList;
	}

	public void setCommentsList(List<CommentInfo> commentsList) {
		this.commentsList = commentsList;
	}

	private String date_modify;
	private List<CommentInfo> commentsList;

	@Override
	public String toString() {
		return "ArticleInfo [id=" + id + ", theme=" + theme + ", category="
				+ category + ", title=" + title + ", type=" + type
				+ ", date_begin_publish=" + date_begin_publish
				+ ", date_posted=" + date_posted + ", date_end_publish="
				+ date_end_publish + ", url_media=" + url_media + ", grip="
				+ accroche + ", aticle_content=" + content + ", url_image="
				+ url_image + ", fav_date=" + fav_date + ", nb_like=" + nb_like
				+ ", date_modify=" + date_modify + ", nb_comment=" + nb_comment
				+ ", name_comment=" + name_comment + ", date_deposit="
				+ date_deposit + ", comment_content=" + comment_content + "]";
	}

	public String getFavor() {
		return favor;
	}

	public void setFavor(String favor) {
		this.favor = favor;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTheme() {
		return theme;
	}

	public void setTheme(String theme) {
		this.theme = theme;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate_begin_publish() {
		return date_begin_publish;
	}

	public void setDate_begin_publish(String date_begin_publish) {
		this.date_begin_publish = date_begin_publish;
	}

	public String getDate_posted() {
		return date_posted;
	}

	public void setDate_posted(String date_posted) {
		this.date_posted = date_posted;
	}

	public String getDate_end_publish() {
		return date_end_publish;
	}

	public void setDate_end_publish(String date_end_publish) {
		this.date_end_publish = date_end_publish;
	}

	public String getUrl_media() {
		return url_media;
	}

	public void setUrl_media(String url_media) {
		this.url_media = url_media;
	}

	public String getAccroche() {
		return accroche;
	}

	public void setAccroche(String accroche) {
		this.accroche = accroche;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl_image() {
		return url_image;
	}

	public void setUrl_image(String url_image) {
		this.url_image = url_image;
	}

	public String getFav_date() {
		return fav_date;
	}

	public void setFav_date(String fav_date) {
		this.fav_date = fav_date;
	}

	public String getNb_like() {
		return nb_like;
	}

	public void setNb_like(String nb_like) {
		this.nb_like = nb_like;
	}

	public String getDate_modify() {
		return date_modify;
	}

	public void setDate_modify(String date_modify) {
		this.date_modify = date_modify;
	}

	public String getNb_comment() {
		return nb_comment;
	}

	public void setNb_comment(String nb_comment) {
		this.nb_comment = nb_comment;
	}

	public String getName_comment() {
		return name_comment;
	}

	public void setName_comment(String name_comment) {
		this.name_comment = name_comment;
	}

	public String getDate_deposit() {
		return date_deposit;
	}

	public void setDate_deposit(String date_deposit) {
		this.date_deposit = date_deposit;
	}

	public String getComment_content() {
		return comment_content;
	}

	public void setComment_content(String comment_content) {
		this.comment_content = comment_content;
	}

	public void setNb_total(String nextText) {
		nb_article = nextText;

	}
	
	public  int getNbTotalArticle(){
		int i = -1;
		try{
			
			i = Integer.parseInt(nb_article);
		}catch(NumberFormatException  e){
			
		}
		
		return i;
	}

}
