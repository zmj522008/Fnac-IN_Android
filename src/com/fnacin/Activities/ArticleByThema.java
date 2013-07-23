package com.fnacin.Activities;

import com.fnacin.interfaces.GoogleAnalytics;
import com.fnacin.pojo.MutableArticleParameterInfo;
import com.fnacin.Activities.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ArticleByThema extends ArticleList {

	public static final String BUNDLE_CATEGORIE_ID = "categorie_id";
	private String categorie_id;
	public static final String BUNDLE_CATEGORIE_NAME= "categorie_name";
	private String categorie_name;
	private Button cate_button;	
	private PullToRefreshListView pullToRefeshListView;
	public ArticleByThema() {
		
		super(R.layout.tab_home, DisplayType.NORMAL_DISPLAY);
	
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		categorie_id = getIntent().getStringExtra(BUNDLE_CATEGORIE_ID);
		categorie_name=getIntent().getStringExtra(BUNDLE_CATEGORIE_NAME);
		
		Log.d(TAG, "ArticleByCatonCreate:catId=" + categorie_id);
		Log.d(TAG, "ArticleByCatonCreate:catName=" + categorie_name);
		GoogleAnalytics.trackPage("/INTRAFNAC - RUBRIQUE/"+categorie_name, this);
		cate_button = (Button) findViewById(R.id.bnt_category);
		cate_button.setVisibility(View.INVISIBLE);
		pullToRefeshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
		pullToRefeshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					// TODO Auto-generated method stub
					new GetDataTask().execute();
				}
			
			 });

	}
	 private class GetDataTask extends AsyncTask<Void, Void, String[]>{

			@Override
			protected String[] doInBackground(Void... params) {
				// TODO Auto-generated method stub
					return null;
			}

			@Override
			protected void onPostExecute(String[] result) {
				// TODO Auto-generated method stub
				populateListArticle();

				pullToRefeshListView.onRefreshComplete();
				super.onPostExecute(result);
			}

				 
				 
			 }
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		populateListArticle();
	}

	@Override
	protected void populateArticleParameterInfo(MutableArticleParameterInfo params) {
		categorie_id = getIntent().getStringExtra(BUNDLE_CATEGORIE_ID);
		//params.setThemeId(categorie_id);
		params.setCateId(categorie_id);
		Log.d(TAG, "Cat.populateArticleParameterInfo catId=" + categorie_id);
	}
@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	//super.onBackPressed();
	Activity act =(Activity) getCurrentFocus().getContext();
	act.getParent().onBackPressed();
	System.out.println("CurrentActivity----"+act.getParent());
}
}
