package com.fnacin.Activities;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.fnacin.Activities.R;

public class OtherApplis extends ListActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.other_applis);
		setListAdapter(new MyListAdapter(this));
	}

	private class MyListAdapter extends BaseAdapter {
		String[] appliNameList = new String[] { "Appli mobile Fnac.com",
				"Adhérent Club Fnac", "Tick&Live", "MarketPlace", "LaboFnac",
				"Kobo by Fnac" };
		Integer[] appliImgList = new Integer[] { R.drawable.icone_mobile_fnac,
				R.drawable.icone_adherent_club_fnac,
				R.drawable.icone_ticket_live, R.drawable.icone_market_place,
				R.drawable.icone_labo_fnac, R.drawable.icone_kobo_fnac };
		private final Context context;

		public MyListAdapter(Context context) {
			this.context = context;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return appliNameList.length;
		}

		@Override
		public Object getItem(int arg0) {
			// TODO Auto-generated method stub
			return appliNameList[arg0];
		}

		@Override
		public long getItemId(int arg0) {
			// TODO Auto-generated method stub
			return arg0;
		}

		@Override
		public View getView(int pos, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			// LayoutInflater layoutInflater =
			// LayoutInflater.from(convertView.getContext());
			convertView = inflater.inflate(R.layout.other_appli_detail, null);
			ImageView myImage = (ImageView) convertView
					.findViewById(R.id.other_appli_img);
			TextView myText = (TextView) convertView
					.findViewById(R.id.other_appli_name);
			myImage.setImageResource(appliImgList[pos]);
			myText.setText(appliNameList[pos]);
			return convertView;
		}

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		
		
		switch (position) {
		case 0:
			Intent browserIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=fr.fnac.com"));
			browserIntent.setClassName("com.android.vending",
					"com.android.vending.AssetBrowserActivity");
			browserIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(browserIntent);
			break;
		case 1:
			Intent adherentIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.fnac.adherent"));
			adherentIntent.setClassName("com.android.vending",
					"com.android.vending.AssetBrowserActivity");
			adherentIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(adherentIntent);
			break;
		case 2:
			/*Intent tickIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=fr.tm.fnac"));
			tickIntent.setClassName("com.android.vending",
					"com.android.vending.AssetBrowserActivity");
			tickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(tickIntent);
			break;*/
			Intent tickIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps"));
			tickIntent.setClassName("com.android.vending", "com.android.vending.AssetBrowserActivity");
			tickIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(tickIntent);
			break;
		case 3:
			/*Intent marketIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=psportugal.trop.precosnafnac.pt"));
			marketIntent.setClassName("com.android.vending",
					"com.android.vending.AssetBrowserActivity");
			marketIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(marketIntent);
			break;*/
			Intent marketIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps"));
			marketIntent.setClassName("com.android.vending", "com.android.vending.AssetBrowserActivity");
			marketIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(marketIntent);
			break;
		case 4:
			/*
			Intent laboIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=fr.labo.fnac"));
			laboIntent.setClassName("com.android.vending",
					"com.android.vending.AssetBrowserActivity");
			laboIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(laboIntent);
			break;*/
			Intent laboIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps"));
			laboIntent.setClassName("com.android.vending", "com.android.vending.AssetBrowserActivity");
			laboIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(laboIntent);
		case 5:
			/*
			Intent koboIntent = new Intent(
					Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps/details?id=com.kobobooks.android"));
			koboIntent.setClassName("com.android.vending",
					"com.android.vending.AssetBrowserActivity");
			koboIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(koboIntent);
			break;*/
			Intent koboIntent = new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://play.google.com/store/apps"));
			koboIntent.setClassName("com.android.vending", "com.android.vending.AssetBrowserActivity");
			koboIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(koboIntent);

		default:
			break;

		}
		
	}
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		//super.onBackPressed();
System.out.println("OthersApp----onbackPressed----");
	OthersGroup parent =(OthersGroup) getParent();
	parent.onBackPressed();
	}

}
