package com.fnacin.service;

import java.io.InputStream;
import java.util.List;
import org.apache.http.NameValuePair;

import android.util.Log;

import com.fnacin.helper.SentEmailResultHelper;
import com.fnacin.helper.UrlHelper;
import com.fnacin.pojo.SendEmailResultInfo;

public class ResetPasswordService extends BaseService{
	// Log Tag
	private final static String TAG = "fnac";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";

	public String setPassword(String newpassword) {
		List<NameValuePair> nameValuePairs = setupHttpValuePairs();
		addValuePair(nameValuePairs, "password", newpassword);
		InputStream inputStream = doServiceRequest(UrlHelper.RESETPW,
				nameValuePairs);
		try {
			Log.d(TAG, "ResetPasswordService get the result to ResetPasswordService");
			SendEmailResultInfo sentEmailResultInfo = SentEmailResultHelper
					.getSentEmailResultInfo(inputStream);
			if (sentEmailResultInfo != null) {
				return sentEmailResultInfo.getErrorInfo().getMessage();
			} else {
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "ResetPasswordService get connect failed:" + e.getMessage());
		}
		return "E";
	}

}