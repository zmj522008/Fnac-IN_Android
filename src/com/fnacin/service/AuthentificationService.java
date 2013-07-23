package com.fnacin.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import com.fnacin.helper.AuthentificationHelper;
import com.fnacin.helper.UrlHelper;
import com.fnacin.pojo.AuthentificationInfo;

public class AuthentificationService extends BaseService {

	private AuthentificationInfo mAuthentificationInfo = null;
	private static AuthentificationService _instance;

	public static AuthentificationService getInstance() {
		if (_instance == null) {
			_instance = new AuthentificationService();
		}
		return _instance;
	}
	
	public boolean Login(String mail, String pass, String serviceVersion) {
		final List<NameValuePair> nameValueParis = new ArrayList<NameValuePair>();
		addValuePair(nameValueParis, "version", serviceVersion);
		addValuePair(nameValueParis, "email", mail);
		addValuePair(nameValueParis, "password", pass);
		AuthentificationInfo authentificationInfo = null;
		/*** Get Parameter From Page **/

		/******************************/
		try {
			
			InputStream inputStream = doServiceRequest(UrlHelper.AUTHENTIFICATION, nameValueParis);
			AuthentificationHelper authentificationHelper = new AuthentificationHelper();
			authentificationInfo = authentificationHelper
					.raadAuthentificationInfo(inputStream);
		} catch (MalformedURLException e) {
			e.printStackTrace();
			authentificationInfo = null;
		} catch (IOException e) {
			e.printStackTrace();
			authentificationInfo = null;
		} catch (Exception e) {
			e.printStackTrace();
			authentificationInfo = null;
		}
		mAuthentificationInfo = authentificationInfo;
		if (authentificationInfo != null
				&& authentificationInfo.getResult() == 1) {
			return true;
		} else {
			return false;
		}
	}

	public AuthentificationInfo getAuthentificationInfo() {
		return mAuthentificationInfo;
	}

	public boolean hasValidInfo() {
		return mAuthentificationInfo != null && mAuthentificationInfo.isValid();
	}
}
