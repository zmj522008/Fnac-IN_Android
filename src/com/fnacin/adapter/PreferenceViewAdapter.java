  package com.fnacin.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fnacin.Activities.R;
import com.fnacin.pojo.CategoryInfo;

public class PreferenceViewAdapter extends ArrayAdapter<CategoryInfo> {

	public PreferenceViewAdapter(Context context, List<CategoryInfo> objects) {
		super(context, 0, objects);
		// TODO Auto-generated constructor stub
	}

	private Map<Integer, View> viewMap = new HashMap<Integer, View>();

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Log.d(TAG, "CategoryViewAdapter getView:"+position);
		View rowView = this.viewMap.get(position);
		if (rowView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());

			rowView = inflater.inflate(R.layout.item_preference, null);
			CategoryInfo categoryInfo = this.getItem(position);

			TextView nameView = (TextView) rowView
					.findViewById(R.id.category_name);
			nameView.setText(categoryInfo.getCategory());
			CheckBox checkboxbtn = (CheckBox) rowView
					.findViewById(R.id.checkbox_btn);

			checkboxbtn.setOnClickListener(new CheckBoxListener(position,
					categoryInfo, checkboxbtn));
			rowView.setOnClickListener(new CheckBoxListener(position,
					categoryInfo, checkboxbtn));
			if (categoryInfo.getPreferenceFlag().equals("1")) {
				checkboxbtn.setButtonDrawable(R.drawable.checkbox_chosen);
			} else {
				checkboxbtn.setButtonDrawable(R.drawable.checkbox_btn);
			}
			// checkboxbtn.setBackgroundColor(0xffff0000);
			//
		}
		return rowView;
	}

	public class CheckBoxListener implements View.OnClickListener {
		CategoryInfo categoryInfo;
		int position;
		CheckBox checkboxbtn;

		public CheckBoxListener(int position, CategoryInfo categoryInfo,
				CheckBox checkboxbtn) {
			this.position = position;
			this.categoryInfo = categoryInfo;
			this.checkboxbtn = checkboxbtn;
		}

		@Override
		public void onClick(View v) {
			if (categoryInfo.getPreferenceFlag().equals("1")) {
				categoryInfo.setPreferenceFlag("0");
				checkboxbtn.setButtonDrawable(R.drawable.checkbox_btn);
			} else {
				categoryInfo.setPreferenceFlag("1");
				checkboxbtn.setButtonDrawable(R.drawable.checkbox_chosen);
			}
			Log.i("v-----------", categoryInfo.getCategory() + ""
					+ categoryInfo.getPreferenceFlag());

		}

	}

}
