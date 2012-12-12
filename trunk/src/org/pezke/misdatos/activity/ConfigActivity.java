package org.pezke.misdatos.activity;

import org.pezke.misdatos.R;
import org.pezke.misdatos.dao.DbManager;
import org.pezke.misdatos.dao.UserDao;
import org.pezke.misdatos.layout.PasswordDialog;
import org.pezke.misdatos.model.GlobalData;

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
        GlobalData globalData = (GlobalData)getApplication();
        String login = globalData.getLogin();
        System.err.println("*************************** " + login);
        
        PasswordDialog preference = (PasswordDialog) findPreference("preferencePassword");
        preference.setLogin(login);
    }
    
	    
    /**
	 * Create the new user
	 */
	public void changePassword(String login, String password) {
		userDao.save(login, password);
	}


	
    
}
