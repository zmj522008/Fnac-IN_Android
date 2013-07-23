package com.fnacin.Activities;

import com.fnacin.interfaces.GoogleAnalytics;
import com.fnacin.pojo.MutableArticleParameterInfo;
import com.fnacin.Activities.R;
import com.google.android.apps.analytics.GoogleAnalyticsTracker;

import android.os.Bundle;

public class Podcast extends ArticleList {
//	//GoogleAnalytics
//	GoogleAnalyticsTracker tracker;
	public Podcast() {
		super(R.layout.tab_podcast, DisplayType.NORMAL_DISPLAY);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GoogleAnalytics.trackPage("/INTRAFNAC - PODCAST", this);
		populateListArticle();
	}
	
	protected void populateArticleParameterInfo(MutableArticleParameterInfo params) {
		params.setPodcast("1");
	}

	@Override
	public void onBackPressed() {
		new ExitDialog(this).show();
	}
}
