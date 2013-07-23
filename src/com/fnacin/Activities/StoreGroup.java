package com.fnacin.Activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;

public class StoreGroup extends ActivityGroup{
	public static StoreGroup group;
	public ArrayList<View> history;
	public ArrayList<String> mIdList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	    this.history = new ArrayList<View>();
		group=this;
		if (mIdList == null) mIdList = new ArrayList<String>();
		startChildActivity("Store", new Intent(this,Store.class));

	}

	/**
	* This is called when a child activity of this one calls its finish method.
	* This implementation calls {@link LocalActivityManager#destroyActivity} on the child activity
	* and starts the previous activity.
	* If the last child activity just called finish(),this activity (the parent),
	* calls finish to finish the entire group.
	*/
	@Override
	public void finishActivityFromChild(Activity child, int requestCode) {
		// TODO Auto-generated method stub
		super.finishActivityFromChild(child, requestCode);
		LocalActivityManager manager = getLocalActivityManager();
		int index = mIdList.size()-1;
		System.out.println("--index---:"+index);

		if (index < 1) {
		finish();
		return;
		}

		manager.destroyActivity(mIdList.get(index), true);
		mIdList.remove(index);
		index--;
		String lastId = mIdList.get(index);
		Intent lastIntent = manager.getActivity(lastId).getIntent();
		Window newWindow = manager.startActivity(lastId, lastIntent);
		setContentView(newWindow.getDecorView());
	}
	/**
	* Starts an Activity as a child Activity to this.
	* @param Id Unique identifier of the activity to be started.
	* @param intent The Intent describing the activity to be started.
	* @throws android.content.ActivityNotFoundException.
	*/
	public void startChildActivity(String Id, Intent intent) {
		Window window = getLocalActivityManager().startActivity(Id,intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
		if (window != null) {
		mIdList.add(Id);
		history.add(window.getDecorView());
		setContentView(window.getDecorView());
		}
		}
	/**
	* Overrides the default implementation for KeyEvent.KEYCODE_BACK
	* so that all systems call onBackPressed().
	*/
	/*@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
	if (keyCode == KeyEvent.KEYCODE_BACK) {
		System.out.println("--onKeyUp---:"+mIdList.size());

	onBackPressed();
	return true;
	}
	return super.onKeyUp(keyCode, event);
	}
	*/
	/**
	* If a Child Activity handles KeyEvent.KEYCODE_BACK.
	* Simply override and add this method.
	*/
	@Override
	public void onBackPressed () {
	int length = mIdList.size();
	System.out.println("--lenth---:"+length);
	System.out.println("--Historylenth---:"+history.size());

	if ( length > 1) {
	Activity current = getLocalActivityManager().getActivity(mIdList.get(length-1));
	System.out.println("Current---:"+current);
	getLocalActivityManager().destroyActivity(mIdList.get(length-1), true);
	mIdList.remove(length-1);
	history.remove(length-1);
	/*
	Intent intn = getLocalActivityManager().getActivity(mIdList.get(length-2)).getIntent();
	Window w = getLocalActivityManager().startActivity(mIdList.get(length-2), intn);
	setContentView(w.getDecorView());
	*/
	System.out.println("Current--View-:"+getLocalActivityManager().getActivity(mIdList.get(length-2)));

	setContentView(history.get(history.size()-1));
	
	}else getLocalActivityManager().getActivity(mIdList.get(length-1)).onBackPressed();
	}
}
