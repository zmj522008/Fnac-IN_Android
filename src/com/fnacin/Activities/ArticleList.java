package com.fnacin.Activities;

import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fnacin.adapter.ArticleViewAdapter;
import com.fnacin.helper.AsyncImageLoader;
import com.fnacin.interfaces.FnacLoadmore;
import com.fnacin.pojo.ArticleInfo;
import com.fnacin.pojo.ArticleParameterInfo;
import com.fnacin.pojo.MutableArticleParameterInfo;
import com.fnacin.service.ArticleService;
import com.fnacin.service.AuthentificationService;
import com.fnacin.Activities.R;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class ArticleList extends ListActivity implements FnacLoadmore {

	public static enum DisplayType {
		NORMAL_DISPLAY, FAVOR_DISPLAY
	}
	private DisplayType currentType;
	protected final String TAG = "fnac";
	private List<ArticleInfo> articleList;
	private ArticleViewAdapter articleViewAdapter;
	private int mlayoutResId;
	private String favo;
	private ArticleParameterInfo articleParameterInfo;
	private View footerView;
	private TextView textView;
	private MutableArticleParameterInfo params ;
	private PullToRefreshListView pullToRefreshView;
	public ArticleList(int layoutResId, DisplayType displayType) {
		mlayoutResId = layoutResId;
		currentType = displayType;
		
	}
	public ArticleList(int layoutResId, DisplayType displayType,String fav) {
		mlayoutResId = layoutResId;
		currentType = displayType;
		favo=fav;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (!AuthentificationService.getInstance().hasValidInfo()) {
			Intent intent = new Intent();
			intent.setClass(this, Login.class);
			startActivity(intent);
			finish();
			return;
		}
		setContentView(mlayoutResId);
		params = new MutableArticleParameterInfo();
		params.setLimtEnd(FnacLoadmore.pageSize);
		params.setLimtStart(0);
		populateArticleParameterInfo(params);
		articleParameterInfo = new ArticleParameterInfo(params);
		articleViewAdapter = new ArticleViewAdapter(this, currentType, null);
		/*footerView = getLayoutInflater().inflate(R.layout.message_list_item_footer,null);
		footerView.setVisibility(View.VISIBLE);
		textView = (TextView) footerView.findViewById(R.id.main_text);
		textView.setText(R.string.loadMore);
		textView.setHeight(120);*/
		//footerView = this.getFooterView();
		//this.getListView().addFooterView(getFooterView());
		//this.getListView().addFooterView(footerView);
		//setFooterVisibility(View.VISIBLE);
		
		//PullToRefreshListView listView = (PullToRefreshListView)this.getListView();
		// listView.setAdapter(articleViewAdapter);
		// pullToRefreshView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
			setListAdapter(articleViewAdapter);
			//pullToRefreshView.setAdapter(articleViewAdapter);
		 /*pullToRefreshView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO Auto-generated method stub
				System.out.println("helloooooooooo4444");
				new GetDataTask().execute();
			}
		
		 });
*/
		//((PullToRefreshListView) getListView()).setOnRefreshListener(new OnRefreshListener(){
/*listView.setOnRefreshListener(new OnRefreshListener(){
			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				new GetDataTask().execute();
			}
			
		});
	}
	private class GetDataTask extends AsyncTask<Void, Void, String[]>{

		@Override
		protected String[] doInBackground(Void... arg0) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// TODO Auto-generated method stub
			//mListItems.addFirst("Added after refresh...");
	        // Call onRefreshComplete when the list has been refreshed.
	        ((PullToRefreshListView) getListView()).onRefreshComplete();
	        super.onPostExecute(result);
		}	
		*/
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

			pullToRefreshView.onRefreshComplete();
			super.onPostExecute(result);
		}

			 
			 
		 }
	// To be overridden to setup sub class specific article parameter infos
	protected void populateArticleParameterInfo(
			MutableArticleParameterInfo params) {
	}

	protected ArticleParameterInfo getCurrentArticleParameterInfo() {
		return articleParameterInfo;
	}

	@Override
	protected void onResume() {
		super.onResume();
		if(favo=="1"){
			populateListArticleFavor();
			ListView mList = this.getListView();
			int index = mList.getFirstVisiblePosition();
			View v = mList.getChildAt(0);
			int top = (v == null) ? 0 : v.getTop();
			// restore
			mList.setSelectionFromTop(index, top);
		}else{
		//populateListArticle();
			ListView mList = this.getListView();
			int index = mList.getFirstVisiblePosition();
			View v = mList.getChildAt(0);
			int top = (v == null) ? 0 : v.getTop();
			// restore
			mList.setSelectionFromTop(index, top);
		}
		AsyncImageLoader.getInstance().start();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		AsyncImageLoader.getInstance().stop();
	}
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		System.out.println("Bundle--------:"+outState);
	}
	protected void resetViewAdapter() {
		setListAdapter(articleViewAdapter);
	}

	public DisplayType getCurrentType() {
		return currentType;
	}

	public void setCurrentType(DisplayType currentType) {
		this.currentType = currentType;
	}

	public ArticleViewAdapter getArticleViewAdapter() {
		return articleViewAdapter;
	}
	public void setArticleViewAdapter(ArticleViewAdapter articleViewAdapter) {
		this.articleViewAdapter = articleViewAdapter;
	}
	public ArticleParameterInfo getArticleParameterInfo() {
		return articleParameterInfo;
	}
	public void setArticleParameterInfo(ArticleParameterInfo articleParameterInfo) {
		this.articleParameterInfo = articleParameterInfo;
	}
	public View getFooterView() {
		if (footerView == null) {

			//final LayoutInflater inflater = (LayoutInflater)context.getSystemService (Context.LAYOUT_INFLATER_SERVICE);
			footerView = getLayoutInflater().inflate(R.layout.message_list_item_footer,null, false);
			final TextView textView = (TextView) footerView.findViewById(R.id.main_text);
			textView.setText(R.string.loadMore);
			textView.setHeight(120);
//System.out.println("footervIEW----"+footerView+"---visibility--"+footerView.getVisibility()+"--articleSize---"+articleList.size());
			 if(articleList.size()<5){
			 footerView.setVisibility(View.INVISIBLE);
		
			 }
			// Log.d(TAG,"nb_article:"+articleList.size());
			footerView.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					textView.setTextColor(Color.BLACK);
					loadMore();
				}
			});
		}
		return footerView;
	}

	protected void populateListArticle() {
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
		final FnacProgressDialog dialog = new FnacProgressDialog(getParent());

		new Thread() {
			public void run() {
				
				final List<ArticleInfo> articles = ArticleService.getArticlesInfo(articleParameterInfo);
				
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						articleList = articles;
						if (articleList != null) {
							updateLoadMoreVisibility();
							if (articleViewAdapter != null) {
								articleViewAdapter.setArticleList(articleList);
								articleViewAdapter.notifyDataSetChanged();
								dialog.dismiss();
							}
						}else{
							dialog.dismiss();
							Toast.makeText(getApplicationContext(), "Erreur de communication avec le serveur", Toast.LENGTH_LONG).show();
						}
					}
				});
			}
		}.start();
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

	}
	protected void populateListArticleFavor() {
	
		//final FnacProgressDialog dialog = new FnacProgressDialog(this);
		final FnacProgressDialog dialog = new FnacProgressDialog(this.getParent());

		new Thread() {
			public void run() {

				final List<ArticleInfo> articles = ArticleService.getArticlesFavorInfo(articleParameterInfo);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						articleList = articles;
						if (articleList != null) {
							updateLoadMoreVisibility();
							if (articleViewAdapter != null) {
								articleViewAdapter.setArticleList(articleList);
								articleViewAdapter.notifyDataSetChanged();
								dialog.dismiss();
							}
						}else{
							dialog.dismiss();
							Toast.makeText(getApplicationContext(), "Erreur de communication avec le serveur", Toast.LENGTH_LONG).show();
						}
					}
				});
			}
		}.start();
	}

	public void loadMore() {
		//final FnacProgressDialog dialog = new FnacProgressDialog(this);
		final FnacProgressDialog dialog = new FnacProgressDialog(this.getParent());

		new Thread() {
			public void run() {
				if (articleList == null) {
					return;
				}
				final MutableArticleParameterInfo params = new MutableArticleParameterInfo(
						articleParameterInfo);
				// Filter
				// authentificationInfo.getParameterInfo().setPageTotalNum(40);
				int start = articleList.size();
				int end = articleList.size() + pageSize;
				params.setLimtEnd(end);
				params.setLimtStart(start);

				List<ArticleInfo> articleListTemp = ArticleService
						.getArticlesInfo(params);
				Log.i(TAG, "onListItemClick  loading................."
						+ articleList.size());
				System.out.println("onListItemClick  loading.................");
				if (articleListTemp != null && articleListTemp.size() > 0) {
					for (ArticleInfo info : articleListTemp) {
						articleList.add(info);
					}
					articleViewAdapter.setArticleList(articleList);
				}
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						updateLoadMoreVisibility();
						articleViewAdapter.notifyDataSetChanged();
						dialog.dismiss();
					}
				});
			}
		}.start();
	}

	private void setFooterVisibility(final int visibility) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				getFooterView().setVisibility(visibility);
			}
		});
	}

	private void updateLoadMoreVisibility() {
		if (articleList != null && articleList.size()!=0&&articleList.size() < articleList.get(0)
				.getNbTotalArticle()) {
			setFooterVisibility(View.VISIBLE);
		} else {
			setFooterVisibility(View.INVISIBLE);							
		}
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return false;
	}
}
