package com.fnacin.Activities;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;
import com.fnacin.service.RefindPasswordService;
import com.fnacin.Activities.R;
public class RefindPW extends Activity {
	private EditText editEmail = null;
	private TextView confirm_btn = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.email);
		editEmail = (EditText) this.findViewById(R.id.EditTextEmail);
		confirm_btn = (TextView) this.findViewById(R.id.Submit);
		confirm_btn.setOnClickListener(new confirmOnClickListener());
	}
	// confirm
	class confirmOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			final String email = editEmail.getText().toString();
			final RefindPasswordService refindPasswordService = new RefindPasswordService();
			final FnacProgressDialog dialog = new FnacProgressDialog(RefindPW.this);
			new Thread() {
				public void run() {
			final String returnValue = refindPasswordService.setEmail(email);
			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					dialog.dismiss();
					onFindPWResult(returnValue);
				}
			});
		}
	}.start();
		}

		private void onFindPWResult(String returnValue) {
			if (returnValue.equals(RefindPasswordService.SUCCESS)) {

				AlertDialog.Builder alterDialog = new AlertDialog.Builder(
						RefindPW.this);
				alterDialog.setTitle(R.string.SuccessDialogTitle);
				alterDialog.setPositiveButton(R.string.DialogOKButton,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int i) {
							}
						});
				alterDialog.setMessage(R.string.SentEmailSuccess);
				alterDialog.show();
			} else {

				AlertDialog.Builder alterDialog = new AlertDialog.Builder(
						RefindPW.this);
				alterDialog.setTitle(R.string.FailedDialogTitle);
				alterDialog.setPositiveButton(R.string.DialogOKButton,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int i) {
							}
						});
				alterDialog.setMessage(returnValue);
				alterDialog.show();
			}
		}

	}
}
