package com.fnacin.adapter;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.fnacin.Activities.R;
import com.fnacin.Activities.StoreMapDetail;
import com.fnacin.indexScroll.StringMatcher;
import com.fnacin.pojo.PersonInfo;
import com.fnacin.pojo.StoreInfo;

public class StoreViewAdapter extends BaseAdapter implements SectionIndexer{
	// Log Tag
	private static String TAG = "fnac";
	private List<StoreInfo> storeList;
	private Activity mActivity;
	 Button map_button;
	private String message;
	private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return storeList.size();
	}

	@Override
	public Object getItem(int position) {
		if (message != null) {
			if (position == 0) {
				return message;
			} else {
				return storeList.get(position-1);
			}
		} else {
			return storeList.get(position);
		}
		// TODO Auto-generated method stub
	}

	@Override
	public long getItemId(int row) {
		int position = row;
		if (message != null) {
			if (row == 0) {
				return 0;
			} else {
				--position;
			}
		}
		StoreInfo p = storeList.get(position);
		try {
			return Long.valueOf(p.getId());
		} catch (NumberFormatException e) {
			return p.hashCode(); 
		}
		// TODO Auto-generated method stub
	}

	class ViewHolder {
		TextView storename;
		
	}
	public StoreViewAdapter(Activity activity, List<StoreInfo> list) {
		super();
		mActivity = activity;
		storeList = new ArrayList<StoreInfo>();
		if (list != null)
			storeList = list;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		final ViewHolder holder;
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(mActivity);
			convertView = inflater.inflate(R.layout.item_store, null);
			holder.storename = (TextView) convertView.findViewById(R.id.store_name);
			convertView.setTag(holder);
		} else {
			holder =  (ViewHolder) convertView.getTag();
		}
		final StoreInfo storeInfo = storeList.get(position);
		Log.d(TAG, " getView storeList size=="+storeList.size());
		holder.storename.setText(Html.fromHtml(storeInfo.getNom()));
		Log.d(TAG, "storename " +storeInfo.getNom());
		
		//convertView.setOnClickListener(new DetailButtonOnClickListener(mActivity,storeInfo));
		return convertView;
	}

	
	/*class DetailButtonOnClickListener implements OnClickListener {
		private StoreInfo storeInfo;

		public DetailButtonOnClickListener(Context context,
				StoreInfo storeInfo) {
			this.storeInfo = storeInfo;
		}

		@Override
		public void onClick(View v) {
			Log.v("item", "clicked " + v.toString());
			// Get current Activity
			Bundle mBundle = new Bundle();
			mBundle.putSerializable(StoreInfo.SER_KEY, storeInfo);
			Intent intent = new Intent();
			intent.putExtras(mBundle);
			//intent.setClass(v.getContext(), StoreMap.class);
			intent.setClass(v.getContext(), StoreMapDetail.class);
			v.getContext().startActivity(intent);
		}
	}*/
	@Override
	public int getPositionForSection(int section) {
		// TODO Auto-generated method stub
		for (int i = section; i >= 0; i--) {
			for (int j = 0; j < getCount(); j++) {
				if (i == 0) {
					// For numeric section
					for (int k = 0; k <= 9; k++) {
						//if (StringMatcher.match(String.valueOf(((String) getItem(j)).charAt(0)), String.valueOf(k)))
						if(StringMatcher.match(String.valueOf(storeList.get(j).getNom().charAt(0)), String.valueOf(k)))	
						return j+1;
					}
				} else {
					//if (StringMatcher.match(String.valueOf(((String) getItem(j)).charAt(0)), String.valueOf(mSections.charAt(i))))
						if(StringMatcher.match(String.valueOf(storeList.get(j).getNom().charAt(0)), String.valueOf(mSections.charAt(i))))
						return j+1;
				}
			}
		}
		return 0;
	}

	@Override
	public int getSectionForPosition(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] getSections() {
		// TODO Auto-generated method stub
		String[] sections = new String[mSections.length()];
		for (int i = 0; i < mSections.length(); i++)
			sections[i] = String.valueOf(mSections.charAt(i));
		return sections;
	}
	
}
