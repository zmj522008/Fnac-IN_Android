package com.fnacin.helper;

import java.io.InputStream;
import org.xmlpull.v1.XmlPullParser;

import android.util.Log;
import android.util.Xml;
import com.fnacin.database.DBHelper;
import com.fnacin.pojo.PersonInfo;

public class GetPersonHelper {
	// Log tag
	private static final String TAG = "fnac";

	private static final boolean debug = false;

	enum Mode {
		Suppression, Ajout, Modification
	}

	private static Mode currentMode;

	 
	public static void ReadXmlByPull(InputStream inputStream) throws Exception {
		Log.d(TAG, "Begin GetPersonHelper ReadXmlByPull");
		XmlPullParser xmlpull = Xml.newPullParser();
		xmlpull.setInput(inputStream, "utf-8");
		int eventCode = xmlpull.getEventType();
		PersonInfo personInfo = null;
		String lastdate = "";
		DBHelper helper = new DBHelper(null);
		helper.openDatabase();
		int totalExpected = -1;
		helper.storeLastDate(""); // Force complete reload in case of trouble
		while (eventCode != XmlPullParser.END_DOCUMENT) {
			switch (eventCode) {
			case XmlPullParser.START_DOCUMENT: {
				break;
			}
			case XmlPullParser.START_TAG: {
				final String name = xmlpull.getName();
				if ("nb_personnes_total".equals(name)) {
					totalExpected = Integer.parseInt(xmlpull.nextText());
					break;
				}
				if ("nb_personnes_a_modifier".equals(name)) {
					if (debug) {
						Log.v(TAG, "n modifications: " + xmlpull.nextText());
					}
					break;
				}
				if ("date_maj".equals(name)) {
					lastdate = xmlpull.nextText();
					break;
				}
				if ("ajout".equals(name)) {
					if (debug) {
						Log.d(TAG, "PersonHelper new ajout");
					}
					currentMode = Mode.Ajout;
					break;
				}
				if ("modification".equals(name)) {
					currentMode = Mode.Modification;
					break;
				}
				if ("suppression".equals(name)) {
					currentMode = Mode.Suppression;
					break;
				}
				if ("personne".equals(name)) {
					personInfo = new PersonInfo();
					personInfo.setId(xmlpull.getAttributeValue("", "id"));
					break;
				}
				if ("civilite".equals(name)) {
					personInfo.setCivilite(xmlpull.nextText());
					break;
				}
				if ("nom".equals(name)) {
					personInfo.setLastname(xmlpull.nextText());
					break;
				}
				if ("prenom".equals(name)) {
					personInfo.setFirstname(xmlpull.nextText());
					break;
				}
				if ("tel_fixe".equals(name)) {
					personInfo.setTel_fixe(xmlpull.nextText());
					break;
				}
				if ("tel_interne".equals(name)) {
					personInfo.setTel_interne(xmlpull.nextText());
					break;
				}
				if ("tel_mobile".equals(name)) {
					personInfo.setTel_mobile(xmlpull.nextText());
					break;
				}
				if ("fax".equals(name)) {
					personInfo.setFax(xmlpull.nextText());
					break;
				}
				if ("email".equals(name)) {
					personInfo.setEmail(xmlpull.nextText());
					break;
				}
				if ("num_bureau".equals(name)) {
					personInfo.setNum_bureau(xmlpull.nextText());
					break;
				}
				if ("fonction".equals(name)) {
					personInfo.setFonction(xmlpull.nextText());
					break;
				}
				if ("site".equals(name)) {
					personInfo.setSite(xmlpull.nextText());
					break;
				}
				if ("adresse".equals(name)) {
					personInfo.setAdresse(xmlpull.nextText());
					break;
				}
				if ("cp".equals(name)) {
					personInfo.setCp(xmlpull.nextText());
					break;
				}
				if ("ville".equals(name)) {
					personInfo.setVille(xmlpull.nextText());
					break;
				}
				if ("commentaire".equals(name)) {
					personInfo.setCommentaire(xmlpull.nextText());
					break;
				}
				if ("site_nom".equals(name)) {
					personInfo.setSite_nom(xmlpull.nextText());
					break;
				}
				if ("site_pays".equals(name)) {
					personInfo.setSite_pays(xmlpull.nextText());
					break;
				}
				if ("site_region".equals(name)) {
					personInfo.setSite_region(xmlpull.nextText());
					break;
				}
				break;
			}
			case XmlPullParser.END_TAG: {
				final String name = xmlpull.getName();
				if ("nb_personnes_a_modifier".equals(name)) {
				} else if ("personne".equals(name)) {
					if (currentMode == Mode.Ajout) {
						helper.insert(personInfo);
						if (debug) {
							Log.d(TAG, "PersonHelper new ajout");
							Log.d(TAG, "Firstname=" + personInfo.getFirstname());
							Log.d(TAG, "Lastname=" + personInfo.getLastname());
						}
					} else if (currentMode == Mode.Modification) {
						helper.modify(personInfo);
						if (debug) {
							Log.d(TAG, "PersonHelper new Modification");
						}
					} else if (currentMode == Mode.Suppression) {
						helper.delete(personInfo);
						if (debug) {
							Log.d(TAG, "PersonHelper new Suppression");
						}
					}
				} else if ("fnac".equals(name)) {
					final int totalCount = helper.getTotalCount();
					if (totalCount == totalExpected) {
						helper.storeLastDate(lastdate);
					}
				}
				break;
			}
			}
			eventCode = xmlpull.next();
		}
		Log.d(TAG, "End GetPersonHelper ReadXmlByPull");

	}
}
