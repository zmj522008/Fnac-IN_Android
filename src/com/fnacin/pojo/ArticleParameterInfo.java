package com.fnacin.pojo;

public class ArticleParameterInfo {
	protected String favor;
	protected String prefere;
	protected String podcast;
	protected String id;
	protected String comment;
	protected String typeId;
	protected String themeId;
	protected String cateId;
	protected Integer limtStart;
	protected Integer limtEnd;
	protected String storeId;
	protected String startDate;
	protected String endDate;
	
	public ArticleParameterInfo() {
	}
	
	public ArticleParameterInfo(ArticleParameterInfo params) {
		super();
		this.favor = params.favor;
		this.prefere = params.prefere;
		this.podcast = params.podcast;
		this.id = params.id;
		this.comment = params.comment;
		this.typeId = params.typeId;
		this.themeId = params.themeId;
		this.cateId = params.cateId;
		this.limtStart = params.limtStart;
		this.limtEnd = params.limtEnd;
		this.storeId = params.storeId;
		this.startDate = params.startDate;
		this.endDate = params.endDate;
	}

	public Integer getLimtStart() {
		return limtStart;
	}
	public Integer getLimtEnd() {
		return limtEnd;
	}

	public String getFavor() {
		return favor;
	}
	public String getPrefere() {
		return prefere;
	}
	public String getPodcast() {
		return podcast;
	}
	public String getId() {
		return id;
	}
	public String getComment() {
		return comment;
	}
	public String getTypeId() {
		return typeId;
	}
	public String getThemeId() {
		return themeId;
	}
	public String getCateId() {
		return cateId;
	}
	public String getStoreId() {
		return storeId;
	}
	public String getStartDate() {
		return startDate;
	}
	public String getEndDate() {
		return endDate;
	}
}
