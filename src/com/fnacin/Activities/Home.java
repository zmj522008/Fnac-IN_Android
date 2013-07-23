package com.fnacin.Activities;

import static com.fnacin.service.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.fnacin.service.CommonUtilities.EXTRA_MESSAGE;
import static com.fnacin.service.CommonUtilities.SENDER_ID;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fnacin.interfaces.GoogleAnalytics;
import com.fnacin.pojo.AuthentificationInfo;
import com.fnacin.service.AuthentificationService;
import com.fnacin.service.ServerUtilities;
import com.google.android.gcm.GCMRegistrar;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

public class Home extends ArticleList {

	AsyncTask<Void, Void, Void> mRegisterTask;
	private Button themaButton;
	private PullToRefreshListView pullToRefreshView;
	SharedPreferences prefs;

	public Home() {
		super(R.layout.tab_home, DisplayType.NORMAL_DISPLAY);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// Log.d(TAG, "HomeActivity:onCreate");
		super.onCreate(savedInstanceState);

		prefs = getSharedPreferences(Home.class.getSimpleName(),
				Context.MODE_PRIVATE);
		setContentView(R.layout.tab_home);
		GoogleAnalytics.trackPage("/INTRAFNAC - HOME", this);

		themaButton = (Button) findViewById(R.id.bnt_category);
		themaButton.setOnClickListener(new ThematiqueButtonListener());

		// Get Registration Id and store it in the DB for Push
		this.getRegId();

		// Pull-TO-Refresh
		pullToRefreshView = (PullToRefreshListView) findViewById(R.id.pull_to_refresh_listview);
		pullToRefreshView
				.setOnRefreshListener(new OnRefreshListener<ListView>() {

					@Override
					public void onRefresh(
							PullToRefreshBase<ListView> refreshView) {
						// TODO Auto-generated method stub

						new GetDataTask().execute();

					}

				});
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// TODO Auto-generated method stub
			// populateListArticle();
			return null;
		}

		@Override
		protected void onPostExecute(String[] result) {
			// TODO Auto-generated method stub
			populateListArticle();
			pullToRefreshView.onRefreshComplete();
			super.onPostExecute(result);
		}

	}

	@Override
	protected void onStart() {
		super.onStart();
		populateListArticle();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//populateListArticle();
		//setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

	}

	private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
			// Waking up mobile if it is sleeping
			// WakeLock.acquire();
			/**
			 * Take appropriate action on this message depending upon your app
			 * requirement For now i am just displaying it on the screen
			 * */

			// Showing received message
			Toast.makeText(getApplicationContext(),
					"Nouveau Message: " + newMessage, Toast.LENGTH_LONG).show();

			// Releasing wake lock
			// WakeLocker.release();
		}
	};

	public boolean onCreateOptionsMenu(android.view.Menu menu) {
		System.out.println("OnCreatOptionMenu---Home");
		return false;
	};
	@Override
	public void onBackPressed() {
		new ExitDialog(getParent()).show();
		
	}

	public void getRegId() {

		// Get Session Id
		AuthentificationInfo authentificationInfo = AuthentificationService
				.getInstance().getAuthentificationInfo();
		String sessionId = "";
		if (authentificationInfo != null) {
			sessionId = authentificationInfo.getSessionId();
	System.out.println("sessonId------"+sessionId);
		}else{
			System.out.println("authentificationInfo------"+authentificationInfo);
			System.out.println("sessonId------"+sessionId);


		}
		

		// Make sure the device has the proper dependencies.
		GCMRegistrar.checkDevice(this);

		// Make sure the manifest was properly set - comment out this line
		// while developing the app, then uncomment it when it's ready.
		GCMRegistrar.checkManifest(this);

		registerReceiver(mHandleMessageReceiver, new IntentFilter(
				DISPLAY_MESSAGE_ACTION));

		// Get GCM registration id
		String regId = GCMRegistrar.getRegistrationId(this);
		if (regId != "") {
			// Enregistrer le Registration Id dans le base
			ServerUtilities.register(this, sessionId, regId);
		} else if (regId.equals("")) {
			// Registration is not present, register now with GCM
System.out.println("Registration is not present, register now with GCM");
			GCMRegistrar.register(this, SENDER_ID);
			regId = GCMRegistrar.getRegistrationId(this);
			// ServerUtilities.register(this, sessionId, regId);
		}
	}

	private class ThematiqueButtonListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent myIntent = new Intent();
			myIntent.setClass(Home.this, Thematique.class);
			ArticleGroup parent = (ArticleGroup) getParent();
			parent.startChildActivity("Thematique", myIntent);
			// startActivity(myIntent);

		}

	}

}
