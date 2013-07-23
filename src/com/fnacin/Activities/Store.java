package com.fnacin.Activities;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.fnacin.adapter.StoreViewAdapter;
import com.fnacin.database.DBHelper;
import com.fnacin.helper.AsyncImageLoader;
import com.fnacin.interfaces.GoogleAnalytics;
import com.fnacin.pojo.StoreInfo;
import com.fnacin.service.GetStoreService;
import com.fnacin.Activities.R;

public class Store extends Activity {
	// Log Tag
	private final String TAG = "fnac";
    private Button map_button;
   // private List<StoreInfo> storeList;
    private int versionSdk;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GoogleAnalytics.trackPage("/INTRAFNAC - MAGASIN", this);
		setContentView(R.layout.tab_store);
		Log.d(TAG, "Store:onCreate");
		versionSdk = Integer.valueOf(android.os.Build.VERSION.SDK);
System.out.println("versionSDK-----:"+versionSdk);
		final DBHelper helper = new DBHelper(this);
		// open database
		helper.openDatabase();
		this.openThread();
		map_button=(Button) this.findViewById(R.id.btnMap);
		map_button.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
					Intent intent;
					/*final String zoom = "15";
					final double lat = 48.1059505; 
					final double lon = -1.6806113;
					String startmaps = "geo:" + lat + "," + lon + "?z=" + zoom;
					Intent intent =  new Intent(android.content.Intent.ACTION_VIEW,Uri.parse(startmaps));
					 */
					//intent.setClass(v.getContext(), AllStoresMap.class);
					//intent.setClass(v.getContext(), MapStore.class);
					//String mapUrl = "http://www.maps.google.com/maps?f=d&geo=48.1059505,-1.6806113";
					//String mapUrl="https://maps.google.com/maps?q=48.1059505,-1.6806113";
				
					if(versionSdk<10){
				
					String mapUrl="https://maps.google.com/maps?q=fnac";
					 intent =  new Intent(android.content.Intent.ACTION_VIEW,Uri.parse(mapUrl));
					 v.getContext().startActivity(intent);
				}else{
				
					 intent =  new Intent(getParent(),MapStore.class);
					 StoreGroup parent = (StoreGroup) getParent();
					 parent.startChildActivity("MapStore", intent);
				}
				//v.getContext().startActivity(intent);

				}
		});
	}

public void openThread(){
	
	new Thread() {
		FnacProgressDialog dialog = new FnacProgressDialog(Store.this.getParent());
		public void run() {
			 final List<StoreInfo> storeList = GetStoreService.getStoreInfo();
			//Log.d(TAG, "storeList size=="+storeList.size());
			dialog.dismiss();
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					final ListView lv = (ListView) findViewById(R.id.store_list);
					lv.setFastScrollEnabled(true);
					if(storeList!=null){
					lv.setAdapter(new StoreViewAdapter(Store.this,
							storeList));
					lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0,
								View view, int arg2, long arg3) {
							StoreInfo storeInfo =storeList.get(arg2);
							Bundle mBundle = new Bundle();
							mBundle.putSerializable(StoreInfo.SER_KEY, storeInfo);
							Intent intent = new Intent();
							intent.putExtras(mBundle);
							intent.setClass(getParent(), StoreMapDetail.class);
							//Window w = StoreGroup.group.getLocalActivityManager().startActivity("StoreMapDetail", intent);
							//View v =w.getDecorView();
							//StoreGroup.group.history.add(v);
							//StoreGroup.group.replaceView(v);
							//lv.getContext().startActivity(intent);
							StoreGroup parentActivity = (StoreGroup) getParent();
							parentActivity.startChildActivity("StoreMapDetail", intent);
						}
					});
					
				}else{
					Toast.makeText(getApplicationContext(), "Erreur de communication avec le serveur", Toast.LENGTH_LONG).show();

				}
				}
			});
		}
	}.start();
}
	@Override
	public void onResume() {
System.out.println("Store on Resume--------");
		super.onResume();
		/*AsyncImageLoader.getInstance().start();
		final ListView lv = (ListView) findViewById(R.id.store_list);
		int index = lv.getFirstVisiblePosition();
		View v = lv.getChildAt(index);
		int top = (v == null) ? 0 : v.getTop();
		lv.setSelectionFromTop(index, top);*/
this.openThread();
	}
	
	
	  @Override
	
	protected void onPause() {
System.out.println("Store on Pause--------");
		super.onPause();
		//AsyncImageLoader.getInstance().stop();
	}


	@Override
	public void onBackPressed() {
System.out.println("Store on backPressed--------");
		new ExitDialog(this.getParent()).show();
	}
	
}