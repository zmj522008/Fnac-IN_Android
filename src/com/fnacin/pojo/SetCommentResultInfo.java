package com.fnacin.pojo;

import java.io.Serializable;

public class SetCommentResultInfo implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2051004190730033478L;
	private String nb_comment;
	public String getNb_comment() {
		return nb_comment;
	}
	public void setNb_comment(String nbComment) {
		nb_comment = nbComment;
	}
	public ErrorInfo getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(ErrorInfo errorInfo) {
		this.errorInfo = errorInfo;
	}
	private ErrorInfo errorInfo;
}

