package com.fnacin.Activities;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.fnacin.helper.AsyncImageLoader;
import com.fnacin.helper.AsyncImageLoader.ImageCallback;
import com.fnacin.pojo.ArticleInfo;
import com.fnacin.Activities.R;

public class ArticleImageView extends ImageView implements ImageCallback {

	private String baseUrl;

	public ArticleImageView(Context context) {
		super(context);
	}

	public ArticleImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}	

	 public void setArticleInfo(final ArticleInfo articleInfo) {
		if (baseUrl == null || !baseUrl.equals(articleInfo.getUrl_image())) {
			baseUrl = articleInfo.getUrl_image();
			setImageDrawable(getResources().getDrawable(R.drawable.loading_detail));
			loadArticleImage();
		}
	
	 }
	protected String loadArticleImage() {
		int width = getWidth();
		int height = getHeight();
		if (width == 0 || height == 0) {
			return null;
		}
		final String imageUrl = baseUrl + "/" + width + "/" + height;
		Log.d("FNAC", "loadArticleImage: " + imageUrl + " by " + this);
		Drawable cachedDrawable = AsyncImageLoader.getInstance().loadDrawable(imageUrl, this);
		if (cachedDrawable != null) {
			setImageDrawable(cachedDrawable);
		}
		return imageUrl;
	}

	public void imageLoaded(final Drawable imageDrawable,
			final String loadedImageUrl) {
		if (getContext() instanceof Activity) {
			((Activity) getContext()).runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (loadedImageUrl.startsWith(baseUrl)) {
						Log.d("FNAC", "imageLoaded: " + loadedImageUrl + " by " + ArticleImageView.this);
						setImageDrawable(imageDrawable);
					} else {
						Log.d("FNAC", "Invalid image received: " + loadedImageUrl + " by " + ArticleImageView.this);
					}
				}
			});
		}
	}

	@Override
	public void onSizeChanged(int w, int h, int oldw, int oldh) {
		loadArticleImage();
	}
}
