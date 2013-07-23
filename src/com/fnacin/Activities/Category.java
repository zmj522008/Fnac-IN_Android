package com.fnacin.Activities;

import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;

import com.fnacin.adapter.CategoryViewAdapter;
import com.fnacin.interfaces.FnacLoadmore;
import com.fnacin.interfaces.GoogleAnalytics;
import com.fnacin.pojo.CategoryInfo;
import com.fnacin.service.PreferenceService;
import com.fnacin.Activities.R;

public class Category extends ListActivity implements FnacLoadmore {
	// Log Tag
	private final String TAG = "fnac";
	private List<CategoryInfo> categoryInfoList;

	// //GoogleAnalytics
	// GoogleAnalyticsTracker tracker;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GoogleAnalytics.trackPage("/INTRAFNAC - THEMATIQUE", this);
		setContentView(R.layout.tab_category);
		Log.d(TAG, "CategoryActivity:onCreate");
		loadCategoryPage();
	}

	// Load the category page
	private void loadCategoryPage() {
		this.setContentView(R.layout.tab_category);
		// Call home service function
		new Thread() {
			public void run() {
				final List<CategoryInfo> categories = PreferenceService
						.getCategoryInfoList("rubriques");
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						categoryInfoList = categories;
						setListAdapter(new CategoryViewAdapter(Category.this,
								categoryInfoList));
					}
				});
			}
		}.start();
	}

	@Override
	public void onBackPressed() {
		// new ExitDialog(this).show();
		finish();
	}

	@Override
	public void loadMore() {

	}
}
