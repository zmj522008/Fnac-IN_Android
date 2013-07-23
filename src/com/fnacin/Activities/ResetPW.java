package com.fnacin.Activities;

import com.fnacin.service.ResetPasswordService;
import com.fnacin.Activities.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.LoginFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class ResetPW extends Activity {
	private EditText et_newpw;
	private EditText et_confirm_pw;
	private String newpw;
	private String confirm_pw;
	private Button confirm_btn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.reset_password);
		et_newpw = (EditText) this.findViewById(R.id.EditTextPassword);
		et_newpw.setFilters(new InputFilter[] { new LoginFilter.PasswordFilterGMail() });
		et_newpw.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				et_confirm_pw.requestFocus();
				return false;
			}
		});

		et_confirm_pw = (EditText) this.findViewById(R.id.ConfirmPassword);
		et_confirm_pw.setFilters(new InputFilter[] { new LoginFilter.PasswordFilterGMail() });
		et_confirm_pw.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				submit();
				return false;
			}
		});

		confirm_btn = (Button) this.findViewById(R.id.confirm_btn);
		confirm_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				submit();
			}
		}
		);

	}
	@Override
	public void onBackPressed() {
	// TODO Auto-generated method stub
	//super.onBackPressed();
	OthersGroup parent = (OthersGroup) getParent();
	parent.onBackPressed();
		}
	
	private void onSetPWResult(String returnValue) {
		if (returnValue.equals(ResetPasswordService.SUCCESS)) {

			//AlertDialog.Builder alterDialog = new AlertDialog.Builder(ResetPW.this);
			AlertDialog.Builder alterDialog = new AlertDialog.Builder(ResetPW.this.getParent());

			alterDialog.setTitle(R.string.SuccessDialogTitle);
			alterDialog.setPositiveButton(R.string.DialogOKButton,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int i) {
						}
					});
			alterDialog.setMessage(R.string.PasswordSetSuccess);
			alterDialog.show();
		}else if(returnValue.equals("E")){
			Toast.makeText(getApplicationContext(), "Erreur de communication avec le serveur", Toast.LENGTH_LONG).show();
		} 
		else {

			//AlertDialog.Builder alterDialog = new AlertDialog.Builder(ResetPW.this);
			AlertDialog.Builder alterDialog = new AlertDialog.Builder(ResetPW.this.getParent());

			alterDialog.setTitle(R.string.FailedDialogTitle);
			alterDialog.setPositiveButton(R.string.DialogOKButton,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int i) {
						}
					});
			//alterDialog.setMessage("Erreu de communication avec le serveur");

			alterDialog.setMessage(returnValue);
			alterDialog.show();
		}

	}

	private void submit() {
		newpw = et_newpw.getText().toString();
		confirm_pw = et_confirm_pw.getText().toString();
		if(newpw==null || newpw.equals("") ||confirm_pw==null || confirm_pw.equals("")){
			AlertDialog alert = new AlertDialog.Builder(ResetPW.this.getParent()).create();
			alert.setTitle("Erreur");
			alert.setMessage("Veuillez saisir un mot de passe!");
			alert.setCancelable(true);
			alert.setButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			});
			alert.show();
			return;
		}
		if (newpw.equals(confirm_pw)) {

			final String newpassword = newpw;
			final ResetPasswordService resetPasswordService = new ResetPasswordService();
			new Thread() {
				public void run() {
					final String returnValue = resetPasswordService
							.setPassword(newpassword);
					runOnUiThread(new Runnable() {
						@Override
						public void run() {
							onSetPWResult(returnValue);

						}
					});
				}
			}.start();

		} else if (newpw != confirm_pw) {
			//AlertDialog.Builder alterDialog = new AlertDialog.Builder(ResetPW.this);
			AlertDialog.Builder alterDialog = new AlertDialog.Builder(ResetPW.this.getParent());

			alterDialog.setTitle(R.string.FailedDialogTitle);
			alterDialog.setPositiveButton(R.string.DialogOKButton,
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog,
								int i) {
						}
					});
			alterDialog.setMessage(R.string.PasswordNotSame);
			alterDialog.show();

		}
	}

}
