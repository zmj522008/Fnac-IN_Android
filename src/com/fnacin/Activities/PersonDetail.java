package com.fnacin.Activities;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentProviderOperation;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fnacin.pojo.PersonInfo;
import com.fnacin.Activities.R;

public class PersonDetail extends Activity {
	private static String TAG = "fnac";
	TextView tv_lastname;
	TextView tv_firstname;
	TextView tv_civilite;
	private LinearLayout contentLayout;	
	private PersonInfo personInfo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.person_detail);
		Log.d(TAG, "PersonDetail onCreate");
		personInfo = (PersonInfo) getIntent().getSerializableExtra(
				PersonInfo.SER_KEY);
		Log.d(TAG, "Lastname=" + personInfo.getLastname());
		
		contentLayout = (LinearLayout) this.findViewById(R.id.tv_content);
		tv_lastname = (TextView) this.findViewById(R.id.tv_lastname);
		tv_firstname = (TextView) this.findViewById(R.id.tv_firstname);
		tv_civilite = (TextView) this.findViewById(R.id.tv_civilite);

		loadPersonData();

		((Button) this.findViewById(R.id.btnAddToContact)).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addToContact();
			}
		});
	}

	public View addItem(String label, String detail) 
	{
		if (detail != null && detail.length() > 0) {
			LayoutInflater inflater = LayoutInflater.from(this);
			View v = inflater.inflate(R.layout.item_person_detail, null);

			TextView v_label = (TextView) v.findViewById(R.id.label);
			TextView v_detail = (TextView) v.findViewById(R.id.detail);
			
			if(String.valueOf(label)=="Fixe"||String.valueOf(label)=="Mobile"||
			    String.valueOf(label)=="Fax"||String.valueOf(label)=="Interne"||String.valueOf(label)=="Email"){
				SpannableString detailString = new SpannableString(detail);
				detailString.setSpan(new UnderlineSpan(), 0, detailString.length(), 0);
				v_detail.setText(detailString);
				v_detail.setTextColor(Color.rgb(00, 150, 255));
				v_label.setText(label);
			}else{
			v_label.setText(label);
			v_detail.setText(detail);}
			
			contentLayout.addView(v);
			return v;
		} else {
			return null;
		}
	}
	
	public void addPhone(final String label, final String detail) {
		View v = addItem(label, detail);
		
		if (v != null) {
			v.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				    try {
				    	//AlertDialog.Builder build = new AlertDialog.Builder(PersonDetail.this);
				    	AlertDialog.Builder build = new AlertDialog.Builder(PersonDetail.this.getParent());

				    	TextView title = new TextView(PersonDetail.this);
				    	title.setText(detail);
				    	title.setGravity(Gravity.CENTER_HORIZONTAL);
				    	title.setTextColor(Color.rgb(0, 150, 255));
				    	title.setTextSize(20);
						build.setCustomTitle(title);
						build.setCancelable(false);
						build.setPositiveButton("Appeler", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Intent callIntent = new Intent(Intent.ACTION_CALL);
						        callIntent.setData(Uri.parse("tel:" + Person.phone(detail)));
						        startActivity(callIntent);
							}
						});
				        build.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
							}
						});
				        AlertDialog myAlert = build.create();
				        myAlert.show();
				    } catch (ActivityNotFoundException e) {
				        Log.e("helloandroid dialing example", "Call failed", e);
				    }
				}
			});
		}
	}

	public void addEmail(final String label, final String detail) {
		View v = addItem(label, detail);
		if (v != null) {
			v.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
				    try {
				    	//AlertDialog.Builder build = new AlertDialog.Builder(PersonDetail.this);
				    	AlertDialog.Builder build = new AlertDialog.Builder(PersonDetail.this.getParent());

				    	TextView titleView = new TextView(PersonDetail.this);
				    	titleView.setText(detail);
				    	titleView.setGravity(Gravity.CENTER_HORIZONTAL);
				    	titleView.setTextColor(Color.rgb(0, 150, 255));
				    	titleView.setTextSize(20);
						build.setCustomTitle(titleView);
						build.setCancelable(false);
						build.setPositiveButton("Envoyer", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								Intent emailIntent = new Intent(Intent.ACTION_SEND);
						        emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[] { detail });
						        emailIntent.setType("message/rfc822");
						        startActivity(emailIntent);
							}
						});
				        build.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
								// TODO Auto-generated method stub
								dialog.cancel();
							}
						});
				        build.create().show();
				    } catch (ActivityNotFoundException e) {
				        Log.e("helloandroid dialing example", "Call failed", e);
				    }
				}
			});
		}
	}
	
	public void loadPersonData() {
		// add value
		tv_lastname.setText(personInfo.getLastname());
		Log.d(TAG, "Lastname=" + personInfo.getLastname());
		tv_firstname.setText(personInfo.getFirstname());
		tv_civilite.setText(personInfo.getCivilite());

		contentLayout.removeAllViews();
		addItem("Fonction", personInfo.getFonction());
		addPhone("Fixe", personInfo.getTel_fixe());
		addPhone("Mobile", personInfo.getTel_mobile());
		addPhone("Interne", personInfo.getTel_interne());
		addPhone("Fax", personInfo.getFax());
		addEmail("Email", personInfo.getEmail());
		
		if (personInfo.getAdresse() != null && personInfo.getCp() != null && personInfo.getVille() != null
				&& (personInfo.getAdresse().length() > 0 || personInfo.getCp().length() > 0)) {
			addItem("Adresse", personInfo.getAdresse() + "\n" + personInfo.getCp() + " " + personInfo.getVille());
		}
		
		addItem("Site", personInfo.getSite());
		addItem("Notes", personInfo.getCommentaire());
	}

	public void addToContact() {

		ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.RawContacts.CONTENT_URI)
				.withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
				.withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
				.build());

		setValue(ops,
				ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
				personInfo.getFirstname());
		setValue(ops,
				ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME,
				personInfo.getLastname());
		setValue(ops,
				ContactsContract.CommonDataKinds.StructuredName.PREFIX,
				personInfo.getCivilite());

		setPhone(ops, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE, personInfo.getTel_mobile());
		setPhone(ops, ContactsContract.CommonDataKinds.Phone.TYPE_WORK, personInfo.getTel_fixe());
		setPhone(ops, ContactsContract.CommonDataKinds.Phone.TYPE_WORK_PAGER, personInfo.getTel_interne());
		setPhone(ops, ContactsContract.CommonDataKinds.Phone.TYPE_FAX_WORK, personInfo.getFax());
		

		// ------------------------------------------------------ Email
		if (personInfo.getEmail() != null) {
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(
							ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Email.DATA,
							 personInfo.getEmail())
					.withValue(ContactsContract.CommonDataKinds.Email.TYPE,
							ContactsContract.CommonDataKinds.Email.TYPE_WORK)
					.build());
		}

		String company = personInfo.getSite();
		String jobTitle = personInfo.getFonction();
		if (company == null) {
			company = "";
		}
		if (jobTitle == null) {
			jobTitle = "";
		}
		ops.add(ContentProviderOperation
				.newInsert(ContactsContract.Data.CONTENT_URI)
				.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
				.withValue(
						ContactsContract.Data.MIMETYPE,
						ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE)
				.withValue(
						ContactsContract.CommonDataKinds.Organization.COMPANY,
						company)
				.withValue(ContactsContract.CommonDataKinds.Organization.TYPE,
						ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
				.withValue(ContactsContract.CommonDataKinds.Organization.TITLE,
						jobTitle)
				.withValue(ContactsContract.CommonDataKinds.Organization.TYPE,
						ContactsContract.CommonDataKinds.Organization.TYPE_WORK)
				.build());

		// Asking the Contact provider to create a new contact
		String message;
		try {
			getContentResolver().applyBatch(ContactsContract.AUTHORITY, ops);
			message = "Contact ajouté";
		} catch (Exception e) {
			e.printStackTrace();
			message = "Erreur dans l'ajout du message";
		}
		//AlertDialog.Builder alterDialog = new AlertDialog.Builder(this);
		AlertDialog.Builder alterDialog = new AlertDialog.Builder(this.getParent());

		alterDialog.setTitle("Contact");
		alterDialog.setPositiveButton(R.string.DialogOKButton,
				new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog,
					int i) {
			}
		});
		alterDialog.setMessage(message);
		alterDialog.show();
	}

	private void setPhone(ArrayList<ContentProviderOperation> ops, int type,
			String value) {
		if (value != null) {
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(
							ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(
							ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
					.withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
							value)
					.withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
							type).build());
		}
	}

	private void setValue(ArrayList<ContentProviderOperation> ops, String key,
			String value) {
		if (value != null) {
			ops.add(ContentProviderOperation
					.newInsert(ContactsContract.Data.CONTENT_URI)
					.withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
					.withValue(
							ContactsContract.Data.MIMETYPE,
							ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
							.withValue(key, value).build());
		}
	}
}
