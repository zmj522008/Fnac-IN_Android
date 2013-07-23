package com.fnacin.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.fnacin.Activities.R;
import com.fnacin.pojo.CommentInfo;

public class CommentViewList{

	private List<CommentInfo> commentList;
	private Context context;
	
	
	public CommentViewList(Context context, 
			List<CommentInfo> objects) {
	this.context = context;
	commentList = objects;
	}


	public List<View> getViews() {
		List<View> list = new ArrayList<View>();
		if (commentList != null) {
			for(CommentInfo info : commentList) {

				LayoutInflater inflater = LayoutInflater.from(context);
				View v = inflater.inflate(R.layout.item_comment, null);

				TextView userName = (TextView) v.findViewById(R.id.userName);
				TextView commentDate = (TextView) v.findViewById(R.id.commentDate);
				TextView commentContent = (TextView) v.findViewById(R.id.commentContent);
				userName.setText(Html.fromHtml(info.getName()));
				commentDate.setText(Html.fromHtml(info.getDate()));
				commentContent.setText(Html.fromHtml(info.getContent()));
				list.add(v);
			}
		}
		return list;
			
	}

	public void setList(List<CommentInfo> newList) {
		commentList = newList;
	}

}
