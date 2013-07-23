package com.fnacin.interfaces;

import android.content.Context;
import android.util.Log;

import com.google.android.apps.analytics.GoogleAnalyticsTracker;

public class GoogleAnalytics {
	public static void trackPage(String name,Context context) {
		GoogleAnalyticsTracker tracker;
		Log.d("FNAC", "GA: " + name);
		tracker = GoogleAnalyticsTracker.getInstance();
		tracker.start("UA-22324743-5",context);
		tracker.trackPageView(name);
		tracker.trackTransactions();
		tracker.dispatch();
	}
}