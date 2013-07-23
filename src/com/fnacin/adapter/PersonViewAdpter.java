package com.fnacin.adapter;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.fnacin.Activities.R;
import com.fnacin.database.DBHelper;
import com.fnacin.indexScroll.StringMatcher;
import com.fnacin.pojo.PersonInfo;

public class PersonViewAdpter extends BaseAdapter implements SectionIndexer {
	// Log Tag
	private static String TAG = "fnac";
	private List<PersonInfo> personList;
	private Activity mActivity;

	private String message;

	private boolean telephoneMode;

	private DBHelper helper;
	private String mSections = "#ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public void setMessage(String message) {
		this.message = message;
	}

	public void setHelper(DBHelper helper) {
		this.helper = helper;
	}

	public void setTelephoneMode(boolean mode) {
		telephoneMode = mode;
	}

	public void setList(List<PersonInfo> list) {
		personList = list;
	}

	@Override
	public int getCount() {
System.out.println("Count:"+(personList != null ? personList.size() : 0)
		+ (this.message != null ? 1 : 0));
		return (personList != null ? personList.size() : 0)
				+ (this.message != null ? 1 : 0);
	}

	@Override
	public Object getItem(int position) {
		if (message != null) {
			if (position == 0) {
				return message;
			} else {
				return personList.get(position - 1);
			}
		} else {
			return personList.get(position);
		}
	}

	/*
	 * public Object getItem(int position) {
	 * 
	 * return position; }
	 */
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
		PersonInfo p = personList.get(position);
		try {
			return Long.valueOf(p.getId());
		} catch (NumberFormatException e) {
			return p.hashCode();
		}
	}

	class ViewHolder {
		TextView lastname;
		TextView firstname;
		TextView telephone;
		TextView message;
	}

	public PersonViewAdpter(Activity activity) {
		super();
		mActivity = activity;
		personList = new ArrayList<PersonInfo>();
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public View getView(final int row, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		
		if (convertView == null) {
			holder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(mActivity);

			convertView = inflater.inflate(R.layout.item_person, parent, false);
			holder.firstname = (TextView) convertView
					.findViewById(R.id.firstname);
			holder.lastname = (TextView) convertView
					.findViewById(R.id.lastname);
			holder.telephone = (TextView) convertView
					.findViewById(R.id.telephone);
			holder.message = (TextView) convertView.findViewById(R.id.message);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		int position = row;
		if (message != null) {
			if (row == 0) {
				holder.firstname.setText(null);
				holder.lastname.setText(null);
				holder.telephone.setText(null);
				holder.message.setText(message);
				convertView.setBackgroundColor(0xffdddddd);
				return convertView;
			} else {
				--position;
			}
		}
		holder.message.setText(null);
		convertView.setBackgroundColor(0xffffffff);

		if (position > personList.size()) {
			Log.v(TAG, "position: " + position
					+ " bigger than personList.size: " + personList.size());
			return convertView;
		}
		final PersonInfo personInfo = personList.get(position);
		if (personInfo == null) {
			Log.v(TAG, "personInfo nulL");
			return convertView;
		}
		
		holder.lastname.setText(personInfo.getLastname());
		holder.firstname.setText(personInfo.getFirstname());
		if (telephoneMode) {
			String tel = personInfo.getTel_fixe();
			holder.telephone.setText(tel);
		} else {
			holder.telephone.setText("");
		}
		
		convertView.setClickable(false);
		convertView.setFocusable(false);
		convertView.setFocusableInTouchMode(false);
		return convertView;
	}


	@Override
	public int getPositionForSection(int section) throws IndexOutOfBoundsException, EmptyStackException{
		// TODO Auto-generated method stub
		for (int i = section; i >= 0; i--) {
			for (int j = 0; j < getCount(); j++) {
				if (i == 0) {
					// For numeric section
					for (int k = 0; k <= 9; k++) {				
						if(j<personList.size()){
						if (StringMatcher.match(
								String.valueOf(personList.get(j).getLastname()
										.charAt(0)), String.valueOf(k))){
							return j + 1;}
						}
					}
				} else {
				if(j<personList.size()){
					if (StringMatcher.match(
							String.valueOf(personList.get(j).getLastname()
									.charAt(0)),
							String.valueOf(mSections.charAt(i)))){
						return j + 1;}
				}
				
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
