package com.fnacin.service;

import java.io.InputStream;
import java.util.List;
import org.apache.http.NameValuePair;
import android.content.Context;
import android.util.Log;
import com.fnacin.adapter.CommentViewList;
import com.fnacin.helper.GetCommentHelper;
import com.fnacin.helper.UrlHelper;
import com.fnacin.pojo.CommentInfo;

public class GetCommentService extends BaseService{
	// Log Tag
	private final String TAG = "fnac";
	

	/**
	 * Get ListAdapter
	 */
	public CommentViewList getCommentListAdapter(Context context,
			List<CommentInfo> commentList) {
		
		try {

			Log.d(TAG, "End Parse XML to categoryInfoList");
			return new CommentViewList(context, commentList);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			Log.d(TAG, "Get inputstream failed:" + e.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "Parse article failed:" + e.getMessage());
		}
		return null;
	}
	
	
	public static List<CommentInfo> getInfoList(
			String articleId) {
		List<NameValuePair> nameValuePairs = setupHttpValuePairs();
		addValuePair(nameValuePairs, "id",articleId);
		addValuePair(nameValuePairs, "commentaire", "1");

		InputStream inputStream = doServiceRequest(UrlHelper.ARTICLE, nameValuePairs);
		
		List<CommentInfo> commentList = null;
		try {
			
			commentList = GetCommentHelper.ReadXmlByPull(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commentList;
	}
	
	
	
	
	
	
	
	
	
	
}