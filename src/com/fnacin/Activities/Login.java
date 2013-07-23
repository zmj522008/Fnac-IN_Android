package com.fnacin.Activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.LoginFilter;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import com.fnacin.pojo.AuthentificationInfo;
import com.fnacin.service.AuthentificationService;
import com.fnacin.Activities.R;
import com.google.android.gms.internal.di;
import com.google.android.gms.internal.ee;

public class Login extends Activity implements OnClickListener {
	private static final String PREFS = "LoginPreferences";

	private static final String PREFS_EMAIL = "Email";
	
	private View mBtnLogin = null;
	private EditText mEditPassword = null;
	private EditText mEditEmail = null;
	public Activity context;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		mBtnLogin = findViewById(R.id.Submit);
		mBtnLogin.setOnClickListener(this);

		mEditPassword = (EditText) findViewById(R.id.EditTextPassWord);
		mEditPassword.setFilters(new InputFilter[] { new LoginFilter.PasswordFilterGMail() });
		mEditPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				submit();
				return false;
			}
		});
		mEditEmail = (EditText) findViewById(R.id.EditTextEmail);
		mEditEmail.setFilters(new InputFilter[] { new LoginFilter.PasswordFilterGMail() });
		mEditEmail.setOnEditorActionListener(new TextView.OnEditorActionListener() {
			
			@Override
			public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
				mEditPassword.requestFocus();
				return false;
			}
		});

		// findpw
		TextView findPW = (TextView) this.findViewById(R.id.FindPassword);

		findPW.setOnClickListener(this);

		String defaultEmail = "";
		SharedPreferences settings = getSharedPreferences(PREFS, 0);

		mEditEmail.setText(settings.getString(PREFS_EMAIL, defaultEmail));
		if (mEditEmail.getText().length() > 0) {
			mEditPassword.requestFocus();
		} else {
			mEditEmail.requestFocus();
		}
		mEditPassword.setText("");				
	}

	@Override
	public void onClick(View v) {
		/*switch (v.getId()) {
		case R.id.Submit:
			submit();
			break;
		case R.id.FindPassword:

			Intent intent2 = new Intent();
			intent2.setClass(this, RefindPW.class);
			startActivity(intent2);

			break;
		default:
			break;
		}*/
		int id = v.getId();
		if(id==R.id.Submit){
			submit();
		}else if(id==R.id.FindPassword){
			Intent intent2 = new Intent();
			intent2.setClass(this, RefindPW.class);
			startActivity(intent2);
		}
	}

	private void submit() {
		final FnacProgressDialog dialog = new FnacProgressDialog(this);
		final AlertDialog myAlert = new AlertDialog.Builder(this).create();
		if((mEditEmail.getText().toString()==null || mEditEmail.getText().toString().equals("")) || (
				mEditPassword.getText().toString().equals("") || mEditPassword.getText().toString()==null)){
			dialog.cancel();
			myAlert.setTitle("Erreur");
			myAlert.setMessage("Saisir l'email ou le mot de passe!");
			myAlert.setButton("OK", new DialogInterface.OnClickListener() {
				
				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					dialog.cancel();
				}
			} );
			myAlert.show();
		}else{
		new Thread() {
			public void run() {
				String versionNo = "0.0";
				PackageInfo pInfo = null;
				try{
					pInfo = getPackageManager().getPackageInfo
					("com.fnacin.Activity",PackageManager.GET_META_DATA);
					versionNo = "A" + pInfo.versionName;
				} catch (android.content.pm.PackageManager.NameNotFoundException e) {
				}
			
				final boolean loginSuccess = AuthentificationService.getInstance().Login(mEditEmail.getText().toString(),
						mEditPassword.getText().toString(), versionNo);
				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						dialog.dismiss();
						if (loginSuccess) {
							SharedPreferences settings = getSharedPreferences(PREFS, 0);
							SharedPreferences.Editor editor = settings.edit();
							editor.putString(PREFS_EMAIL, mEditEmail.getText().toString());
							editor.commit();
						}
						onLoginResponse(loginSuccess);
					}
				});
			
			}
		}.start();
	}
	}

	private void onLoginResponse(final boolean loginSuccess) {
		if (!loginSuccess) {
			final AlertDialog.Builder alterDialog = new AlertDialog.Builder(this);
			alterDialog.setTitle(R.string.FailedDialogTitle);
			alterDialog.setPositiveButton(R.string.DialogOKButton,
					new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int i) {

				}
			});
			final AuthentificationInfo infos = AuthentificationService.getInstance()
			.getAuthentificationInfo();
			alterDialog.setMessage(infos != null ? infos.getErrorInfo()
					.getMessage() : getString(R.string.CanNotConnect));
			alterDialog.show();
		} else {
			AuthentificationInfo infos = AuthentificationService.getInstance()
			.getAuthentificationInfo();
			String st=infos.getParameterInfo().getPrePage();
			Intent intent = new Intent(); 
			if(st!=""){
				intent.setClass(this, PrePage.class);

			}else{
				intent.setClass(this, MainPage.class);

			}
			startActivity(intent);

			finish();
		}
	}
}