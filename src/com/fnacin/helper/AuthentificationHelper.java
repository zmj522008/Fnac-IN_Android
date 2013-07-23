package com.fnacin.helper;

import java.io.InputStream;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

import com.fnacin.pojo.AuthentificationInfo;
import com.fnacin.pojo.ErrorInfo;
import com.fnacin.pojo.ParameterInfo;

public class AuthentificationHelper {

	public AuthentificationInfo raadAuthentificationInfo(InputStream inputStream) throws Exception{
		AuthentificationInfo authentificationInfo = null;
		ErrorInfo errorInfo = null;
		ParameterInfo parameterInfo = null;
		XmlPullParser xmlpull = Xml.newPullParser();
		xmlpull.setInput(inputStream, "utf-8");
		int eventCode = xmlpull.getEventType();
		while(eventCode!=XmlPullParser.END_DOCUMENT){
			switch (eventCode){
				case XmlPullParser.START_DOCUMENT:	{
					break;
				}
				case XmlPullParser.START_TAG:{
					if("authentification".equals(xmlpull.getName())){
						authentificationInfo = new AuthentificationInfo();
						break;
					}
					if("resultat".equals(xmlpull.getName())){
						String temp = xmlpull.nextText();
						if(temp!=null){
							try{
								authentificationInfo.setResult(Integer.parseInt(temp));
							}catch (Exception e){
								e.printStackTrace();
							}
						}
						break;
					}
					if("dirigeant".equals(xmlpull.getName())){
						authentificationInfo.setRuler(xmlpull.nextText());
						break;
					}
					if("session_id".equals(xmlpull.getName())){
						authentificationInfo.setSessionId(xmlpull.nextText());
						break;
					}
					if("version".equals(xmlpull.getName())){
						authentificationInfo.setVersion(xmlpull.nextText());
						break;
					}
					if("parametres".equals(xmlpull.getName())){
						parameterInfo = new ParameterInfo();
						break;
					}
					if("nb_articles_page".equals(xmlpull.getName())){
						String temp = xmlpull.nextText();
						if(temp!=null){
							try{
								parameterInfo.setPageTotalNum(Integer.parseInt(temp));
							}catch (Exception e){
								e.printStackTrace();
							}
						}
						break;
					}
					if("pre_page".equals(xmlpull.getName())){
						String temp = xmlpull.getAttributeValue("", "ferme");
						if(temp!=null){
							try{
								parameterInfo.setIsMandatory(Integer.parseInt(temp));
							}catch (Exception e){
								parameterInfo.setIsMandatory(0);
							}
						}
						parameterInfo.setPrePage(xmlpull.nextText());
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
						authentificationInfo.setErrorInfo(errorInfo);
						errorInfo = null;
						break;
					}
					if("parametres".equals(xmlpull.getName())){
						authentificationInfo.setParameterInfo(parameterInfo);
						parameterInfo = null;
						break;
					}
					break;
				}
			}
			eventCode = xmlpull.next();
		}
		return authentificationInfo;
	}
}
