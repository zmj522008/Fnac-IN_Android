package com.fnacin.Activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;

public class FavoGroup extends ActivityGroup{
	public static ActivityGroup group;
	public ArrayList<View> history;
	public ArrayList<String> viewList;
	public LocalActivityManager localManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		group=this;
	    this.history = new ArrayList<View>();
	    this.viewList = new ArrayList<String>();
	    localManager = getLocalActivityManager();
		startChildActivity("Favorite", new Intent(this,Favorite.class));
	}
	public void startChildActivity(String Id, Intent intent) {
		// TODO Auto-generated method stub
		Window window = getLocalActivityManager().startActivity(Id,intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		if (window != null) {
		viewList.add(Id);
		history.add(window.getDecorView());
System.out.println("history--Size---:"+history.size());
		setContentView(window.getDecorView());
		}
	}
@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	int length = viewList.size();
System.out.println("Lenth---:"+length);
			if ( length > 1) {
			Activity current = getLocalActivityManager().getActivity(viewList.get(length-1));
System.out.println("Current---:"+current);
			getLocalActivityManager().destroyActivity(viewList.get(length-1), true);
			viewList.remove(length-1);
			history.remove(length-1);
			/*
			  Intent intn = getLocalActivityManager().getActivity(viewList.get(length-2)).getIntent();
			 
			Window w = getLocalActivityManager().startActivity(viewList.get(length-2), intn);
			setContentView(w.getDecorView());
			*/
			setContentView(history.get(history.size()-1));
			}else getLocalActivityManager().getActivity(viewList.get(length-1)).onBackPressed();
			
	}
@Override
public boolean onCreateOptionsMenu(Menu menu) {
	// TODO Auto-generated method stub
	return this.getCurrentActivity().onCreateOptionsMenu(menu);
}
@Override
public boolean onMenuItemSelected(int featureId, MenuItem item) {

	// TODO Auto-generated method stub
	return this.getLocalActivityManager().getCurrentActivity().onMenuItemSelected(featureId, item);
	}
}
