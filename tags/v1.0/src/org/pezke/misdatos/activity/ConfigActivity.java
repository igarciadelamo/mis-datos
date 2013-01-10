package org.pezke.misdatos.activity;

import org.pezke.misdatos.R;
import org.pezke.misdatos.dao.DbManager;
import org.pezke.misdatos.dao.UserDao;
import org.pezke.misdatos.layout.PasswordDialog;
import org.pezke.misdatos.util.CommonConstants;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class ConfigActivity extends PreferenceActivity {

		
	/**
	 * DAOs
	 */
	private UserDao userDao;
	
	
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    public void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.config);
                
        //Create the database
        DbManager dbManager = DbManager.getInstance(this);
        userDao = new UserDao(dbManager);
        
        // Get the custom preference
        SharedPreferences preferences = 
			getSharedPreferences(CommonConstants.DATA, Context.MODE_PRIVATE);
		String login = preferences.getString(CommonConstants.LOGIN, "");
               
        PasswordDialog preference = (PasswordDialog) findPreference(CommonConstants.PASSWORD);
        preference.setLogin(login);
    }
    
	    
    /**
	 * Create the new user
	 */
	public void changePassword(String login, String password) {
		userDao.save(login, password);
	}


	
    
}
