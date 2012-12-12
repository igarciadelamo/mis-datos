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
import android.view.Window;

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
        
    	requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.config);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_data);
        
        //Create the database
        DbManager dbManager = new DbManager(this, DbManager.DB_NAME, null, DbManager.DB_VERSION);
        userDao = new UserDao(dbManager);
        
        // Get the custom preference
        SharedPreferences preferences = 
			getSharedPreferences(CommonConstants.DATA, Context.MODE_PRIVATE);
		String login = preferences.getString(CommonConstants.LOGIN, "");
        System.err.println("*************************** " + login);
        
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
