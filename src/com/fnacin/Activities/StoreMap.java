package com.fnacin.Activities;

import java.util.ArrayList;
import java.util.List;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.fnacin.pojo.StoreInfo;
import com.fnacin.Activities.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

public class StoreMap extends MapActivity{
	private MapView map=null;
	private MyLocationOverlay me=null;
	double lat,lon;
	private StoreInfo storeInfo;
	List<StoreInfo> storeList;
	TextView tv_name;
	TextView tv_fax;
	TextView tv_tel;
	TextView tv_email;
	TextView tv_adresse;
	private TextView tv_billeterie;
	// Log tag
	private static final String TAG = "fnac";
	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.store_detail);
		storeInfo = (StoreInfo) getIntent()
		.getSerializableExtra(StoreInfo.SER_KEY);
		try{
			lat=Float.parseFloat(storeInfo.getLatitude());
			Log.d(TAG, "StoreMap lat===="+lat);
			lon=Float.parseFloat(storeInfo.getLongitude());
		}catch (NumberFormatException e) {
			e.printStackTrace();
		}
		map=(MapView)findViewById(R.id.map);
	}

	private GeoPoint getPoint(double lat, double lon) {
		return(new GeoPoint((int)(lat*1000000.0),(int)(lon*1000000.0)));
	}

	private class SitesOverlay extends ItemizedOverlay<OverlayItem> {
		private List<OverlayItem> items=new ArrayList<OverlayItem>();

		public SitesOverlay(Drawable marker) {
			super(marker);
			boundCenterBottom(marker);
			items.clear();
			items.add(new OverlayItem(getPoint(lat,lon),null, storeInfo.getNom()));
			populate();
		}

		@Override
		protected OverlayItem createItem(int i) {
			return(items.get(i));
		}

		@Override
		protected boolean onTap(int i) {

			return(true);
		}

		@Override
		public int size() {
			return(items.size());
		}
	}



	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onResume() {
		super.onResume();

		map.getController().setZoom(16);
		map.setSatellite(false);
		map.setBuiltInZoomControls(true);

		map.getOverlays().clear();
		
		Drawable marker=getResources().getDrawable(R.drawable.marker);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),marker.getIntrinsicHeight());

		map.getOverlays().add(new SitesOverlay(marker));
		me = new MyLocationOverlay(this, map);
		map.getOverlays().add(me);

		map.getController().animateTo(getPoint(lat, lon));

		loadPersonData();

		me.enableCompass();
	}		

	@Override
	public void onPause() {
		super.onPause();

		me.disableCompass();
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
		tv_fax.setText(storeInfo.getFax());
		tv_tel.setText(storeInfo.getTelephone());
		tv_email.setText(storeInfo.getEmail());
		tv_adresse.setText(storeInfo.getAdresse());
		tv_billeterie.setText(storeInfo.getBilleterie());
	}

}
