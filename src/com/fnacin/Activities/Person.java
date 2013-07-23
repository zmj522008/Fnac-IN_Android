package com.fnacin.Activities;

import java.text.Collator;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.fnacin.adapter.PersonViewAdpter;
import com.fnacin.database.DBHelper;
import com.fnacin.indexScroll.IndexableListView;
import com.fnacin.interfaces.GoogleAnalytics;
import com.fnacin.pojo.PersonInfo;
import com.fnacin.service.PersonService;
import com.fnacin.Activities.R;

public class Person extends Activity {
	// Log Tag
	private static final String TAG = "fnac";
	List<PersonInfo> personList;
	LinearLayout searchLinearout;
	EditText et_search;
	ListView lv;
	PersonViewAdpter adapter;
	ArrayList<PersonInfo> searchResults;
	String searchTerm;
	private DBUpdater dbUpdater;
	SearchThread currentSearch;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		GoogleAnalytics.trackPage("/INTRAFNAC/ANNUAIRE", this);
		setContentView(R.layout.tab_person);
		lv=(IndexableListView) this.findViewById(R.id.person_list);
		et_search=(EditText) this.findViewById(R.id.et_search);
		et_search.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				loadSearchLinearout();
			}

		});
		final PersonService personService = new PersonService();
		final DBHelper helper = new DBHelper(this);
		// open database
		helper.openDatabase();

		adapter = new PersonViewAdpter(this);
		lv.setAdapter(adapter);
		lv.setFastScrollEnabled(true);
		lv.setTextFilterEnabled(true);
		adapter.setHelper(helper);
		dbUpdater = new DBUpdater(helper, personService);
		dbUpdater.start();
		loadSearchLinearout();
		lv.setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View v, int position,
					long arg3) {
				// TODO Auto-generated method stub
				if(position ==0){
					System.out.println("Position-----:"+position);
				}else{

				Bundle mBundle = new Bundle();
				PersonInfo personInfo = helper.getUser(Long.toString(arg3));
				mBundle.putSerializable(PersonInfo.SER_KEY, personInfo);
				Intent intent = new Intent();
				intent.putExtras(mBundle);
				intent.setClass(lv.getContext(), PersonDetail.class);
				PersonGroup parent = (PersonGroup)getParent();
				parent.startChildActivity("PersonDetail", intent);
				//lv.getContext().startActivity(intent);
				}
			}
		});
	}
	

	
	private void loadSearchLinearout() {

		if(searchLinearout == null) {
			searchLinearout = (LinearLayout) findViewById(R.id.ll_search);
			et_search = (EditText)findViewById(R.id.et_search);
			et_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				
				@Override
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					return true;
				}
			});
			
			et_search.addTextChangedListener(new TextWatcher() {
				
				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
				}
				
				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
						int after) {
				}
				
				@Override
				public void afterTextChanged(Editable s) {
					long startDate = new Date().getTime();
					final String searchTerm = et_search.getText().toString();
					if (searchTerm.length() == 0) {
						adapter.setList(personList);
						adapter.setTelephoneMode(false);
						showMessage(null);
						return;
					}
					final List<PersonInfo> source;
					if (Person.this.searchTerm != null && searchTerm.startsWith(Person.this.searchTerm)) {
						source = Person.this.searchResults;
					} else {
						source = personList;
					}
					
					lv.scrollTo(0, 0);
					
					if (currentSearch != null) {
						currentSearch.interrupted = true;
					}
					SearchThread newSearch = new SearchThread(source, searchTerm);
					newSearch.start();
					currentSearch = newSearch;
					
					// TODO
					// Search in phones and all
					Log.v(TAG, "search " + searchTerm + " in " + (new Date().getTime() - startDate) + "ms");
					return;
				}
			});
		}
	}

	public void showMessage(String message) {
		adapter.setMessage(message);
		adapter.notifyDataSetChanged();
	}
	
	public void showMessageOnUiThread(final String message) {
		runOnUiThread(new Runnable() {
			@Override
			public void run() {
				showMessage(message);
			}
		});
	}
	
	private void notifyDatasetChangedOnUiThread() {
		runOnUiThread(new Runnable() {
			
			@Override
			public void run() {							
				adapter.notifyDataSetChanged();
			}
		});
	}


	public static String phone(String str) {
		StringBuffer r = new StringBuffer();
		
		addPhone(str, r);
		return r.toString();
	}

	private static void addPhone(String str, StringBuffer r) {
		final int l = str.length();
		for (int i = 0; i < l; i++) {
			final char c = str.charAt(i);
			if (c >= '0' && c <= '9') {
				r.append(c);
			}
		}
	}
	
	private final class SearchThread extends Thread {
		private final List<PersonInfo> source;
		private final String searchTerm;
		private final ArrayList<PersonInfo> results;
		boolean interrupted;

		private SearchThread(List<PersonInfo> source, String searchTerm) {
			this.source = source;
			this.searchTerm = searchTerm;
			this.results = new ArrayList<PersonInfo>();
			interrupted = false;
		}

		public void run() {
			boolean telephone = searchTerm.matches("^[0-9 \\(\\)\\+]+$");
			adapter.setTelephoneMode(telephone);
			showMessageOnUiThread("Recherche en cours");
			if (telephone) {
				searchByTelephone(phone(searchTerm), results, source);
			} else {
				searchByName(searchTerm, results, source);
			}
			
			if (!interrupted) {
				int c = results.size();
				final String msg;
				if (c == 0) {
					msg = "Pas de resultat";
				} else if (c == 1) {
					msg = "1 personne trouvee";
				} else {
					msg = c + " personnes trouvees";
				}
				Person.this.searchTerm = searchTerm;
				Person.this.searchResults = results;

				runOnUiThread(new Runnable() {
					@Override
					public void run() {
						showMessage(msg);
						adapter.setList(results);
						adapter.notifyDataSetChanged();
					}
				});
			}
		}


		private void searchByName(String searchTerm,
				ArrayList<PersonInfo> results, List<PersonInfo> source) {
			Collator collator = Collator.getInstance(Locale.FRANCE);
			collator.setStrength(Collator.PRIMARY);
			filterStartingWith(results, source, searchTerm, collator);
			notifyDatasetChangedOnUiThread();
			filterContainsAfterFirstChar(results, source, searchTerm, collator);
			notifyDatasetChangedOnUiThread();
		}

		private void searchByTelephone(String searchTerm,
				ArrayList<PersonInfo> results, List<PersonInfo> source) {
			filterByPhone(results, source, searchTerm);
			notifyDatasetChangedOnUiThread();
		}


		void filterStartingWith(ArrayList<PersonInfo> results, List<PersonInfo> source, String searchTerm, Collator collator) {
			int l = searchTerm.length();
			for (Iterator<PersonInfo> i = personList.iterator(); i.hasNext() && !interrupted;) {
				PersonInfo person = (PersonInfo) i.next();
				String first = person.getFirstname();
				String last = person.getLastname();
				String first_last =person.getFirstname()+" "+person.getLastname();
				String last_first = person.getLastname()+" "+person.getFirstname();
				//if(searchTerm.contains(" ")){
				/*String[] searchTerms=searchTerm.split(" ");
				int t1=searchTerms[0].length();
				int t2=searchTerms[1].length();
System.out.println("T1------"+searchTerms[0]);	
System.out.println("T2------"+searchTerms[1]);	
System.out.println("t1------"+t1);	
System.out.println("t2------"+t2);	

				if ((first.length() >= l && collator.compare(first.substring(0, l), searchTerm) == 0)
						|| (last.length() >= l && collator.compare(last.substring(0, l), searchTerm) == 0)
						||(first_last.length()>=l && collator.compare(first_last.substring(0, l), searchTerm)==0)
						||(first_last.length()>=l && collator.compare(last_first.substring(0, l), searchTerm)==0)
						||(collator.compare(first.substring(0, t1), searchTerms[0])==0 && collator.compare(last.substring(0, t2), searchTerms[1])==0)
						||(collator.compare(last.substring(0, t1), searchTerms[0])==0 && collator.compare(first.substring(0, t2), searchTerms[1])==0)) {
					if (!results.contains(person)) {
						results.add(person);
					}
				}
				}
				else{*/
					if ((first.length() >= l && collator.compare(first.substring(0, l), searchTerm) == 0)
							|| (last.length() >= l && collator.compare(last.substring(0, l), searchTerm) == 0)
							||(first_last.length()>=l && collator.compare(first_last.substring(0, l), searchTerm)==0)
							||(first_last.length()>=l && collator.compare(last_first.substring(0, l), searchTerm)==0)) {
						if (!results.contains(person)) {
							results.add(person);
						}
					}
				//}
			}
		}

		boolean contains(String string, String searchTerm, Collator collator, int offset) {
			int length = searchTerm.length();
			int testEnd = string.length() - length;
			for (int i = offset; i < testEnd; i++) {
				if (collator.compare(string.substring(i, i + length), searchTerm) == 0)
					return true;
			}
			return false;
		}
		
		void filterContainsAfterFirstChar(ArrayList<PersonInfo> results, List<PersonInfo> source, String searchTerm, Collator collator) {
			for (Iterator<PersonInfo> i = personList.iterator(); i.hasNext() && !interrupted;) {
				PersonInfo person = (PersonInfo) i.next();
				String first_last = person.getFirstname()+" "+person.getLastname();
				String last_first = person.getLastname()+" "+person.getFirstname();
				if (contains(person.getFirstname(), searchTerm, collator, 1)
						|| contains(person.getLastname(), searchTerm, collator, 1)
						|| contains(first_last,searchTerm,collator,1)
						|| contains(last_first,searchTerm,collator,1)) {
					if (!results.contains(person)) {
						results.add(person);
					}
				}
			}
		}
		
		void filterByPhone(ArrayList<PersonInfo> results, List<PersonInfo> source, String searchTerm) {
			boolean optimizingMessage = false;
			for (Iterator<PersonInfo> i = personList.iterator(); i.hasNext() && !interrupted;) {
				PersonInfo person = (PersonInfo) i.next();
				String phoneDigits = person.getPhoneDigits();
				if (phoneDigits == null) {
					if (!optimizingMessage) {
						showMessageOnUiThread("Annuaire en cours d'optimisation");
						optimizingMessage = true;
					}
					StringBuffer digits = new StringBuffer();
					addPhone(person.getTel_fixe(), digits);
					addPhone(person.getTel_mobile(), digits);
					addPhone(person.getTel_interne(), digits);
					addPhone(person.getFax(), digits);
					phoneDigits = digits.toString();
					person.setPhoneDigits(phoneDigits);
				}
				if (phoneDigits.contains(searchTerm)) {
					if (!results.contains(person)) {
						results.add(person);
					}
				}
			}
			if (optimizingMessage) {
				showMessageOnUiThread("");
			}
		}
	}

	private final class DBUpdater extends Thread {
		private final DBHelper helper;
		private final PersonService personService;
		private DBUpdater(DBHelper helper, PersonService personService) {
			this.helper = helper;
			this.personService = personService;

		}

		public void run() {
			showMessageOnUiThread("Annuaire en cours d'optimisation");
			personList = helper.getAllUser();
			adapter.setList(personList);
			if (personList.size() == 0) {
				showMessageOnUiThread("Chargement en cours, navigation reste possible");
			} else {
				//showMessageOnUiThread("Mise à jour de l'annuaire");
				showMessageOnUiThread("Chargement effectué");

			}
			String lastdate = helper.getLastDate();
			Log.d(TAG, "lastdate==="+lastdate);
			personService.getInfoList(lastdate);

			showMessageOnUiThread("Annuaire en cours d'optimisation");
			Log.d(TAG, "Service done");
			personList = helper.getAllUser();

			runOnUiThread(new Runnable() {
				@Override
				public void run() {
					if (personList.size() == 0) {
						showMessage("Erreur de communication. Veuillez redemarrer");
					} else {
						showMessage(null);
					}
					adapter.setList(personList);
					adapter.notifyDataSetChanged();
					// Reset cached search results
					Person.this.searchTerm = null;
					Person.this.searchResults = null;
				}
			});
		}
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		new ExitDialog(this.getParent()).show();
	}
	
}
