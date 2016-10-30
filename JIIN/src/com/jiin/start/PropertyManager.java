package com.jiin.start;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.jiin.MyApplication;

public class PropertyManager {
	private static PropertyManager instance;
	public static PropertyManager getInstance() {
		if (instance == null) {
			instance = new PropertyManager();
		}
		return instance;
	}
	SharedPreferences mPrefs;
	SharedPreferences.Editor mEditor;
	
	private PropertyManager() {
		mPrefs = PreferenceManager.getDefaultSharedPreferences(MyApplication.getContext());
		mEditor = mPrefs.edit();
	}
	
	private static final String FIELD_FACEBOOK_ID = "facebookid";
	public void setFacebookId(String facebookId) {
		mEditor.putString(FIELD_FACEBOOK_ID, facebookId);
		mEditor.commit();
	}
	public String getFacebookId() {
		return mPrefs.getString(FIELD_FACEBOOK_ID, "");
	}
	
	private final String FIELD_USER_EMAIL = "email";
	public String getUserEmail() {
		return mPrefs.getString(FIELD_USER_EMAIL, "");
	}
	public void setUserEmail(String email) {
		mEditor.putString(FIELD_USER_EMAIL, email);
		mEditor.commit();
	}
	
	
	public final String JOIN_PATH = "join_path";
	public String getJoinPath() {
		return mPrefs.getString(JOIN_PATH,"");
	}
	public void setJoinPath(String path){
		mEditor.putString(JOIN_PATH, path);
		mEditor.commit();
	}
	
	public final String PASSWORD = "password";
	public String getPassword(){
		return mPrefs.getString(PASSWORD,"");
	}
	public void setPassword(String password){
		mEditor.putString(PASSWORD, password);
		mEditor.commit();
	}
	
	public final String REGID = "regid";
	public String getRegistrationId() {
		return mPrefs.getString(REGID, "");
	}
	public void setRegistrationId(String regid) {
		mEditor.putString(REGID,regid);
		mEditor.commit();
	}
	
	public final String MY_GENDER = "my_gender";
	public String getMyGender(){
		return mPrefs.getString(MY_GENDER,"");
	}
	public void setMyGender(String gender){
		mEditor.putString(MY_GENDER, gender);
		mEditor.commit();
	}
}	
