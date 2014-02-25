package org.pezke.misdatos.activity;

import java.util.List;

import org.pezke.misdatos.R;
import org.pezke.misdatos.dao.DataDao;
import org.pezke.misdatos.dao.DbManager;
import org.pezke.misdatos.dao.UserDao;
import org.pezke.misdatos.layout.DeleteAccountDialog;
import org.pezke.misdatos.layout.PasswordDialog;
import org.pezke.misdatos.model.Data;
import org.pezke.misdatos.util.CommonConstants;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ConfigActivity extends PreferenceActivity {

	/**
	 * Login
	 */
	private String login;

	/**
	 * DAOs
	 */
	private UserDao userDao;
	private DataDao dataDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	@SuppressWarnings("deprecation")
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.config);

		// Create the database
		DbManager dbManager = DbManager.getInstance(this);
		userDao = new UserDao(dbManager);
		dataDao = new DataDao(dbManager);

		// Get the custom preference
		SharedPreferences preferences = getSharedPreferences(
				CommonConstants.DATA, Context.MODE_PRIVATE);
		login = preferences.getString(CommonConstants.LOGIN, "");

		PasswordDialog preference = (PasswordDialog) findPreference(CommonConstants.PASSWORD);
		preference.setLogin(login);

		DeleteAccountDialog deleteAccount = (DeleteAccountDialog) findPreference(CommonConstants.DELETE_ACCOUNT);
		deleteAccount.setCallbackActivity(this);
	}

	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		super.onPause();
		setResult(CommonConstants.RESULT_PAUSED_CONFIGURATION);
		finish();
	}
	
	/**
	 * Create the new user
	 */
	public void changePassword(String login, String password) {
		userDao.save(login, password);
	}

	public void deleteAccount() {
		// Delete data info
		List<Data> list = dataDao.getByLogin(login);
		if (list != null) {
			for (Data each : list) {
				dataDao.delete(login, each.getKey());
			}
		}
		
		userDao.delete(login);
		setResult(CommonConstants.RESULT_DELETE_ACCOUNT);
		finish();
	}

}
