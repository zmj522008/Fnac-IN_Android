package com.fnacin.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fnacin.Activities.ArticleByCat;
import com.fnacin.Activities.R;
import com.fnacin.pojo.CategoryInfo;

public class CategoryViewAdapter extends ArrayAdapter<CategoryInfo> {
	// Log tag
	private List<CategoryInfo> categoryInfoList;

	public CategoryViewAdapter(Context context, List<CategoryInfo> list) {
		super(context, 0, list);
		categoryInfoList  = list;
	}

	private Map<Integer, View> viewMap = new HashMap<Integer, View>();
	
	
	@Override
	public int getCount() {
		return categoryInfoList.size();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// Log.d(TAG, "CategoryViewAdapter getView:"+position);
		//View rowView = this.viewMap.get(position);
		View rowView = null;
		if (rowView == null) {
			LayoutInflater inflater = LayoutInflater.from(getContext());
			rowView = inflater.inflate(R.layout.item_category, null);
			CategoryInfo categoryInfo = this.getItem(position);

			TextView nameView = (TextView) rowView
					.findViewById(R.id.category_name);
			nameView.setText(categoryInfo.getCategory());
			nameView.setOnClickListener(new DetailOnClickListener(this
					.getContext(), categoryInfo));

			LinearLayout ll_category = (LinearLayout) rowView
					.findViewById(R.id.ll_category);
			ll_category.setOnClickListener(new DetailOnClickListener(this
					.getContext(), categoryInfo));

			TextView detailView = (TextView) rowView
					.findViewById(R.id.cate_Detail_btn);
			detailView.setOnClickListener(new DetailOnClickListener(this
					.getContext(), categoryInfo));
			viewMap.put(position, rowView);
		} 
		return rowView;
	}
	

	public class DetailOnClickListener implements OnClickListener {
		protected final CategoryInfo categoryInfo;
		protected final Context context;

		public DetailOnClickListener(Context context, CategoryInfo categoryInfo) {
			this.categoryInfo = categoryInfo;
			this.context = context;
		}

		@Override
		public void onClick(View arg0) {
			Intent i = new Intent(context, ArticleByCat.class);
			i.putExtra(ArticleByCat.BUNDLE_CATEGORIE_ID, categoryInfo.getCategoryId());
			i.putExtra(ArticleByCat.BUNDLE_CATEGORIE_NAME, categoryInfo.getCategory());
			context.startActivity(i);
		}
	}
}
