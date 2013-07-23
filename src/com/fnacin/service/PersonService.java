package com.fnacin.service;

import java.io.InputStream;
import java.util.List;
import org.apache.http.NameValuePair;
import android.util.Log;
import com.fnacin.helper.GetPersonHelper;
import com.fnacin.helper.UrlHelper;

public class PersonService extends BaseService{
	// Log tag
	private static final String TAG = "fnac";
	public void getInfoList(String lastdate
			 ) {
		List<NameValuePair> nameValuePairs = setupHttpValuePairs();
		addValuePair(nameValuePairs, "retour","data");
		addValuePair(nameValuePairs, "date_derniere_maj",lastdate);
		InputStream inputStream = doServiceRequest(UrlHelper.ANNUAIRE, nameValuePairs, 90000);
		try {
			GetPersonHelper.ReadXmlByPull(inputStream);
			Log.d(TAG, "PersonHelper success");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
