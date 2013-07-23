package com.fnacin.service;

import java.io.InputStream;
import java.util.List;
import org.apache.http.NameValuePair;
import android.util.Log;
import com.fnacin.helper.SentEmailResultHelper;
import com.fnacin.helper.UrlHelper;
import com.fnacin.pojo.SendEmailResultInfo;

public class RefindPasswordService extends BaseService {
	// Log Tag
	private final String TAG = "fnac";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";

	public String setEmail(String email) {
		List<NameValuePair> nameValuePairs = setupHttpValuePairs();
		addValuePair(nameValuePairs, "email", email);
		InputStream inputStream = doServiceRequest(UrlHelper.PASSWORD,
				nameValuePairs);
		try {
			Log.d(TAG, "CommentService get the result to SetCommentResultInfo");
			SendEmailResultInfo sentEmailResultInfo = SentEmailResultHelper
					.getSentEmailResultInfo(inputStream);
			if (sentEmailResultInfo != null) {
				return sentEmailResultInfo.getErrorInfo().getMessage();
			} else {
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "CommentService get connect failed:" + e.getMessage());
		}
		return null;
	}

}
