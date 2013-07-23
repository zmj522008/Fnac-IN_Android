package com.fnacin.Activities;

import java.io.InputStream;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fnacin.adapter.CommentViewList;
import com.fnacin.helper.AsyncImageLoader;
import com.fnacin.pojo.ArticleInfo;
import com.fnacin.pojo.FavorParameterInfo;
import com.fnacin.service.SetFavorService;
import com.fnacin.service.SetLikeService;

public class DisplayArticle extends Activity {
	private static String TAG = "fnac";
	private TextView commentbtn = null;
	private TextView favbtn = null;
	private Button returnbtn = null;
	private Button watchbtn = null;
	private TextView setLikebtn = null;
	private TextView articleContent = null;
	private TextView submitButton;
	private Activity mainListView;
	private TextView commentContent;
	private CommentViewList commentViewList;
	private String articleId;
	private LinearLayout commentListLayout;
	private ArticleInfo articleInfo;
	private Button toggleCommentVisiblity;
	private TextView nb_comments;
	private Button btn_return;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// GoogleAnalytics
		setContentView(R.layout.article_detail);
		articleInfo = (ArticleInfo) getIntent().getSerializableExtra(
				ArticleInfo.SER_KEY);
		// GoogleAnalytics
		//GoogleAnalytics.trackPage("/ INTRAFNAC - ARTICLE /" + articleInfo.getTitle(), this);
		// comment
		commentListLayout = (LinearLayout) findViewById(R.id.commentList);
		submitButton = (TextView) findViewById(R.id.btnOK);
		//submitButton.setOnClickListener(new SubmitButtonOnClickListener());
		articleId = articleInfo.getId();
		Log.d(TAG, "CommentActivity setAdapter length:"
				+ articleInfo.getCommentsList().size());
		Log.d(TAG, "CommentActivity addHeaderView");
		// View view =
		// LayoutInflater.from(this).inflate(R.layout.comment_header, null);
		commentContent = (TextView) this.findViewById(R.id.content);
		toggleCommentVisiblity = (Button) this
				.findViewById(R.id.add_comment_button);
		//toggleCommentVisiblity.setOnClickListener(new ToggleCommentVisiblityClickListener());
		// detail
		commentbtn = (TextView) findViewById(R.id.article_comment_btn);
		btn_return =(Button) findViewById(R.id.bnt_retour);
		btn_return.setOnClickListener(new RetourOnClickListener());
	
		nb_comments = (TextView) this.findViewById(R.id.nb_comments);
		// favbtn = (TextView) findViewById(R.id.article_favoris_btn);
		// favbtn.setText(this.getString(R.string.AddFavorisButton));
		// favbtn.setOnClickListener(new FavbtnListener(articleInfo));
		watchbtn = (Button) this.findViewById(R.id.detail_watch_button);
		//watchbtn.setOnClickListener(new WatchButtonOnClickListener(this,articleInfo));
		watchbtn.setOnClickListener(new WatchButtonOnClickListener(getParent(),articleInfo));

		// setLikebtn = (TextView) findViewById(R.id.article_like_btn);
		// setLikebtn.setOnClickListener(new LikebtnListener(articleInfo));
		final ArticleImageView imageView = (ArticleImageView) findViewById(R.id.article_image);
		imageView.setArticleInfo(articleInfo);
		if ("1".equals(articleInfo.getTypeId())) {
			watchbtn.setVisibility(View.INVISIBLE);
		} else {
			//watchbtn.setOnClickListener(new WatchButtonOnClickListener(this,articleInfo));
			//imageView.setOnClickListener(new WatchButtonOnClickListener(this,articleInfo));
			watchbtn.setOnClickListener(new WatchButtonOnClickListener(getParent(),articleInfo));
			imageView.setOnClickListener(new WatchButtonOnClickListener(getParent(),articleInfo));
		}
		// get category
		TextView articleCate = (TextView) findViewById(R.id.article_cate);
		articleCate.setText(articleInfo.getCategory());
		// get theme
		TextView articleTheme = (TextView) findViewById(R.id.article_theme);
		articleTheme.setText(articleInfo.getTheme());
		// get title
		TextView articleTitle = (TextView) findViewById(R.id.article_title);
		articleTitle.setText(articleInfo.getTitle());
		// get content
		articleContent = (TextView) findViewById(R.id.article_content);
		String sourceContent=articleInfo.getAccroche() + "\n\n"+ articleInfo.getContent();
		
		 ImageGetter imageGetter = new Html.ImageGetter() {
		 
			
			@Override
			public Drawable getDrawable(String source) {
				// TODO Auto-generated method stub
				try{
					InputStream is=(InputStream) new URL(source).getContent();
					Drawable drawable = Drawable.createFromStream(is, "src");
					drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
					return drawable;
				}catch(Exception e){
					return null;
				}
				
			}
		};
		articleContent.setText(Html.fromHtml(sourceContent, imageGetter, null));
		//articleContent.setMovementMethod(new ScrollingMovementMethod());
		articleContent.setMovementMethod(LinkMovementMethod.getInstance());

		// Comments disabled 8/01/2012
		// updateComments();
		// updateLikeButton();
	}

	@Override
	protected void onResume() {
		super.onResume();
		AsyncImageLoader.getInstance().start();
	}

	@Override
	protected void onPause() {
		super.onPause();
		AsyncImageLoader.getInstance().stop();
	}

	private void updateLikeButton() {
		setLikebtn.setText(this.getString(R.string.LikeButton) + " ("
				+ articleInfo.getNb_like() + ")");
	}

	/*
	 *private void updateComments() {
	 
		commentbtn.setText(this.getString(R.string.CommentButton) + " ("
				+ articleInfo.getNb_comment() + ")");
		nb_comments.setText("(" + articleInfo.getNb_comment() + " "
				+ this.getString(R.string.commentLabel) + ")");
		new Thread() {
			public void run() {
				final List<CommentInfo> commentList = GetCommentService
						.getInfoList(articleId);
				runOnUiThread(new Runnable() {

					@Override
					public void run() {
						if (commentViewList == null) {
							commentViewList = new CommentViewList(
									DisplayArticle.this, commentList);

						} else {
							commentViewList.setList(commentList);
						}
						List<View> list = commentViewList.getViews();
						commentListLayout.removeAllViews();
						for (View v : list) {
							commentListLayout.addView(v);
						}
					}
				});
			}
		}.start();
	}
*/
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
	
		//getMenuInflater().inflate(R.menu.article_detail_menu, menu);
		(this.getParent()).getMenuInflater().inflate(R.menu.article_detail_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		/*
		 * switch(item.getItemId()){ case R.id.like_btn: LikebtnListener likebtn
		 * = new LikebtnListener(articleInfo);
		 * likebtn.onClick(this.findViewById(R.id.ll_top)); break; case
		 * R.id.favor_btn: FavbtnListener favbtn = new
		 * FavbtnListener(articleInfo);
		 * favbtn.onClick(this.findViewById(R.id.ll_top)); break;
		 * 
		 * }
		 */
		int id = item.getItemId();
		if (id == R.id.like_btn) {
			LikebtnListener likebtn = new LikebtnListener(articleInfo);
			likebtn.onClick(this.findViewById(R.id.ll_top));
		} else if (id == R.id.favor_btn) {
			FavbtnListener favbtn = new FavbtnListener(articleInfo);
			favbtn.onClick(this.findViewById(R.id.ll_top));
		}
		//return super.onMenuItemSelected(featureId, item);
		return true;
	}

	/*private void onCommentResult(String ret) {
		if (ret == BaseService.SUCCESS) {
			AlertDialog.Builder alterDialog = new AlertDialog.Builder(
					DisplayArticle.this);
			alterDialog.setTitle(R.string.SuccessDialogTitle);
			alterDialog.setPositiveButton(R.string.DialogOKButton,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int i) {
							articleInfo.setNb_comment(Integer.toString(Integer
									.parseInt(articleInfo.getNb_comment()) + 1));
							updateComments();
						}
					});
			alterDialog.setMessage(R.string.SetCommentSuccessMessage);
			alterDialog.show();

		} else {

			AlertDialog.Builder alterDialog = new AlertDialog.Builder(
					DisplayArticle.this);
			alterDialog.setTitle(R.string.FailedDialogTitle);
			alterDialog.setPositiveButton(R.string.DialogOKButton,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int i) {

						}
					});
			alterDialog.setMessage(R.string.LabelError);
			alterDialog.show();
		}
	}
*/
	private void onLikeResult(String ret) {
		if (SetLikeService.SUCCESS.equals(ret)) {
			// Call a dialog
			AlertDialog.Builder alterDialog = new AlertDialog.Builder(DisplayArticle.this.getParent());
			//AlertDialog.Builder alterDialog = new AlertDialog.Builder(DisplayArticle.this);

			alterDialog.setTitle(R.string.SuccessDialogTitle);
			alterDialog.setPositiveButton(R.string.DialogOKButton,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int i) {
							// finish();
						}
					});
			alterDialog.setMessage(R.string.LabelSuccess);
			alterDialog.show();
			articleInfo.setNb_like(Integer.toString(Integer
					.parseInt(articleInfo.getNb_like()) + 1));
			// updateLikeButton();
			// Change to article page
		} else if (SetLikeService.ERROR.equals(ret)) {
			// Call a dialog
			AlertDialog.Builder alterDialog = new AlertDialog.Builder(DisplayArticle.this.getParent());

			//AlertDialog.Builder alterDialog = new AlertDialog.Builder(DisplayArticle.this);
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
		} else if(ret.equals("E")){
			Toast.makeText(getApplicationContext(), "Erreur de communication avec le serveur", Toast.LENGTH_LONG).show();

		}else {
			// Call a dialog
			//AlertDialog.Builder alterDialog = new AlertDialog.Builder(DisplayArticle.this);
			AlertDialog.Builder alterDialog = new AlertDialog.Builder(DisplayArticle.this.getParent());

			alterDialog.setTitle(R.string.FailedDialogTitle);
			alterDialog.setPositiveButton(R.string.DialogOKButton,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int i) {
						}
					});
			alterDialog.setMessage(ret);
			alterDialog.show();
		}
	}
/*
	class ToggleCommentVisiblityClickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (commentContent.getVisibility() == View.GONE
					&& submitButton.getVisibility() == View.GONE) {
				commentContent.setVisibility(View.VISIBLE);
				submitButton.setVisibility(View.VISIBLE);
				toggleCommentVisiblity.setVisibility(View.GONE);
				nb_comments.setVisibility(View.GONE);
			} else {

				submitButton.setVisibility(View.GONE);
			}
		}

	}
*/
	
	@Override
	public void onBackPressed() {
		System.out.println("ArticleDetail--	OnBacePressed");

		if (submitButton.getVisibility() == View.VISIBLE) {
System.out.println("ArticleDetail--	OnBacePressed----");
			submitButton.setVisibility(View.GONE);
			toggleCommentVisiblity.setVisibility(View.VISIBLE);
			commentContent.setVisibility(View.GONE);
			nb_comments.setVisibility(View.VISIBLE);
		} else {
			System.out.println("ArticleDetail--	OnBacePressed---finish");
			
Intent intent = new Intent(DisplayArticle.this,Home.class);
startActivity(intent);
			//finish();
		}
	}
@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
System.out.println("OnKeyDown----ArticleDetail");
	return super.onKeyDown(keyCode, event);
}
@Override
public boolean onKeyUp(int keyCode, KeyEvent event) {
	// TODO Auto-generated method stub
	System.out.println("OnkeyUp-----ArticleDetail");

	return super.onKeyUp(keyCode, event);
}

@Override
public boolean dispatchKeyEvent(KeyEvent event) {
	// TODO Auto-generated method stub
	System.out.println("dispatchKeyEvent-----ArticleDetail");

	return super.dispatchKeyEvent(event);
}

class RetourOnClickListener implements OnClickListener{

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
System.out.println("ClassName:"+getParent().getLocalClassName().toString());		
		if(getParent().getLocalClassName().toString().equals("FavoGroup")){
			Intent myIntent = new Intent();
			myIntent.setClass(DisplayArticle.this, Favorite.class);
			FavoGroup favoParent = (FavoGroup) getParent();
			favoParent.startChildActivity("Favorite", myIntent);

		}else{
			Intent myIntent = new Intent();
			myIntent.setClass(DisplayArticle.this, Home.class);
			ArticleGroup parent = (ArticleGroup) getParent();
			parent.startChildActivity("Home", myIntent);
		}
		}
	
}

	class FavbtnListener implements OnClickListener {
		protected final ArticleInfo articleInfo;

		public FavbtnListener(ArticleInfo articleInfo) {
			this.articleInfo = articleInfo;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			FavorParameterInfo favorParameterInfo = (FavorParameterInfo) getIntent()
					.getSerializableExtra(FavorParameterInfo.SER_KEY);
			if (favorParameterInfo == null) {
				favorParameterInfo = new FavorParameterInfo();
			}
			final String articleId = articleInfo.getId();
			final SetFavorService setFavorService = new SetFavorService();
			new Thread() {
				@Override
				public void run() {
					final String ret = setFavorService.setFavor(articleId);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							onFavReslult(ret);
						}
					});
				}
			}.start();

		}

		private void onFavReslult(String ret) {
			if (SetFavorService.SUCCESS.equals(ret)) {
				// Call a dialog
				Integer fav = Integer.parseInt(articleInfo.getFavor());
				switch (fav) {
				case 1:
					//AlertDialog.Builder alterDialog = new AlertDialog.Builder(DisplayArticle.this);
					AlertDialog.Builder alterDialog = new AlertDialog.Builder(DisplayArticle.this.getParent());

					alterDialog.setTitle(R.string.FailedDialogTitle);
					alterDialog.setPositiveButton(R.string.DialogOKButton,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int i) {
								}
							});
					alterDialog.setMessage(R.string.DialogFavErrorButton);
					alterDialog.show();
					break;
				case 0:

					//AlertDialog.Builder alterDialog1 = new AlertDialog.Builder(DisplayArticle.this);
					AlertDialog.Builder alterDialog1 = new AlertDialog.Builder(DisplayArticle.this.getParent());

					alterDialog1.setTitle(R.string.SuccessDialogTitle);
					alterDialog1.setPositiveButton(R.string.DialogOKButton,
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int i) {

								}
							});
					alterDialog1.setMessage(R.string.LabelSuccess);
					alterDialog1.show();
					break;
				default:
					break;
				// Change to article page
				}
			} else if (SetFavorService.ERROR.equals(ret)) {
				// Call a dialog
				AlertDialog.Builder alterDialog = new AlertDialog.Builder(DisplayArticle.this.getParent());

				alterDialog.setTitle(R.string.FailedDialogTitle);
				alterDialog.setPositiveButton(R.string.DialogOKButton,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int i) {
							}
						});
				alterDialog.setMessage(ret);
				alterDialog.show();
				

			} else if(ret.equals("E")) {
				// Call a dialog
				//AlertDialog.Builder alterDialog = new AlertDialog.Builder(mainListView.getBaseContext());
				/*
				 *AlertDialog.Builder alterDialog = new AlertDialog.Builder(DisplayArticle.this.getParent());
				 
				alterDialog.setTitle(R.string.FailedDialogTitle);
				alterDialog.setPositiveButton(R.string.DialogOKButton,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int i) {
							}
						});
				alterDialog.setMessage(ret);
				alterDialog.show();
				*/
				Toast.makeText(getApplicationContext(), "Erreur de communication avec le serveur", Toast.LENGTH_LONG).show();

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
			String vinetteUrl = articleInfo.getUrl_image();
			int a = url.lastIndexOf(".");
			String b = url.substring(a, url.length());
			Log.d(TAG, a + " | " + b);
System.out.println("url------"+url);
System.out.println("vinetteUrl------"+vinetteUrl);

			if (b.toLowerCase().equals(".mp4")
					|| b.toLowerCase().equals(".3gp")
					|| b.toLowerCase().equals(".wmv")) {
				Log.d(TAG, "Watch:" + url);
				Uri uri = Uri.parse(url);

				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setType("video/*");
				intent.setDataAndType(uri, "video/*");
				context.startActivity(intent);
			} else if (b.toLowerCase().equals(".mp3")) {
				Log.d(TAG, "Listen:" + url);
				Uri uri = Uri.parse(url);
				Uri vinettUri = Uri.parse(vinetteUrl);
				Intent intent = new Intent(Intent.ACTION_VIEW, uri);
				// intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.setType("audio/mp3");
				intent.setDataAndType(vinettUri, "image/*");
				intent.setDataAndType(uri, "audio/mp3");
				context.startActivity(intent);
			} else {
				Toast.makeText(context, R.string.URLERROR, Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

	class LikebtnListener implements OnClickListener {
		protected final ArticleInfo articleInfo;

		public LikebtnListener(ArticleInfo articleInfo) {
			this.articleInfo = articleInfo;
		}

		@Override
		public void onClick(View v) {
			//final FnacProgressDialog dialog = new FnacProgressDialog(DisplayArticle.this);
			final FnacProgressDialog dialog = new FnacProgressDialog(DisplayArticle.this.getParent());

			final String articleId = articleInfo.getId();
			final SetLikeService setLikeService = new SetLikeService();
			new Thread() {
				@Override
				public void run() {
					final String ret = setLikeService.setLike(articleId);

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							dialog.dismiss();
							onLikeResult(ret);
						}
					});
				}
			}.start();

		}

	}
/*
	class SubmitButtonOnClickListener implements OnClickListener {

		ArticleInfo articleInfo = (ArticleInfo) getIntent()
				.getSerializableExtra(ArticleInfo.SER_KEY);
		AuthentificationInfo authentificationInfo = AuthentificationService
				.getInstance().getAuthentificationInfo();

		@Override
		public void onClick(View v) {
			final FnacProgressDialog dialog = new FnacProgressDialog(
					DisplayArticle.this);
			submitButton.setVisibility(View.GONE);
			toggleCommentVisiblity.setVisibility(View.VISIBLE);
			nb_comments.setVisibility(View.VISIBLE);
			Log.d(TAG, "CommentActivity SubmitButtonOnClickListener onClick");
			final String content = commentContent.getText().toString();
			final CommentService commetnService = new CommentService();
			final String articleId = articleInfo.getId();
			new Thread() {
				public void run() {
					final String ret = commetnService.setComment(articleId,
							content);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							dialog.dismiss();
							onCommentResult(ret);
							commentContent.setVisibility(View.GONE);
						}
					});
				}
			}.start();
		}
	}
	*/
}
