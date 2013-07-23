package com.fnacin.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import com.fnacin.Activities.R;

public class FnacProgressDialog extends ProgressDialog {

	public FnacProgressDialog(Context context) {
		super(context);
		setProgressStyle(ProgressDialog.STYLE_SPINNER);
		setMessage(context.getString(R.string.Progress));
		setCancelable(true);
		this.setIndeterminate(true);
		show();
	}

}
