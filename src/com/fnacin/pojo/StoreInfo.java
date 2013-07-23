package com.fnacin.pojo;

import java.io.Serializable;

public class StoreInfo implements Serializable{
	public final static String SER_KEY = "com.fnac.pojo.personinfo";
	
	private String id;
	private String nom;
	private String adresse;
	private String code_postal;
	private String pays;
	private String region;
	private String ville;
	private String latitude;
	private String longitude;
	private String fax;
	private String telephone;
	private String email;
	private String billeterie;
	private String ouverture;
	private String ouverture_exceptionnelle;
	public void setEmail(String email) {
		this.email = email;
	}
	private String url_fnaccom;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getAdresse() {
		return adresse;
	}
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
	public String getCode_postal() {
		return code_postal;
	}
	public void setCode_postal(String code_postal) {
		this.code_postal = code_postal;
	}
	public String getPays() {
		return pays;
	}
	public void setPays(String pays) {
		this.pays = pays;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getVille() {
		return ville;
	}
	public void setVille(String ville) {
		this.ville = ville;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmai(String email) {
		this.email = email;
	}
	public StoreInfo() {
		super();
	}
	public String getBilleterie() {
		return billeterie;
	}
	public void setBilleterie(String billeterie) {
		this.billeterie = billeterie;
	}
	public String getOuverture() {
		return ouverture;
	}
	public void setOuverture(String ouverture) {
		this.ouverture = ouverture;
	}
	public String getOuverture_exceptionnelle() {
		return ouverture_exceptionnelle;
	}
	public void setOuverture_exceptionnelle(String ouverture_exceptionnelle) {
		this.ouverture_exceptionnelle = ouverture_exceptionnelle;
	}
	public String getUrl_fnaccom() {
		return url_fnaccom;
	}
	public void setUrl_fnaccom(String url_fnaccom) {
		this.url_fnaccom = url_fnaccom;
	}
	public StoreInfo(String id, String nom, String adresse, String code_postal,
			String pays, String region, String ville, String latitude,
			String longitude, String fax, String telephone, String email,
			String billeterie, String ouverture,
			String ouverture_exceptionnelle, String url_fnaccom) {
		super();
		this.id = id;
		this.nom = nom;
		this.adresse = adresse;
		this.code_postal = code_postal;
		this.pays = pays;
		this.region = region;
		this.ville = ville;
		this.latitude = latitude;
		this.longitude = longitude;
		this.fax = fax;
		this.telephone = telephone;
		this.email = email;
		this.billeterie = billeterie;
		this.ouverture = ouverture;
		this.ouverture_exceptionnelle = ouverture_exceptionnelle;
		this.url_fnaccom = url_fnaccom;
	}
	
}
