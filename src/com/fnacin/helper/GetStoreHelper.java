package com.fnacin.helper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import com.fnacin.pojo.StoreInfo;
import android.util.Log;
import android.util.Xml;

public class GetStoreHelper {

	// Log tag
	private static final String TAG = "fnac";
	public static List<StoreInfo> ReadXmlByPull (InputStream inputStream)throws Exception {
		Log.d(TAG, "Begin  GetStoreHelper ReadXmlByPull");
		
		List<StoreInfo> storeList = null;
		XmlPullParser xmlpull = Xml.newPullParser();
		xmlpull.setInput(inputStream, "utf-8");
		int eventCode = xmlpull.getEventType();
		StoreInfo storeInfo = null;
		
		while(eventCode!=XmlPullParser.END_DOCUMENT){
			switch (eventCode){
				case XmlPullParser.START_DOCUMENT:	{
					break;
				}
				case XmlPullParser.START_TAG:{
					if("magasins".equals(xmlpull.getName())){
						Log.d(TAG, "GetStoreHelper new ArrayList<StoreInfo>");
						storeList = new ArrayList<StoreInfo>();
						break;
					}
					if("magasin".equals(xmlpull.getName())){
						storeInfo = new StoreInfo();
						storeInfo.setId(xmlpull.getAttributeValue("", "id"));
						break;
					}if("nom".equals(xmlpull.getName())){
						storeInfo.setNom(xmlpull.nextText());
						break;
					}if("adresse".equals(xmlpull.getName())){
						storeInfo.setAdresse(xmlpull.nextText());
						break;
					}if("code_postal".equals(xmlpull.getName())){
						storeInfo.setCode_postal(xmlpull.nextText());
						break;
					}if("pays".equals(xmlpull.getName())){
						storeInfo.setPays(xmlpull.nextText());
						break;
					}if("region".equals(xmlpull.getName())){
						storeInfo.setRegion(xmlpull.nextText());
						break;
					}if("ville".equals(xmlpull.getName())){
						storeInfo.setVille(xmlpull.nextText());
						break;
					}
					if("latitude".equals(xmlpull.getName())){
						storeInfo.setLatitude(xmlpull.nextText());
						break;
					}if("longitude".equals(xmlpull.getName())){
						storeInfo.setLongitude(xmlpull.nextText());
						break;
					}if("fax".equals(xmlpull.getName())){
						storeInfo.setFax(xmlpull.nextText());
						break;
					}if("telephone".equals(xmlpull.getName())){
						storeInfo.setTelephone(xmlpull.nextText());
						break;
					}if("email".equals(xmlpull.getName())){
						storeInfo.setEmai(xmlpull.nextText());
						break;
					}
					if("billeterie".equals(xmlpull.getName())){
						storeInfo.setBilleterie(xmlpull.nextText());
						break;
					}if("ouverture".equals(xmlpull.getName())){
						storeInfo.setOuverture(xmlpull.nextText());
						break;
					}if("ouverture_exceptionnelle".equals(xmlpull.getName())){
						storeInfo.setOuverture_exceptionnelle(xmlpull.nextText());
						break;
					}if("url_fnaccom".equals(xmlpull.getName())){
						storeInfo.setUrl_fnaccom(xmlpull.nextText());
						break;
					}
					break;
				}
				case XmlPullParser.END_TAG:{
					if("magasin".equals(xmlpull.getName())){
						if(storeInfo!=null){
							storeList.add(storeInfo);
							storeInfo = null;
						}
						break;
					}
					break;
				}
			}
			eventCode = xmlpull.next();
		}
		Log.d(TAG, "End GetStoreHelper ReadXmlByPull");
		Log.d(TAG, "storeList size" +storeList.size() );
		return storeList;		
	}
}