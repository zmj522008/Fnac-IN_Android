package com.fnacin.Activities;

import com.fnacin.interfaces.GoogleAnalytics;
import com.fnacin.Activities.R;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class Favorite extends ArticleList {
	private final String TAG = "fnac";
//	//GoogleAnalytics
//	GoogleAnalyticsTracker tracker;
	public Favorite() {
		super(R.layout.tab_preference, DisplayType.NORMAL_DISPLAY,"1");
	}

	// Log Tag
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.d(TAG, "Favorite:onCreate");
		super.onCreate(savedInstanceState);
		GoogleAnalytics.trackPage("/INTRAFNAC - FAVORIS", this);
		Button editButton = (Button) findViewById(R.id.btnEdit);
		editButton.setOnClickListener(new EditButtonOnClickListener());
		loadArticlePage();
	}
	
	private void loadArticlePage() {
		setContentView(R.layout.tab_preference);
		// Filter
		super.getArticleViewAdapter().setmCurrentType(DisplayType.NORMAL_DISPLAY);
		//resetViewAdapter();
		populateListArticleFavor();
		//Set EditButton
		Button editButton = (Button) findViewById(R.id.btnEdit);
		// Set OnClickListener
		editButton.setOnClickListener(new EditButtonOnClickListener());
		Log.d(TAG, "Show preference articles");
	}
	private void loadArticlePageEdit() {
		
		setContentView(R.layout.tab_preference_edit);
		super.getArticleViewAdapter().setmCurrentType(DisplayType.FAVOR_DISPLAY);
		//resetViewAdapter();
		populateListArticleFavor();
		//Set EditButton
		Button editButton = (Button) findViewById(R.id.btn_valide);
		// Set OnClickListener
		editButton.setOnClickListener(new ValideButtonOnClickListener());
	}
	class EditButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			//loadCategoryPage();
			//Set EditButton
			//Button editButton = (Button) findViewById(R.id.btnEdit);
			// Set OnClickListener
			//editButton.setOnClickListener(new EditButtonOnClickListener());
			loadArticlePageEdit();

		}
	}
	class ValideButtonOnClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			loadArticlePage();
		}
		
	}
	@Override
	public void onBackPressed() {
	new ExitDialog(this.getParent()).show();
	} 
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadArticlePage();
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}
}