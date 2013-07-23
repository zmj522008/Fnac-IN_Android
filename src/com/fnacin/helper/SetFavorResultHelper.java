package com.fnacin.helper;

import java.io.IOException;
import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.fnacin.pojo.ErrorInfo;
import com.fnacin.pojo.SetFavorResultInfo;

public class SetFavorResultHelper {
	// Log tag
	private static final String TAG = "fnac";
	public static SetFavorResultInfo getSetFavorResultInfo(InputStream inputStream) throws XmlPullParserException, IOException{
		XmlPullParser xmlpull = Xml.newPullParser();
		xmlpull.setInput(inputStream, "utf-8");
		int eventCode = xmlpull.getEventType();
		SetFavorResultInfo setFavorResultInfo = null;
		ErrorInfo errorInfo = null;
		while(eventCode!=XmlPullParser.END_DOCUMENT){
			switch (eventCode){
				case XmlPullParser.START_DOCUMENT:	{
					break;
				}
				case XmlPullParser.START_TAG:{
					if("resultat".equals(xmlpull.getName())){
						setFavorResultInfo = new SetFavorResultInfo();
						setFavorResultInfo.setResult(xmlpull.nextText());
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
						if(setFavorResultInfo!=null){
							setFavorResultInfo.setErrorInfo(errorInfo);
						}
						break;
					}
					break;
				}
			}
			eventCode = xmlpull.next();
		}
		return setFavorResultInfo;
	}
}
