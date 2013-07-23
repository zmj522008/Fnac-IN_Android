package com.fnacin.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fnacin.Activities.ArticleGroup;
import com.fnacin.Activities.ArticleImageView;
import com.fnacin.Activities.ArticleList;
import com.fnacin.Activities.ArticleList.DisplayType;
import com.fnacin.Activities.DisplayArticle;
import com.fnacin.Activities.FavoGroup;
import com.fnacin.Activities.R;
import com.fnacin.pojo.ArticleInfo;
import com.fnacin.pojo.FavorParameterInfo;
import com.fnacin.service.DeleteFavorService;
public class ArticleViewAdapter extends BaseAdapter {
	// Log Tag
	private static String TAG = "fnac";
	private List<ArticleInfo> articleList;
	private Activity mActivity;
	private ArticleList.DisplayType mCurrentType;
	private ArticleList artLi;
	class ViewHolder{
		TextView theme;
		TextView cate;
		TextView title;
		TextView date_publication;
		TextView accroche;
		TextView nbLike;
		TextView nbComment;
		Button article_like_btn;
		Button article_comment_btn;
		ArticleImageView image;
		Button viewDetail;
		TextView deletefavorite;
		Button watch;
	}

	public ArticleViewAdapter(Activity activity,
			ArticleList.DisplayType displayType, List<ArticleInfo> list) {
		super();
		mActivity = activity;
		mCurrentType = displayType;
		articleList = new ArrayList<ArticleInfo>();
		if (list != null)
			articleList = list;
	}

	public List<ArticleInfo> getArticleList() {
		return articleList;
	}

	public void setArticleList(List<ArticleInfo> list) {
		articleList = list;
	}

	@Override
	public int getCount() {

		return articleList.size();
	}

	public ArticleList.DisplayType getmCurrentType() {
		return mCurrentType;
	}

	public void setmCurrentType(ArticleList.DisplayType mCurrentType) {
		this.mCurrentType = mCurrentType;
	}

	@Override
	public View getView(final int position,  View convertView, ViewGroup parent) {

		//		if (articleList.size() == 0 || position == getCount() - 1) {
		//			// return this.viewMap.get(position-1);
		//			return getFooterView(0, convertView, parent);
		//		}
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(mActivity);
			if (mCurrentType == DisplayType.FAVOR_DISPLAY) {
				convertView = inflater.inflate(R.layout.item_article_delete, null);
			} else {
				convertView = inflater.inflate(R.layout.item_article, null);
			}
			holder.theme = (TextView) convertView.findViewById(R.id.article_theme);
			holder.cate = (TextView) convertView.findViewById(R.id.article_cate);
			holder.title= (TextView) convertView.findViewById(R.id.article_title);
			holder.date_publication = (TextView) convertView.findViewById(R.id.article_date_publication);
			
			holder.accroche = (TextView) convertView.findViewById(R.id.article_content);
			holder.nbLike = (TextView) convertView.findViewById(R.id.nb_like);
			holder.nbComment = (TextView) convertView.findViewById(R.id.nb_comment);
			holder.article_like_btn = (Button) convertView.findViewById(R.id.article_like_btn);
			holder.article_comment_btn = (Button) convertView.findViewById(R.id.article_comment_btn);
			holder.image = (ArticleImageView) convertView.findViewById(R.id.article_image);
			holder.watch = (Button) convertView.findViewById(R.id.article_watch);
			holder.viewDetail = (Button) convertView.findViewById(R.id.btnDetail);
			holder.deletefavorite = (TextView) convertView.findViewById(R.id.fav_delete_favor);
			convertView.setTag(holder);
		} else {

			holder =  (ViewHolder) convertView.getTag();
		}

		final ArticleInfo articleInfo = articleList.get(position);
		holder.theme.setText(Html.fromHtml(articleInfo.getTheme()));
		// Category
		holder.cate.setText(articleInfo.getCategory());
		holder.title.setText(Html.fromHtml(articleInfo.getTitle()));
		holder.date_publication.setText(articleInfo.getDate_deposit());
		
		holder.accroche.setText(Html.fromHtml(articleInfo.getAccroche()));
		holder.accroche.setOnClickListener(new DetailButtonOnClickListener(mActivity, articleInfo));
		holder.nbLike.setText("(" + articleInfo.getNb_like() + ")");
		holder.nbComment.setText("(" + articleInfo.getNb_comment() + ")");
		holder.article_like_btn.setOnClickListener(new DetailButtonOnClickListener(mActivity, articleInfo));
		holder.article_comment_btn.setOnClickListener(new DetailButtonOnClickListener(mActivity, articleInfo));
		Integer TypeId = Integer.parseInt(articleInfo.getTypeId());
		holder.image.setOnClickListener(null);
		holder.image.setArticleInfo(articleInfo);
		switch (TypeId) {
		case 1:
			holder.watch.setVisibility(View.INVISIBLE);
			break;
		case 3:
		case 2:
			holder.watch.setText(TypeId == 3 ? R.string.LabelListenButton : R.string.LabelWatchButton);
			holder.watch.setVisibility(View.VISIBLE);
			WatchButtonOnClickListener listener = new WatchButtonOnClickListener(mActivity, articleInfo);
			holder.watch.setOnClickListener(listener);
			holder.image.setOnClickListener(listener);
			break;
		default:
			break;
		}
		holder.viewDetail.setOnClickListener(new DetailButtonOnClickListener(mActivity,
				articleInfo));

		if (mCurrentType == DisplayType.FAVOR_DISPLAY) {
			holder.deletefavorite
			.setOnClickListener(new DeleteFavorButtonOnClickListener(
					articleInfo));
		}
		convertView.setOnClickListener(new DetailButtonOnClickListener(mActivity,
				articleInfo));
		return convertView;
	}

	class DetailButtonOnClickListener implements OnClickListener {
		private ArticleInfo articleInfo;

		public DetailButtonOnClickListener(Context context,
				ArticleInfo articleInfo) {
			this.articleInfo = articleInfo;
		}

		@Override
		public void onClick(View v) {
			Log.v("item", "clicked " + v.toString());
			// Get current Activity
			//List<NameValuePair> nameValuePairs = setupHttpValuePairsFavor();

//System.out.println("ArticleList------:"+mActivity.getLocalClassName().toString());
			Bundle mBundle = new Bundle();
			mBundle.putSerializable(ArticleInfo.SER_KEY, articleInfo);
			Intent intent = new Intent();
			intent.putExtras(mBundle);
			intent.setClass(v.getContext(), DisplayArticle.class);
			if(mActivity.getLocalClassName().toString().equals("Favorite")){
					FavoGroup parent = (FavoGroup) ((Activity)v.getContext()).getParent();
					parent.startChildActivity("DisplayArticle", intent);			
			}else{
					ArticleGroup parent = (ArticleGroup) ((Activity)v.getContext()).getParent();
					parent.startChildActivity("DisplayArticle", intent);			
			 }
		}
	}

	class WatchButtonOnClickListener implements OnClickListener {
		protected final ArticleInfo articleInfo;
		protected final Context context;
		public WatchButtonOnClickListener(Context context,
				ArticleInfo articleInfo) {
			this.articleInfo = articleInfo;
			this.context = context;
		}

		@Override
		public void onClick(View v) {
			Log.d(TAG, "Read media from server");
			String url = articleInfo.getUrl_media();
			int a = url.lastIndexOf(".");
			String extension = url.substring(a, url.length());
			Log.d(TAG, a + " | " + extension);
			if (extension.toLowerCase().equals(".mp4")
					|| extension.toLowerCase().equals(".3gp")
					|| extension.toLowerCase().equals(".wmv")) {
				Log.d(TAG, "Watch:" + url);
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setType("video/*");
				intent.setDataAndType(uri, "video/*");
				context.startActivity(intent);
			} else if (extension.toLowerCase().equals(".mp3")) {
				Log.d(TAG, "Listen:" + url);
				Uri uri = Uri.parse(url);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setType("audio/mp3");
				intent.setDataAndType(uri, "audio/mp3");
				context.startActivity(intent);
			} else {
				Toast.makeText(context, R.string.URLERROR, Toast.LENGTH_SHORT)
				.show();
			}
		}
	}

	/**
	 * Add LoadMore view
	 * 
	 * @param position
	 * @param convertView
	 * @param parent
	 * @return
	 */

	class DeleteFavorButtonOnClickListener implements OnClickListener {
		protected final ArticleInfo articleInfo;
		public DeleteFavorButtonOnClickListener(ArticleInfo articleInfo) {
			this.articleInfo = articleInfo;
		}

		@Override
		public void onClick(View v) {
			FavorParameterInfo favorParameterInfo = (FavorParameterInfo) mActivity
			.getIntent().getSerializableExtra(
					FavorParameterInfo.SER_KEY);
			if (favorParameterInfo == null) {
				favorParameterInfo = new FavorParameterInfo();
			}
			final String articleId = articleInfo.getId();
			final DeleteFavorService deleteFavorService = new DeleteFavorService();
			new Thread() {
				public void run(){
					final String ret = deleteFavorService.setFavor(
							articleId);
					mActivity.runOnUiThread(new Runnable() {
						@Override
						public void run() {
							onDeleteFavResult(ret, articleId);
						}
					});		
				}
			}.start();
		}
		
		public void onDeleteFavResult(String ret, final String articleId) {
			if (DeleteFavorService.SUCCESS.equals(ret)) {
				// Call a dialog
				//AlertDialog.Builder alterDialog = new AlertDialog.Builder(mActivity);
				AlertDialog.Builder alterDialog = new AlertDialog.Builder(mActivity.getParent());
				
				alterDialog.setTitle(R.string.SuccessDialogTitle);
				alterDialog.setPositiveButton(R.string.DialogOKButton,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int i) {
								onDeleteRefresh(articleId);
							}
						});
				alterDialog
						.setMessage(mActivity.getString((R.string.DeleteFavorSuccess)));
				alterDialog.show();
				// Change to article page
			} else if (DeleteFavorService.ERROR.equals(ret)) {
				// Call a dialog
				//AlertDialog.Builder alterDialog = new AlertDialog.Builder(mActivity);
				AlertDialog.Builder alterDialog = new AlertDialog.Builder(mActivity.getParent());
				
				alterDialog.setTitle(R.string.FailedDialogTitle);
				alterDialog.setPositiveButton(R.string.DialogOKButton,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int i) {
								// finish();
							}
						});
				alterDialog.setMessage(R.string.CanNotConnect);
				alterDialog.show();
			} else {
				// Call a dialog
//				AlertDialog.Builder alterDialog = new AlertDialog.Builder(mActivity);
				AlertDialog.Builder alterDialog = new AlertDialog.Builder(mActivity.getParent());

				alterDialog.setTitle(R.string.CanNotConnect);
				alterDialog.setPositiveButton(R.string.FailedDialogTitle,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int i) {
							}
						});
				alterDialog.setMessage(ret);
				alterDialog.show();
			}
		}

	}
      public void onDeleteRefresh(String articleId){
    	 for (ArticleInfo article : articleList) {
			if (article.getId().equals(articleId)){
				articleList.remove(article);
				break;
			}
		}
    	 notifyDataSetChanged();
      }
	
	@Override
	public ArticleInfo getItem(int i) {
		return articleList.get(i);
	}

	@Override
	public long getItemId(int i) {
		ArticleInfo articleInfo = getItem(i);
		try {
			return Integer.parseInt(articleInfo.getId());
		} catch (NumberFormatException e) {
			return articleInfo.hashCode();
		}
	}
}
