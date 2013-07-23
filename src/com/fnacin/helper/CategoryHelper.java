package com.fnacin.helper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.util.Xml;

import com.fnacin.pojo.CategoryInfo;
public class CategoryHelper {
	// Log tag
	private static final String TAG = "fnac";
	public static List<CategoryInfo> ReadXmlByPull (InputStream inputStream)throws Exception {
		Log.d(TAG, "Begin CategoryHelper ReadXmlByPull");
		
		List<CategoryInfo> categoryList = null;
		XmlPullParser xmlpull = Xml.newPullParser();
		xmlpull.setInput(inputStream, "utf-8");
		int eventCode = xmlpull.getEventType();
		CategoryInfo categoryInfo = null;
		
		while(eventCode!=XmlPullParser.END_DOCUMENT){
			switch (eventCode){
				case XmlPullParser.START_DOCUMENT:	{
					break;
				}
				case XmlPullParser.START_TAG:{
					if("rubriques".equals(xmlpull.getName())){
System.out.println("Begain Rubriques:-------"+xmlpull.getName());
						Log.d(TAG, "CategoryHelper new ArrayList<CategoryInfo>");
						categoryList = new ArrayList<CategoryInfo>();
						break;
					}
					if("rubrique".equals(xmlpull.getName())){
System.out.println("Begain rubrique:-------"+xmlpull.getName());
						categoryInfo = new CategoryInfo();
						categoryInfo.setCategoryId(xmlpull.getAttributeValue("", "id"));
						categoryInfo.setCategory(xmlpull.nextText());
						Log.d(TAG,"CategoryHelper new CategoryInfo");
						if(categoryInfo!=null){
							categoryList.add(categoryInfo);
							categoryInfo = null;
						}
						break;
					}
					break;
				}
				case XmlPullParser.END_TAG:{
System.out.println("TagNum----------"+xmlpull.getName());
					if("rubrique".equals(xmlpull.getName())){
System.out.println("End rubrique:-------");
						if(categoryInfo!=null){
							categoryList.add(categoryInfo);
							categoryInfo = null;
						}
						break;
					}
					break;
				}
			}
			eventCode = xmlpull.next();
		}
		Log.d(TAG, "End CategoryHelper ReadXmlByPull");
		return categoryList;		
	}
}
