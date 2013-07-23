package com.fnacin.service;

import java.io.InputStream;
import java.util.List;
import org.apache.http.NameValuePair;
import android.content.Context;
import android.util.Log;
import com.fnacin.adapter.CategoryViewAdapter;
import com.fnacin.helper.CategoryHelper;
import com.fnacin.helper.UrlHelper;
import com.fnacin.pojo.CategoryInfo;

public class CategoryService extends  BaseService{
	/**
	 * Get ListAdapter
	 */
	public CategoryViewAdapter getCategoryListAdapter(Context context,
			List<CategoryInfo> categoryInfoList) {
		
		try {

			Log.d(TAG, "End Parse XML to categoryInfoList");
			return new CategoryViewAdapter(context, categoryInfoList);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			Log.d(TAG, "Get inputstream failed:" + e.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "Parse article failed:" + e.getMessage());
		}
		return null;
	}

	public static List<CategoryInfo> getInfoList(
			String categoryId) {
		List<NameValuePair> nameValuePairs = setupHttpValuePairs();
		addValuePair(nameValuePairs, "id",categoryId);
		InputStream inputStream = doServiceRequest(UrlHelper.CATEGORY, nameValuePairs);
		List<CategoryInfo> categoryInfoList = null;
		try {
			
			categoryInfoList = CategoryHelper.ReadXmlByPull(inputStream);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return categoryInfoList;
	}
}
