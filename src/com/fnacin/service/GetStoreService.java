package com.fnacin.service;

import java.io.InputStream;
import java.util.List;
import org.apache.http.NameValuePair;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.fnacin.adapter.StoreViewAdapter;
import com.fnacin.helper.GetStoreHelper;
import com.fnacin.helper.UrlHelper;
import com.fnacin.pojo.StoreInfo;

public class GetStoreService extends BaseService{
	private final static String TAG = "fnac";

	public static List<StoreInfo> getStoreInfo() {
		List<NameValuePair> nameValuePairs = setupHttpValuePairs();
		InputStream inputStream = doServiceRequest(UrlHelper.STORE, nameValuePairs);
		List<StoreInfo> storeList = null;
		try {
			storeList = GetStoreHelper.ReadXmlByPull(inputStream);
			Log.d(TAG, "GetStoreService start");
			Log.d(TAG, "storeList size=="+storeList.size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return storeList;
	}
	public StoreViewAdapter getStoreListAdapter(Activity activity,
			List<StoreInfo> storeList) {
		
		try {

			Log.d(TAG, "End Parse XML to storeList");
			return new StoreViewAdapter(activity, storeList);
		} catch (IllegalStateException e) {
			e.printStackTrace();
			Log.d(TAG, "Get inputstream failed:" + e.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
			Log.d(TAG, "Parse store failed:" + e.getMessage());
		}
		return null;
	}

}
