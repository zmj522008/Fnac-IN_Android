package com.fnacin.pojo;

import java.io.Serializable;

public class SetLikeResultInfo implements Serializable {
	
	private String nb_like;

	public String getNb_like() {
		return nb_like;
	}

	public void setNb_like(String nb_like) {
		this.nb_like = nb_like;
	}
	public ErrorInfo getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(ErrorInfo errorInfo) {
		this.errorInfo = errorInfo;
	}
	private ErrorInfo errorInfo;
	

}
