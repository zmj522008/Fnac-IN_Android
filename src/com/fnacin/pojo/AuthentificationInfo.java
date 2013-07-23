package com.fnacin.pojo;

import java.io.Serializable;

public class AuthentificationInfo  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6176167522149298080L;
	private Integer result;
	private String ruler;
	private String sessionId;
	private String version;
	public  final static String SER_KEY = "com.fnac.pojo.AuthentificationInfo";
	private ErrorInfo errorInfo;
	private ParameterInfo parameterInfo;
	
	public ErrorInfo getErrorInfo() {
		return errorInfo;
	}
	public void setErrorInfo(ErrorInfo errorInfo) {
		this.errorInfo = errorInfo;
	}
	public ParameterInfo getParameterInfo() {
		return parameterInfo;
	}
	public void setParameterInfo(ParameterInfo parameterInfo) {
		this.parameterInfo = parameterInfo;
	}
	public Integer getResult() {
		return result;
	}
	public void setResult(Integer result) {
		this.result = result;
	}
	public String getRuler() {
		return ruler;
	}
	public void setRuler(String ruler) {
		this.ruler = ruler;
	}
	public String getSessionId() {
		return sessionId;
	}
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public boolean isValid() {
		return sessionId != null && sessionId.length() > 0;
	}
}
