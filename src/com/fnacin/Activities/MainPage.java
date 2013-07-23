package com.fnacin.Activities;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

import com.fnacin.Activities.R;

public class MainPage extends TabActivity {
	private TabHost mth;
	public static final String TAB_HOME = "TabHome";
	public static final String TAB_PREFERENCE = "TabPreference";
	public static final String TAB_PODCAST = "TabPodcast";
	public static final String TAB_CATEGORY = "TabCategory";
	public static final String TAB_FAVORITE = "TabFavorite";
	public RadioGroup mainbtGroup;
	// Log Tag
	private final String TAG = "fnac";
	private Intent cate_intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		Log.d(TAG, "MainPageActivity: onCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.maintabs);
		mth = this.getTabHost();
		mth.setup(this.getLocalActivityManager());
		// Initial parameter objects
		/*addTab("Podcast", Podcast.class, R.string.Podcast,R.drawable.tab_podcast_gray);
			addTab("Category", Category.class, R.string.Category,R.drawable.tab_category_gray);
			addTab("ArticleByCat", ArticleByCat.class, R.string.Category,R.drawable.tab_category_gray);
			*/
		
	    //addTab("Home", Home.class, R.string.Home, R.drawable.tab_home_gray);
	    addTab("Home", ArticleGroup.class,R.string.Home, R.drawable.tab_home_gray);
	    
		//addTab("Preference", Favorite.class, R.string.Favor,R.drawable.tab_preference_gray);
		addTab("Preference", FavoGroup.class, R.string.Favor,R.drawable.tab_preference_gray);
		
		//addTab("Annuaire", Person.class, R.string.Annuaire,R.drawable.annuaire);
		addTab("Annuaire", PersonGroup.class, R.string.Annuaire,R.drawable.annuaire);
		
		//addTab("Magasin", Store.class, R.string.Store,R.drawable.magasin);
		addTab("Magasin", StoreGroup.class, R.string.Store,R.drawable.magasin);
		
		//addTab("Others", Others.class, R.string.Document,R.drawable.icon_5_n);
		addTab("Others", OthersGroup.class, R.string.Document,R.drawable.icon_5_n);
		
		this.mainbtGroup = (RadioGroup) this.findViewById(R.id.main_radio);
		mainbtGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {
				Log.d("select ID", "---------------" + checkedId);
				/*switch (checkedId) {

				case R.id.radio_button1:
					mth.setCurrentTabByTag("Preference");
					break;
				//case R.id.radio_button2:
					//mth.setCurrentTabByTag("Podcast");
				//	break;
				case R.id.radio_button2:
					mth.setCurrentTabByTag("Annuaire");
					break;
				case R.id.radio_button3:
					mth.setCurrentTabByTag("Magasin");
					break;
				case R.id.radio_button4:
					mth.setCurrentTabByTag("Others");
					break;
				default:
					mth.setCurrentTabByTag("Home");
					break;
				}*/
				if(checkedId ==R.id.radio_button1){
					mth.setCurrentTabByTag("Preference");
				}else if(checkedId==R.id.radio_button2){
					mth.setCurrentTabByTag("Annuaire");
				}else if(checkedId==R.id.radio_button3){
					mth.setCurrentTabByTag("Magasin");
				}else if(checkedId==R.id.radio_button4){
					mth.setCurrentTabByTag("Others");
				}else{					
					mth.setCurrentTabByTag("Home");
					}
			}

		});
		mth.setCurrentTab(0);
		RadioButton rb = (RadioButton) findViewById(R.id.radio_button0);
		if (rb != null)
			rb.setChecked(true);
		Log.d("maintab", "maintab_MainActivity------onCreate");
		
		mth.setOnTabChangedListener(new OnTabChangeListener(){

			@Override
			public void onTabChanged(String tabId) {
				// TODO Auto-generated method stub
				System.out.println("TabID------:"+tabId);
				if(tabId =="Home"){
					mth.setCurrentTabByTag("Home");

				}
			}
			
		});
		
		getTabWidget().getChildAt(1).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("Here is child 0");
				if (getTabHost().getCurrentTabTag().equals("Home")) {
					System.out.println("Here is child home");
		        }
			}
		});
	}

	private void addTab(String tabText, Class<?> classe, int labelRessource,
			int ImageRessource) {
		Log.d(TAG, "MenuActivity: setContent " + tabText);
		Intent intent;
		TabHost.TabSpec spec;
		spec = mth.newTabSpec(tabText);
		intent = new Intent(this, classe);
		spec.setContent(intent);

		spec.setIndicator(getResources().getString(labelRessource),
				getResources().getDrawable(ImageRessource));
		mth.addTab(spec);
	}

}
