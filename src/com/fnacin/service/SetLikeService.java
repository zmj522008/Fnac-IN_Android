package com.fnacin.service;

import java.io.InputStream;
import java.util.List;
import org.apache.http.NameValuePair;
import android.util.Log;
import com.fnacin.helper.SetLikeResultHelper;
import com.fnacin.helper.UrlHelper;
import com.fnacin.pojo.SetLikeResultInfo;

public class SetLikeService extends BaseService {
	// Log Tag
	private final String TAG = "fnac";
	public static final String SUCCESS = "success";
	public static final String ERROR = "error";
	
	public String setLike(String articleId) {

		List<NameValuePair> nameValuePairs = setupHttpValuePairs();
		addValuePair(nameValuePairs, "article_id", articleId);
		InputStream inputStream = doServiceRequest(UrlHelper.SETLIKE,
				nameValuePairs);
		try {

			Log.d(TAG, "SetLikeService get the result to SetLikeResultInfo");
			SetLikeResultInfo setLikeResultInfo = SetLikeResultHelper
					.getSetLikeResultInfo(inputStream);
			if (setLikeResultInfo != null) {
				// 0 is successSetFavorResultHelper
				if (setLikeResultInfo.getErrorInfo() != null) {
					return setLikeResultInfo.getErrorInfo().getMessage();
				}
				else { // return the error message
					Log.d(TAG, "SetLikeService setFavor failed");
					if (isNumeric(setLikeResultInfo.getNb_like()) == true
							&& setLikeResultInfo.getNb_like() != null) {
						Log.d(TAG, "SetLikeService setLike success");
						return SUCCESS;
					}
				}
			} else {
				Log.d(TAG, "SetLikeService setFavor cannot connecto server");
				return ERROR;
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "SetLikeService get connect failed:" + e.getMessage());
		}
		return "E";
	}

	public static boolean isNumeric(String str) {
		for (int i = str.length(); --i >= 0;) {
			if (!Character.isDigit(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}