package com.fnacin.Activities;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.fnacin.pojo.StoreInfo;
import com.fnacin.service.GetStoreService;
import com.fnacin.Activities.R;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.OverlayItem;

public class AllStoresMap extends FragmentActivity implements LocationListener {
	// Log tag
	private static final String TAG = "fnac";

	private MapView map = null;
	private MyLocationOverlay me = null;
	float lat, lon;

	// private StoreInfo storeInfo;
	List<StoreInfo> storeList;
	private LocationManager locationManager;

	@Override
	protected void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.map);
		map = (MapView) findViewById(R.id.map);
		locationManager = (LocationManager) this
				.getSystemService(LOCATION_SERVICE);
	}

	private void centerOnUser() {
		Location location = locationManager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null) {
			location = locationManager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
		if (location != null) {
			Log.d(TAG, location.toString());
			this.onLocationChanged(location);
		}
	}

	private GeoPoint getPoint(double lat2, double lon2) {
		return (new GeoPoint((int) (lat2 * 1000000.0), (int) (lon2 * 1000000.0)));
	}

	private class SitesOverlay extends ItemizedOverlay<OverlayItem> {
		private List<Item> items = new ArrayList<Item>();
		final FnacProgressDialog dialog = new FnacProgressDialog(
				AllStoresMap.this);

		private class Item extends OverlayItem {
			public StoreInfo storeInfo;

			public Item(GeoPoint point, String title, String snippet,
					StoreInfo storeInfo) {
				super(point, title, snippet);
				this.storeInfo = storeInfo;
			}
		}

		public SitesOverlay(Drawable marker) {
			super(marker);
			boundCenterBottom(marker);
			new Thread() {
				public void run() {
					storeList = GetStoreService.getStoreInfo();
					Log.d(TAG, "storeList size==" + storeList.size());

					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							items.clear();
							for (StoreInfo storeInfo : storeList) {
								try {
									lat = Float.parseFloat(storeInfo
											.getLatitude());
									Log.d(TAG, "lat====" + lat);
									lon = Float.parseFloat(storeInfo
											.getLongitude());
								} catch (NumberFormatException e) {
									e.printStackTrace();
								}
								map.getController().setCenter(
										getPoint(lat, lon));
								Log.v(TAG,
										items.size() + ": "
												+ storeInfo.getNom());
								items.add(new Item(getPoint(lat, lon), null,
										storeInfo.getNom(), storeInfo));
							}

							dialog.dismiss();
							populate();
							centerOnUser();
						}
					});
				}
			}.start();

		}

		@Override
		protected OverlayItem createItem(int i) {
			return (items.get(i));
		}

		@Override
		protected boolean onTap(int i) {
			final AllStoresMap context = AllStoresMap.this;

			Item item = items.get(i - 1);
			Log.v(TAG, "onTap(" + i + ") " + item.storeInfo.getNom());

			Bundle mBundle = new Bundle();
			mBundle.putSerializable(StoreInfo.SER_KEY, item.storeInfo);
			Intent intent = new Intent();
			intent.putExtras(mBundle);
			intent.setClass(context, StoreMap.class);
			context.startActivity(intent);

			return (true);
		}

		@Override
		public int size() {
			return (items.size());
		}
	}

	protected boolean isRouteDisplayed() {
		return false;
	}

	@Override
	public void onResume() {
		super.onResume();
		map.getController().setZoom(12);
		map.setSatellite(false);
		map.setBuiltInZoomControls(true);

		Drawable marker = getResources().getDrawable(R.drawable.marker);
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight());
		map.getOverlays().add(new SitesOverlay(marker));
		me = new MyLocationOverlay(this, map);
		map.getOverlays().add(me);

		centerOnUser();
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				1000, 10, this);
		me.enableCompass();
	}

	@Override
	public void onPause() {
		super.onPause();

		locationManager.removeUpdates(this);
		me.disableCompass();
	}

	@Override
	public void onLocationChanged(Location location) {
		Log.d(TAG, "onLocationChanged with location " + location.toString());

		map.getController().animateTo(
				getPoint(location.getLatitude(), location.getLongitude()));
	}

	public StoreInfo getItem(int i) {
		return storeList.get(i);
	}

	@Override
	public void onProviderDisabled(String provider) {
	}

	@Override
	public void onProviderEnabled(String provider) {
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
	}
}
