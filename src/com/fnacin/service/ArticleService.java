package com.fnacin.service;

import java.io.InputStream;
import java.util.List;

import org.apache.http.NameValuePair;

import android.app.AlertDialog;
import android.util.Log;

import com.fnacin.helper.ArticleContentHelper;
import com.fnacin.helper.UrlHelper;
import com.fnacin.pojo.ArticleInfo;
import com.fnacin.pojo.ArticleParameterInfo;

public class ArticleService extends BaseService {
	/**
	 * 
	 * @Title: getHttpResponse
	 * @Description: Generate HttpResponse
	 * @param @param authentificationInfo
	 * @param @param articleParameterInfo
	 * @param @return HttpResponse if success
	 * @return HttpResponse
	 * @throws
	 */
	public static List<ArticleInfo> getArticlesInfo(
			ArticleParameterInfo articleParameterInfo) {
		List<NameValuePair> nameValuePairs = setupHttpValuePairs();
		populateHttpValuePairs(articleParameterInfo, nameValuePairs);

		InputStream inputStream = doServiceRequest(UrlHelper.ARTICLE,
				nameValuePairs);

		List<ArticleInfo> articleList = null;
		try {
			articleList = ArticleContentHelper.ReadXmlByPull(inputStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
System.out.println("ArticleList exception:"+e);
		}
		return articleList;
	}

	public static List<ArticleInfo> getArticlesFavorInfo(
			ArticleParameterInfo articleParameterInfo) {
		List<NameValuePair> nameValuePairs = setupHttpValuePairsFavor();
		populateHttpValuePairs(articleParameterInfo, nameValuePairs);

		InputStream inputStream = doServiceRequest(UrlHelper.ARTICLE,
				nameValuePairs);

		List<ArticleInfo> articleList = null;
		try {
			articleList = ArticleContentHelper.ReadXmlByPull(inputStream);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return articleList;
	}

	public AlertDialog getAlertDialog() {

		return null;
	}

	protected static void populateHttpValuePairs(
			ArticleParameterInfo articleParameterInfo,
			List<NameValuePair> nameValuePairs) {
		// If the the filter exists
		if (articleParameterInfo != null) {
			Log.d(TAG, "Filter exists");
			addValuePair(nameValuePairs, "rubrique_id",
					articleParameterInfo.getCateId());
			addValuePair(nameValuePairs, "commentaire",
					articleParameterInfo.getComment());
			addValuePair(nameValuePairs, "podcast",
					articleParameterInfo.getPodcast());
			addValuePair(nameValuePairs, "prefere",
					articleParameterInfo.getPrefere());
			addValuePair(nameValuePairs, "limit_start", articleParameterInfo
					.getLimtStart().toString());
			addValuePair(nameValuePairs, "limit_end", articleParameterInfo
					.getLimtEnd().toString());
			addValuePair(nameValuePairs, "thematique_id",
					articleParameterInfo.getThemeId());
			addValuePair(nameValuePairs, "favoris",
					articleParameterInfo.getFavor());
		}
	}

}
