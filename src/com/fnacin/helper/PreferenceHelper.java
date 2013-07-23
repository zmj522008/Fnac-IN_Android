package com.fnacin.helper;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;
import android.util.Xml;

import com.fnacin.pojo.CategoryInfo;
import com.fnacin.pojo.ErrorInfo;
import com.fnacin.pojo.SetPreferenceResultInfo;
public class PreferenceHelper {
	// Log tag
	private static final String TAG = "fnac";
	public static List<CategoryInfo> ReadXmlByPull (InputStream inputStream)throws Exception {
		Log.d(TAG, "PreferenceHelper ReadXmlByPull Begin");
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
					if("thematiques".equals(xmlpull.getName())){
						Log.d(TAG, "CategoryHelper new ArrayList<CategoryInfo>");
						categoryList = new ArrayList<CategoryInfo>();
						break;
					}
					if("thematique".equals(xmlpull.getName())){
						categoryInfo = new CategoryInfo();
						categoryInfo.setCategoryId(xmlpull.getAttributeValue("", "id"));
						categoryInfo.setCategory(xmlpull.nextText());
						break;
					}
					if("prefere".equals(xmlpull.getName())){

						categoryInfo.setPreferenceFlag(xmlpull.nextText());
						if(categoryInfo!=null){
							categoryList.add(categoryInfo);
							categoryInfo = null;
						}
						break;
					}
					break;
				}
				case XmlPullParser.END_TAG:{
					if("prefere".equals(xmlpull.getName())){
						if(categoryInfo!=null){
							categoryList.add(categoryInfo);
							categoryInfo = null;
						}
						break;
					}
					break;
				}
			}
			eventCode = xmlpull.next();//没有结束xml文件就推到下个进行解析
		}
		Log.d(TAG, "PreferenceHelper ReadXmlByPull End");
		return categoryList;		
	}
	
	public static SetPreferenceResultInfo getSetPreferenceResult(InputStream inputStream) throws XmlPullParserException, IOException{
		XmlPullParser xmlpull = Xml.newPullParser();
		xmlpull.setInput(inputStream, "utf-8");
		int eventCode = xmlpull.getEventType();
		SetPreferenceResultInfo setPreferenceResultInfo = null;
		ErrorInfo errorInfo = null;
		while(eventCode!=XmlPullParser.END_DOCUMENT){
			switch (eventCode){
				case XmlPullParser.START_DOCUMENT:	{
					break;
				}
				case XmlPullParser.START_TAG:{
					if("resultat".equals(xmlpull.getName())){
						setPreferenceResultInfo = new SetPreferenceResultInfo();
						setPreferenceResultInfo.setResult(xmlpull.nextText());
						break;
					}
					if("erreur".equals(xmlpull.getName())){
						errorInfo = new ErrorInfo();
						break;
					}
					if("code".equals(xmlpull.getName())){
						String temp = xmlpull.nextText();
						if(temp!=null){
							try{
								errorInfo.setCode(Integer.parseInt(temp));
							}catch (Exception e){
								e.printStackTrace();
							}
						}						
						break;
					}
					if("message".equals(xmlpull.getName())){
						errorInfo.setMessage(xmlpull.nextText());
						break;
					}
					if("reauthentification".equals(xmlpull.getName())){
						String temp = xmlpull.nextText();
						if(temp!=null){
							try{
								errorInfo.setReauthentification(Integer.parseInt(temp));
							}catch (Exception e){
								e.printStackTrace();
							}
						}	
						break;
					}
					break;
				}
				case XmlPullParser.END_TAG:{
					if("erreur".equals(xmlpull.getName())){
						if(setPreferenceResultInfo!=null){
							setPreferenceResultInfo.setErrorInfo(errorInfo);
						}
						break;
					}
					break;
				}
			}
			eventCode = xmlpull.next();//没有结束xml文件就推到下个进行解析
		}
		return setPreferenceResultInfo;
	}
	
}
