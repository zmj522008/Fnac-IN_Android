package com.fnacin.helper;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.util.Xml;

import com.fnacin.pojo.ArticleInfo;
import com.fnacin.pojo.CommentInfo;

public class ArticleContentHelper {
	private static final String TAG = "fnac";
	
	// Optimize XML parse
	private static HashMap<String, Integer> tagMap = new HashMap<String, Integer>(){
		private static final long serialVersionUID = 4124751168637400732L;

	{
		put("articles",1);
		put("article",2);
		put("thematique",3);
		put("rubrique",4);
		put("titre",5);
		put("type",6);
		put("date_debut_publication",7);
		put("date_affichee",8);
		put("date_fin_publication",9);
		put("url_media",10);
		put("accroche",11);
		put("contenu",12);
		put("url_image",13);
		put("favoris",14);
		put("nb_jaime",15);
		put("date_modification",16);
		put("nb_commentaires",17);
		put("commentaires",18);
		put("commentaire",19);
		put("prenom",20);
		put("date_depot",21);
		put("nb_articles",22);
	}};

	public static List<ArticleInfo> ReadXmlByPull (InputStream inputStream) throws Exception {
		Log.d(TAG, "Begin ReadXmlByPull"+new Date());
		List <ArticleInfo> articleList = null;
		XmlPullParser xmlpull = Xml.newPullParser();
		xmlpull.setInput(inputStream, "utf-8");
		int eventCode = xmlpull.getEventType();
		ArticleInfo articleInfo = null;
		// 评论List
		List<CommentInfo> commentsList = null;
		// 评论
		String total = "-1";
		CommentInfo commentInfo = null;
		while(eventCode!=XmlPullParser.END_DOCUMENT){
			switch (eventCode){
				case XmlPullParser.START_DOCUMENT:	{
					break;
				}
				case XmlPullParser.START_TAG:{	
					Integer tagNum = tagMap.get(xmlpull.getName());
					if(tagNum==null){
						break;
					}
					switch(tagNum){
					case 1: 		// 遇到articles表示article数组开始
						articleList =new  ArrayList<ArticleInfo>();
						break;
					case 2: 		// 生成新的article
						articleInfo =  new ArticleInfo();
						articleInfo.setNb_total(total);
						articleInfo.setId(xmlpull.getAttributeValue("", "id"));
						break;
					case 3: 		// Theme
						articleInfo.setThemeId(xmlpull.getAttributeValue("", "id"));
						articleInfo.setTheme(xmlpull.nextText());
						break;
					case 4: 		// rubrique_libelle
						articleInfo.setCategoryId(xmlpull.getAttributeValue("", "id"));
						articleInfo.setCategory(xmlpull.nextText());
						break;
					case 5:			// titre
						articleInfo.setTitle(xmlpull.nextText());
						break;
					case 6:			// type
						articleInfo.setTypeId(xmlpull.getAttributeValue("", "id"));
						articleInfo.setType(xmlpull.nextText());
						break;
					case 7:			// date_debut_publication
						articleInfo.setDate_begin_publish(xmlpull.nextText());
						break;
					case 8:			// date_affichee
						articleInfo.setDate_deposit(xmlpull.nextText());
						break;
					case 9:			// date_fin_publication
						articleInfo.setDate_end_publish(xmlpull.nextText());
						break;
					case 10:		// url_media
						articleInfo.setUrl_media(xmlpull.nextText());
						break;
					case 11:		// accroche
						articleInfo.setAccroche(xmlpull.nextText());
						break;
					case 12:		// contenu both article and comment
						if(commentInfo!=null){
							commentInfo.setContent(xmlpull.nextText());							
						}else if(articleInfo!=null){
							articleInfo.setContent(xmlpull.nextText());							
						}
						break;
					case 13:		// url_image
						articleInfo.setUrl_image(xmlpull.nextText());
						break;
					case 14:		// favoris
						articleInfo.setFav_date(xmlpull.getAttributeValue("", "date"));
						articleInfo.setFavor(xmlpull.nextText());
						break;
					case 15:		// nb_jaime
						articleInfo.setNb_like(xmlpull.nextText());
						break;
					case 16:		// date_modification 
						articleInfo.setDate_modify(xmlpull.nextText());
						break;
					case 17:		// nb_commentaires
						articleInfo.setNb_comment(xmlpull.nextText());
						break;
					case 18:		// commentaires
						commentsList = new ArrayList<CommentInfo>();
						break;
					case 19:		// commentaire
						commentInfo = new CommentInfo();
						commentInfo.setId(xmlpull.getAttributeValue("","id"));
						break;
					case 20:		// prenom
						commentInfo.setName(xmlpull.nextText());
						break;
					case 21:		// date_depot
						commentInfo.setDate(xmlpull.nextText());
						break;
					case 22:		// nb total
						total = xmlpull.nextText();
						
						break;
					default:
						break;
					}
					break;
				}
				case XmlPullParser.END_TAG:	{ 
					Integer tagNum = tagMap.get(xmlpull.getName());
					if(tagNum==null){
						break;
					}
					switch(tagNum){
					case 2:			// article
						if(articleInfo!=null){
							articleList.add(articleInfo);
							articleInfo = null;
						}
						break;
					case 18:		// commentaires
						articleInfo.setCommentsList(commentsList);
						//Log.d(TAG, "Comment List Size:"+commentsList.size());
						commentsList = null;
					case 19:		// commentaire
						if(commentInfo!=null){
							commentsList.add(commentInfo);
							commentInfo = null;
						}
						break;
				
					default:
						break;
					}
					break;
				}
			}
			
			eventCode = xmlpull.next();//没有结束xml文件就推到下个进行解析
			
			
		}
		Log.d(TAG, "End ReadXmlByPull"+new Date());
		return articleList;
	}
}
