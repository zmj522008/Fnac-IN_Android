package com.fnacin.Activities;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.fnacin.Activities.R;
import com.fnacin.adapter.ThematiqueViewAdapter;
import com.fnacin.interfaces.FnacLoadmore;
import com.fnacin.interfaces.GoogleAnalytics;
import com.fnacin.pojo.CategoryInfo;
import com.fnacin.service.PreferenceService;

public class Thematique extends ListActivity implements FnacLoadmore {
	// Log Tag
	private final String TAG = "fnac";
	private List<CategoryInfo> categoryInfoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GoogleAnalytics.trackPage("/INTRAFNAC - THEMATIQUE", this);
		setContentView(R.layout.tab_thematique);
		Log.d(TAG, "CategoryActivity:onCreate");
		loadThematiquePage();
	}

	// Load the category page
	private void loadThematiquePage() {
		this.setContentView(R.layout.tab_thematique);
		// Call home service function
		new Thread() {
			public void run() {
				final List<CategoryInfo> categories = PreferenceService
						.getCategoryInfoList("thematiques");
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(categories!=null){
						categoryInfoList = categories;
						setListAdapter(new ThematiqueViewAdapter(
								Thematique.this, categoryInfoList));
						}else{
							Toast.makeText(getApplicationContext(), "Erreur de communication avec le serveur", Toast.LENGTH_LONG).show();

						}
					}
				});
			}
		}.start();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		Activity act = (Activity) getCurrentFocus().getContext();
		act.getParent().onBackPressed();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void loadMore() {
		// TODO Auto-generated method stub

	}
}
