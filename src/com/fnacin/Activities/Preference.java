package com.fnacin.Activities;

import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import com.fnacin.interfaces.GoogleAnalytics;
import com.fnacin.pojo.CategoryInfo;
import com.fnacin.pojo.MutableArticleParameterInfo;
import com.fnacin.Activities.R;

public class Preference extends ArticleList {
	private final String TAG = "fnac";
	private ListView mainListView;
	private List<CategoryInfo> categoryInfoList;

	public Preference() {

		super(R.layout.tab_preference, DisplayType.NORMAL_DISPLAY);
	}

	// //GoogleAnalytics
	// GoogleAnalyticsTracker tracker;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GoogleAnalytics.trackPage("/INTRAFNAC - PREFERENCE", this);
		Button editButton = (Button) findViewById(R.id.btnEdit);
		editButton.setOnClickListener(new EditButtonOnClickListener());
		// loadCategoryPage();
		loadArticlePage();
	}

	protected void populateArticleParameterInfo(
			MutableArticleParameterInfo params) {
		params.setPrefere("1");
	}

	private void loadArticlePage() {
		setContentView(R.layout.tab_preference);
		// Filter
		super.getArticleViewAdapter().setmCurrentType(
				DisplayType.NORMAL_DISPLAY);
		resetViewAdapter();
		populateListArticle();
		// Set EditButton
		Button editButton = (Button) findViewById(R.id.btnEdit);
		// Set OnClickListener
		editButton.setOnClickListener(new EditButtonOnClickListener());
		Log.d(TAG, "Show preference articles");
	}

	private void loadArticlePageEdit() {

		setContentView(R.layout.tab_preference_edit);
		super.getArticleViewAdapter()
				.setmCurrentType(DisplayType.FAVOR_DISPLAY);
		resetViewAdapter();
		populateListArticle();
		// Set EditButton
		Button editButton = (Button) findViewById(R.id.btn_valide);
		// Set OnClickListener
		editButton.setOnClickListener(new ValideButtonOnClickListener());
	}

	// Load the category page
	/*
	 * private void loadCategoryPage() {
	 * this.setContentView(R.layout.tab_preference_edit); Log.d(TAG,
	 * "FavorActivity show preference category"); // Call home service function
	 * new Thread() { public void run() { final List<CategoryInfo> categories =
	 * PreferenceService .getCategoryInfoList("rubriques"); runOnUiThread(new
	 * Runnable() {
	 * 
	 * @Override public void run() { categoryInfoList = categories;
	 * onCategoryListLoaded(); } }); } }.start(); }
	 * 
	 * private void onCategoryListLoaded() { this.setListAdapter(new
	 * PreferenceViewAdapter( getApplicationContext(), categoryInfoList));
	 * 
	 * mainListView = this.getListView();
	 * 
	 * Log.d(TAG, "FavorActivity setItemsCanFocus false");
	 * mainListView.setItemsCanFocus(false);
	 * mainListView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE); //
	 * mainListView.setBackgroundColor(R.color.ListViewBackground);
	 * 
	 * // Load selections Log.d(TAG, "FavorActivity loadSelections");
	 * loadSelections(); // Set doneButton Log.d(TAG,
	 * "FavorActivity Set doneButton"); Button doneButton = (Button)
	 * findViewById(R.id.btnOK); // Set OnClickListener
	 * doneButton.setOnClickListener(new DoneButtonOnClickListener()); }
	 */

	// EditButtonOnClickListener
	class EditButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			// loadCategoryPage();
			// Set EditButton
			// Button editButton = (Button) findViewById(R.id.btnEdit);
			// Set OnClickListener
			// editButton.setOnClickListener(new EditButtonOnClickListener());
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

	/**
	 * 
	 * @Title: loadSelections
	 * @Description:
	 * @param
	 * @return void
	 * @throws
	 */
	public void loadSelections() {
		for (int i = 0; i < categoryInfoList.size(); i++) {
			if ("1".equals(categoryInfoList.get(i).getPreferenceFlag())) {
				mainListView.setItemChecked(i, true);
			} else {
				mainListView.setItemChecked(i, false);
			}
		}

	}

	// DoneButtonOnClickListener
	/*
	 * private class DoneButtonOnClickListener implements OnClickListener {
	 * 
	 * @Override public void onClick(View v) {
	 * 
	 * StringBuffer buf = new StringBuffer(); for(CategoryInfo categoryInfo:
	 * categoryInfoList) { if ("1".equals(categoryInfo.getPreferenceFlag())) {
	 * if (buf.length() > 0) { buf.append(','); }
	 * buf.append(categoryInfo.getCategoryId()); } } final String categoryIds =
	 * buf.toString(); new Thread() { public void run() {
	 * 
	 * final String ret1 = PreferenceService.setPreference(categoryIds);
	 * runOnUiThread(new Runnable() {
	 * 
	 * @Override public void run() { onPreferenceSet(ret1); } }); } }.start(); }
	 * 
	 * }
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			Log.d(TAG, "MenuActivity onKeyDown KEYCODE_BACK");
			dialog();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	protected void dialog() {
		AlertDialog.Builder builder = new Builder(this);
		builder.setMessage(R.string.ConfirmMessage);
		builder.setTitle(R.string.ConfirmTitle);
		builder.setPositiveButton(R.string.ConfirmBtnYes,
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
						finish();
					}

				});
		builder.setNegativeButton(R.string.ConfirmBtnNo,
				new android.content.DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.create().show();
	}

	/*
	 * private void onPreferenceSet(final String result) { if
	 * (PreferenceService.SUCCESS.equals(result)) { // Call a dialog
	 * 
	 * AlertDialog.Builder alterDialog = new AlertDialog.Builder(
	 * mainListView.getContext());
	 * alterDialog.setTitle(R.string.SuccessDialogTitle);
	 * alterDialog.setPositiveButton(R.string.DialogOKButton, new
	 * DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int i) {
	 * loadArticlePage(); } });
	 * alterDialog.setMessage(R.string.SetPreferenceSuccessMessage);
	 * alterDialog.show(); // Change to article page } else if
	 * (PreferenceService.ERROR.equals(result)) { // Call a dialog
	 * AlertDialog.Builder alterDialog = new AlertDialog.Builder(
	 * mainListView.getContext());
	 * alterDialog.setTitle(R.string.FailedDialogTitle);
	 * alterDialog.setPositiveButton(R.string.DialogOKButton, new
	 * DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int i) { finish();
	 * } }); alterDialog.setMessage(R.string.CanNotConnect); alterDialog.show();
	 * } else { // Call a dialog AlertDialog.Builder alterDialog = new
	 * AlertDialog.Builder( mainListView.getContext());
	 * alterDialog.setTitle(R.string.CanNotConnect);
	 * alterDialog.setPositiveButton(R.string.FailedDialogTitle, new
	 * DialogInterface.OnClickListener() {
	 * 
	 * @Override public void onClick(DialogInterface dialog, int i) { } });
	 * alterDialog.setMessage(result); alterDialog.show(); } }
	 */

}