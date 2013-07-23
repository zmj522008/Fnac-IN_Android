package com.fnacin.Activities;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.fnacin.pojo.StoreInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class StoreMapDetail extends FragmentActivity{
	private GoogleMap googleMap;
	double lat,lon;
	private StoreInfo storeInfo;
	private List<StoreInfo> storeList;
	private TextView tv_map_url;
	private TextView tv_name;
	private TextView tv_fax;
	private TextView tv_tel;
	private TextView tv_email;
	private TextView tv_adresse;
	private TextView tv_billeterie;
	private int versionSdk;
	// Log tag
	private static final String TAG = "fnac";
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		versionSdk = Integer.valueOf(android.os.Build.VERSION.SDK);
		
		if(versionSdk<10){
			setContentView(R.layout.store_detail_no_map);
			storeInfo = (StoreInfo) getIntent().getSerializableExtra(StoreInfo.SER_KEY);
			try{
				lat=Float.parseFloat(storeInfo.getLatitude());
				lon=Float.parseFloat(storeInfo.getLongitude());
			}catch (NumberFormatException e) {
				e.printStackTrace();
			}
			tv_map_url=(TextView) findViewById(R.id.map_url);
			String mapText="Votre tŽlŽphone ne supporte pas le module GoogleMap, veuillez cliquer le lien suivant:\n" +
					"https://maps.google.com/maps?q="+lat+","+lon;
			tv_map_url.setText(mapText);

		}else{
		
		setContentView(R.layout.store_detail);
		storeInfo = (StoreInfo) getIntent().getSerializableExtra(StoreInfo.SER_KEY);
		try{
			lat=Float.parseFloat(storeInfo.getLatitude());
			lon=Float.parseFloat(storeInfo.getLongitude());
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}
    	googleMap = ((SupportMapFragment)getSupportFragmentManager().findFragmentById(R.id.map)).getMap();

		//googleMap=((MapFragment) getFragmentManager().findFragmentById(R.id.map_detail)).getMap();
		}
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//googleMap.animateCamera(CameraUpdateFactory.zoomTo(15),2000,null);
		
		if(versionSdk<10){
			loadPersonData();
		}else{
		
			makeMarker();
			loadPersonData();
		}

	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		this.finish();
	}
	public void makeMarker(){
		if(String.valueOf(storeInfo.getLatitude()).length()==0 || String.valueOf(storeInfo.getLongitude()).length()==0){
			lat=48.870834;
			lon=2.3043019;
			LatLng latlon = new LatLng(lat,lon);	
			googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlon,5));
		}
		else{
		lat=Double.parseDouble(storeInfo.getLatitude());
		lon=Double.parseDouble(storeInfo.getLongitude());
		LatLng latlon = new LatLng(lat,lon);		
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlon,15));
		googleMap.addMarker(new MarkerOptions()
							.position(latlon)
							.title(storeInfo.getNom())
							.snippet(storeInfo.getOuverture())
							.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker)));}
	}
	public void loadPersonData() {
		tv_name=(TextView) this.findViewById(R.id.store_name);
		tv_fax=(TextView) this.findViewById(R.id.store_fax);
		tv_tel=(TextView) this.findViewById(R.id.store_tele);
		tv_email=(TextView) this.findViewById(R.id.store_email);
		tv_adresse=(TextView) this.findViewById(R.id.store_adresse);
		tv_billeterie=(TextView) this.findViewById(R.id.store_billeterie);
		// add value		
		tv_name.setText(storeInfo.getNom());
		
		SpannableString faxString = new SpannableString(storeInfo.getFax());
		faxString.setSpan(new UnderlineSpan(), 0, faxString.length(), 0);
		tv_fax.setText(faxString);
		tv_fax.setOnClickListener(new AppelOnClickListener(storeInfo.getFax()));
		
		SpannableString teleString =new SpannableString(storeInfo.getTelephone());
		teleString.setSpan(new UnderlineSpan(), 0, teleString.length(), 0);
		tv_tel.setText(teleString);
		int index = storeInfo.getTelephone().indexOf("(");
		if(index!=-1){
		String teleNum = storeInfo.getTelephone().substring(0, index-1);
		tv_tel.setOnClickListener(new AppelOnClickListener(teleNum));
		}else {
		tv_tel.setOnClickListener(new AppelOnClickListener(storeInfo.getTelephone()));
		}
		
		SpannableString emailString =new SpannableString(storeInfo.getEmail());
		emailString.setSpan(new UnderlineSpan(), 0, emailString.length(), 0);
		tv_email.setText(emailString);
		tv_email.setOnClickListener(new EmailOnClickListener(storeInfo.getEmail()));
		
		tv_adresse.setText(storeInfo.getAdresse());
		tv_billeterie.setText(storeInfo.getBilleterie());
	}
	private class AppelOnClickListener implements OnClickListener{
			private String title;
		public AppelOnClickListener(String teleString){
			this.title=teleString;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
		    try {
		    	AlertDialog.Builder build = new AlertDialog.Builder(StoreMapDetail.this.getParent());
		    	TextView titleView = new TextView(StoreMapDetail.this);
		    	titleView.setText(title);
		    	titleView.setGravity(Gravity.CENTER_HORIZONTAL);
		    	titleView.setTextColor(Color.rgb(0, 150, 255));
		    	titleView.setTextSize(20);
				build.setCustomTitle(titleView);
				build.setCancelable(false);
				build.setPositiveButton("Appeler", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent callIntent = new Intent(Intent.ACTION_CALL);
				        callIntent.setData(Uri.parse("tel:" + title));
				        startActivity(callIntent);
					}
				});
		        build.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.cancel();
					}
				});
		        AlertDialog myAlert = build.create();
		        myAlert.show();
		    } catch (ActivityNotFoundException e) {
		        Log.e("helloandroid dialing example", "Call failed", e);
		    }
		}
		
	}
	private class EmailOnClickListener implements OnClickListener{
		private String email;
		public EmailOnClickListener(String email){
			this.email=email;
		}
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			AlertDialog.Builder build = new AlertDialog.Builder(StoreMapDetail.this.getParent());
	    	TextView titleView = new TextView(StoreMapDetail.this);
	    	titleView.setText(email);
	    	titleView.setGravity(Gravity.CENTER_HORIZONTAL);
	    	titleView.setTextColor(Color.rgb(0, 150, 255));
	    	titleView.setTextSize(20);
			build.setCustomTitle(titleView);
			build.setCancelable(false);
			build.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					 Intent emailIntent = new Intent(Intent.ACTION_SEND);
				        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { email });
				        emailIntent.setType("message/rfc822");
				        startActivity(emailIntent);
				}
			});
			build.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			build.create().show();
		}
		
	}
}
