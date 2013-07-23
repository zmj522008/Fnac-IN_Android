package com.fnacin.Activities;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import com.fnacin.Activities.R;

public class Others extends ListActivity {
	SimpleAdapter adapter;
	ListView lv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.others);
		String[] others_name = {"Site Institutionnel Groupe Fnac", "Autres applis Fnac",
				"Changement de mot de passe" };
		this.setListAdapter(getMenuAdapter(others_name));
		lv = this.getListView();

		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				OthersGroup parent = (OthersGroup) getParent();
				switch (arg2) {
				case 0:{
					Intent fnacIntent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://www.groupe-fnac.com/"));
					fnacIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

					startActivity(fnacIntent);
				}
				case 1: {
					Intent otherAppliIntent = new Intent(Others.this, OtherApplis.class);
					parent.startChildActivity("OtherApplis", otherAppliIntent);

					//startActivity(intent);
					break;
				}
				case 2: {
					Intent resetPWIntent = new Intent(Others.this, ResetPW.class);
					parent.startChildActivity("ResetPW", resetPWIntent);

					//startActivity(intent);
					break;
				}
				}
			}

		});

	}

	private SimpleAdapter getMenuAdapter(String[] others_name) {
		ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
		for (int i = 0; i < others_name.length; i++) {
			HashMap<String, Object> map = new HashMap<String, Object>();
			map.put("other_name", others_name[i]);
			data.add(map);
		}
		SimpleAdapter simperAdapter = new SimpleAdapter(this, data,
				R.layout.item_others, new String[] { "other_name" },
				new int[] { R.id.other_name });
		return simperAdapter;
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		new ExitDialog(this.getParent()).show();
	}

}
