package com.fnacin.service;

import java.io.InputStream;
import java.util.List;
import org.apache.http.NameValuePair;
import android.util.Log;
import com.fnacin.helper.SetFavorResultHelper;
import com.fnacin.helper.UrlHelper;
import com.fnacin.pojo.SetFavorResultInfo;

public class DeleteFavorService extends BaseService {
	// Log Tag
	private final String TAG = "fnac";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";

	public String setFavor(String articleId) {
		List<NameValuePair> nameValuePairs = setupHttpValuePairs();
		addValuePair(nameValuePairs, "article_id", articleId);
		addValuePair(nameValuePairs, "type", "0");
		InputStream inputStream = doServiceRequest(UrlHelper.SETFAVOR,
				nameValuePairs);
		try {
			Log.d(TAG, "SetFavorService get the result to SetFavorResultInfo");
			SetFavorResultInfo setFavorResultInfo = SetFavorResultHelper
					.getSetFavorResultInfo(inputStream);
			if (setFavorResultInfo != null) {
				// 0 is successSetFavorResultHelper
				if ("0".equals(setFavorResultInfo.getResult())) {
					Log.d(TAG, "SetFavorService setFavor success");
					return SUCCESS;
				} else { // return the error message
					Log.d(TAG, "SetFavorService setFavor failed");
					if (setFavorResultInfo.getErrorInfo() != null) {
						return setFavorResultInfo.getErrorInfo().getMessage();
					}
				}
			} else {
				Log.d(TAG, "SetFavorService setFavor cannot connecto server");
				return ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "SetFavorService get connect failed:" + e.getMessage());
		}
		return null;
	}

}