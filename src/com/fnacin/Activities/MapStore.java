package com.fnacin.Activities;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.fnacin.pojo.StoreInfo;
import com.fnacin.service.GetStoreService;
import com.fnacin.Activities.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.support.v4.app.FragmentActivity;


public class MapStore extends FragmentActivity{
	private GoogleMap googleMap;
	float lat,lon;
	List<StoreInfo> storeList;
	@SuppressLint("NewApi")
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.map);
		int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getBaseContext());
	    // Showing status
	    if(status==ConnectionResult.SUCCESS)
	    {
	    	//googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
	    	googleMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
	    	googleMap.setMyLocationEnabled(false);
//System.out.println("googleMap----:"+googleMap);

	    }
	    else{

	        int requestCode = 10;
	        Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, getParent(), requestCode);
	        dialog.show();
	    }
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(48.870834,2.3043019),10));
		Drawable marker=getResources().getDrawable(R.drawable.marker);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),marker.getIntrinsicHeight());
		markStores();

	}
	private void markStores() {
		final FnacProgressDialog dialog = new FnacProgressDialog(MapStore.this.getParent());
		new Thread() {
			
			public void run() {
				storeList = GetStoreService.getStoreInfo();
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						if(storeList!=null){
						for (StoreInfo storeInfo : storeList) {
							try{
								lat=Float.parseFloat(storeInfo.getLatitude());
								lon=Float.parseFloat(storeInfo.getLongitude());
							}catch (NumberFormatException e) {
								e.printStackTrace();
							}
							googleMap.addMarker(new MarkerOptions()
							.position(new LatLng(lat,lon))
							.title(storeInfo.getNom())
							.snippet(storeInfo.getOuverture())
							.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));
						}
						

						dialog.dismiss();
						}else{
							dialog.dismiss();
							Toast.makeText(getApplicationContext(), "Erreur de communication avec le serveur", Toast.LENGTH_LONG).show();

						}
					}
				});
			}
		}.start();
	}
	
}
