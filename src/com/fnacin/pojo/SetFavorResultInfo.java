package com.fnacin.pojo;

import java.io.Serializable;

public class SetFavorResultInfo implements Serializable {

	
		
		/**
		 * @Fields serialVersionUID 
		 */
		private static final long serialVersionUID = 1903617481104123087L;
		
		private String result;
		public String getResult() {
			return result;
		}
		public void setResult(String result) {
			this.result = result;
		}
		public ErrorInfo getErrorInfo() {
			return errorInfo;
		}
		public void setErrorInfo(ErrorInfo errorInfo) {
			this.errorInfo = errorInfo;
		}
		private ErrorInfo errorInfo;
	}
