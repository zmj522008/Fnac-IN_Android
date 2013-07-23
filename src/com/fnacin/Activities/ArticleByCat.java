package com.fnacin.Activities;

import com.fnacin.interfaces.GoogleAnalytics;
import com.fnacin.pojo.MutableArticleParameterInfo;
import com.fnacin.Activities.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

public class ArticleByCat extends ArticleList {

	public static final String BUNDLE_CATEGORIE_ID = "categorie_id";
	private String categorie_id;
	public static final String BUNDLE_CATEGORIE_NAME= "categorie_name";
	private String categorie_name;
	private Button cate_button;	
	private PullToRefreshListView pullToRefeshListView;
	public ArticleByCat() {
		
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
		//cate_button.setOnClickListener(new CategoryButtonListener());
		pullToRefeshListView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
		pullToRefeshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {

				@Override
				public void onRefresh(PullToRefreshBase<ListView> refreshView) {
					// TODO Auto-generated method stub
					System.out.println("hello--catoo4444");
					new GetDataTask().execute();
				}
			
			 });

	}
	 private class GetDataTask extends AsyncTask<Void, Void, String[]>{

			@Override
			protected String[] doInBackground(Void... params) {
				// TODO Auto-generated method stub
				System.out.println("hellocat;;;;;;55555");
					return null;
			}

			@Override
			protected void onPostExecute(String[] result) {
				// TODO Auto-generated method stub
				System.out.println("hellocat;;;;;;6666");
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
		params.setCateId(categorie_id);
		Log.d(TAG, "Cat.populateArticleParameterInfo catId=" + categorie_id);
	}
	
	/*private class CategoryButtonListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent myIntent = new Intent();
			myIntent.setClass(ArticleByCat.this, Category.class);
			//myIntent.putExtra("categoryId", "1,2,3,4");
			startActivity(myIntent);
		}		
	}*/
}
