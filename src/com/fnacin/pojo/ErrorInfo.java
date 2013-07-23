package com.fnacin.pojo;

import java.io.Serializable;

public class ErrorInfo  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2221768686225659129L;
	private Integer code;
	private String message;
	private Integer reauthentification; 
	public  final static String SER_KEY = "com.fnac.pojo.ErrorInfo";
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Integer getReauthentification() {
		return reauthentification;
	}
	public void setReauthentification(Integer reauthentification) {
		this.reauthentification = reauthentification;
	}
	
}
