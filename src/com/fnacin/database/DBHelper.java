package com.fnacin.database;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.fnacin.pojo.PersonInfo;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper {

	public static final String DB_DBNAME = "employees";
	public static final String DB_TABLENAME = "employee";
	public static final String DB_TABLENAME_DATE = "lastdate";
	public static final int VERSION =1;
	public static SQLiteDatabase dbInstance;
	private MyDBHelper myDBHelper;
	private Context context;
   
	public DBHelper(Context context) {
		this.context = context;
	}
	public void openDatabase() {
		if(dbInstance == null) {
			myDBHelper = new MyDBHelper(context,DB_DBNAME,VERSION);
			dbInstance = myDBHelper.getWritableDatabase();
		}
	}
	// insert 
	public long insert(PersonInfo person) {
		ContentValues values = new ContentValues();
		values.put("id", person.id);
		values.put("lastname", person.lastname);
		values.put("firstname", person.firstname);
		values.put("civilite", person.civilite);
		values.put("tel_fixe", person.tel_fixe);
		values.put("tel_interne", person.tel_interne);
		values.put("tel_mobile", person.tel_mobile);
		values.put("fax", person.fax);
		values.put("email", person.email);
		values.put("num_bureau", person.num_bureau);
		values.put("fonction", person.fonction);
		values.put("site", person.site);
		values.put("adresse", person.adresse);
		values.put("cp", person.cp);
		values.put("ville", person.ville);
		values.put("commentaire", person.commentaire);
		values.put("site_nom", person.site_nom);
		values.put("site_pays", person.site_pays);
		values.put("site_region", person.site_region);
		
		return dbInstance.replace(DB_TABLENAME, null, values);
	}
	
	// modify
	public void modify(PersonInfo person) {
		ContentValues values = new ContentValues();
		values.put("lastname", person.lastname);
		values.put("firstname", person.firstname);
		values.put("civilite", person.civilite);
		values.put("tel_fixe", person.tel_fixe);
		values.put("tel_interne", person.tel_interne);
		values.put("tel_mobile", person.tel_mobile);
		values.put("fax", person.fax);
		values.put("email", person.email);
		values.put("num_bureau", person.num_bureau);
		values.put("fonction", person.fonction);
		values.put("site", person.site);
		values.put("adresse", person.adresse);
		values.put("cp", person.cp);
		values.put("ville", person.ville);
		values.put("commentaire", person.commentaire);
		values.put("site_nom", person.site_nom);
		values.put("site_pays", person.site_pays);
		values.put("site_region", person.site_region);
		
		dbInstance.update(DB_TABLENAME, values, "id=?", new String[] { String.valueOf(person.id) });
	}
	
	// delete
	public int delete(PersonInfo person) {
		return dbInstance.delete(DB_TABLENAME, "id = ?", new String[] { person.getId() });
	}
	//get user
	public List<PersonInfo> getAllUser() {
		long startDate = new Date().getTime();
		//ArrayList list = new ArrayList();
		List<PersonInfo> em = new ArrayList<PersonInfo>();
		Cursor cursor = null;
		// request without phone numbers is 33% faster
		String sql = "SELECT id, lastname, firstname, tel_fixe, tel_interne, tel_mobile, fax FROM EMPLOYEE ORDER BY lastname ASC";
		cursor = dbInstance.rawQuery(sql, null);
		while(cursor.moveToNext()) {
			String id= cursor.getString(0);
			String lastname= cursor.getString(1);
			String firstname= cursor.getString(2);
			String tel_fixe= cursor.getString(3);
			String tel_interne= cursor.getString(4);
			String tel_mobile= cursor.getString(5);
			String fax= cursor.getString(6);
			em.add(new PersonInfo(id,lastname,firstname,null,tel_fixe,tel_interne,tel_mobile,fax,null,null,null,null,null,null,null,null,null,null,null));
		}
		cursor.close();
		Log.v("fnac", "getAllUser in " + (new Date().getTime() - startDate) + "ms");
		return em;
	}

	//get user
	public PersonInfo getUser(String id) {
		//ArrayList list = new ArrayList();
		Cursor cursor = null;
		PersonInfo p = null;
		// request without phone numbers is 33% faster
		String sql = "SELECT lastname, firstname, tel_fixe, tel_interne, tel_mobile, fax, civilite, email, num_bureau, fonction, site, adresse, cp, ville, commentaire, site_nom, site_pays, site_region FROM EMPLOYEE WHERE id = '" + id + "'";
		cursor = dbInstance.rawQuery(sql, null);
		while(cursor.moveToNext()) {
			String lastname= cursor.getString(0);
			String firstname= cursor.getString(1);
			String tel_fixe= cursor.getString(2);
			String tel_interne= cursor.getString(3);
			String tel_mobile= cursor.getString(4);
			String fax= cursor.getString(5);
			String civilite = cursor.getString(6);
			String email = cursor.getString(7);
			String num_bureau = cursor.getString(8);
			String fonction = cursor.getString(9);
			String site = cursor.getString(10);
			String adresse = cursor.getString(11);
			String cp = cursor.getString(12);
			String ville = cursor.getString(13);
			String commentaire = cursor.getString(14);
			String site_nom = cursor.getString(15);
			String site_pays = cursor.getString(16);
			String site_region = cursor.getString(17);
			p = new PersonInfo(id, lastname, firstname, civilite, tel_fixe,
					tel_interne, tel_mobile, fax, email, num_bureau, fonction,
					site, adresse, cp, ville, commentaire, site_nom, site_pays,
					site_region);
		}
		cursor.close();
		return p;
	}
	
	public void storeLastDate(String date) { 
		
		if(DB_TABLENAME_DATE!=null){
			dbInstance.delete(DB_TABLENAME_DATE, null, null);
		}
		ContentValues values = new ContentValues();
		values.put("date", date);
		dbInstance.insert(DB_TABLENAME_DATE, null, values);
	}
	
	public String getLastDate() 
	{
		String lastDate = "";
		Cursor cursor = dbInstance.rawQuery("SELECT * FROM " + DB_TABLENAME_DATE, null);
		if (cursor.moveToNext()) {
			lastDate = cursor.getString(0);
		}
		cursor.close();
		return lastDate;
	}
	
	class MyDBHelper extends SQLiteOpenHelper {

		public MyDBHelper(Context context, String name,
				int version) {
			super(context, name, null, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("CREATE TABLE IF NOT EXISTS "+ DB_TABLENAME+" (id INTEGER PRIMARY KEY," +
					"lastname text," +
					"firstname text," +
					"civilite text," +
					"tel_fixe text," +
					"tel_interne text," +
					"tel_mobile text," +
					"fax text," +
					"email text," +
					"num_bureau text," +
					"fonction text," +
					"site text," +
					"adresse text," +
					"cp text," +
					"ville text," +
					"commentaire text," +
					"site_nom text," +
					"site_pays text," +
					"site_region text)");
			db.execSQL("CREATE TABLE "+ DB_TABLENAME_DATE+"(date text)");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			String sql = "drop table if exists " + DB_TABLENAME;
			db.execSQL(sql);
			myDBHelper.onCreate(db);
		}
	}
	
	//get total count
	public int getTotalCount() {
		Cursor cursor = dbInstance.query(DB_TABLENAME, new String[]{"count(*)"}, null, null, null, null, null);
		cursor.moveToNext();
		int count = cursor.getInt(0);
		cursor.close();
		return count;
	}
	
}
