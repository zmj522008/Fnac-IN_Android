package com.fnacin.pojo;

import java.io.Serializable;

/**
 * @Title: ArticleParameterInfo.java
 * @Package com.fnac.pojo
 * @Description: TODO(ÃÌº”√Ë ˆ)
 * @author Hubert hejunjie2002@sina.com
 * @date 2011-4-20 œ¬ŒÁ06:32:44
 * @version V1.0
 */
public class MutableArticleParameterInfo extends ArticleParameterInfo {
	public MutableArticleParameterInfo() {
		super();
	
	}
	public MutableArticleParameterInfo(ArticleParameterInfo params) {
		super(params);
	}
	
	public void setLimtStart(Integer limtStart) {
		this.limtStart = limtStart;
	}
	public void setLimtEnd(Integer limtEnd) {
		this.limtEnd = limtEnd;
	}
	
	public void setFavor(String favor) {
		this.favor = favor;
	}
	public void setPrefere(String prefere) {
		this.prefere = prefere;
	}
	public void setPodcast(String podcast) {
		this.podcast = podcast;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}
	public void setThemeId(String themeId) {
		this.themeId = themeId;
	}
	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
}
