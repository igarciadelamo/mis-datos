package org.pezke.misdatos.activity;

import org.pezke.misdatos.R;
import org.pezke.misdatos.dao.DbManager;
import org.pezke.misdatos.dao.UserDao;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.view.Window;
import android.widget.Toast;

public class ConfigActivity extends PreferenceActivity {

		
	/**
	 * Database Manager
	 */
	private DbManager dbManager;

	
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    public void onCreate(Bundle savedInstanceState) {
        
        
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.config);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_data);
        
        // Get the custom preference
        Preference preference = (Preference) findPreference("preferencePassword");
		preference
				.setOnPreferenceClickListener(new OnPreferenceClickListener() {

					public boolean onPreferenceClick(Preference preference) {
						Toast.makeText(getBaseContext(),
								"The custom preference has been clicked",
								Toast.LENGTH_LONG).show();
						return true;
					}

				});
        
        
        //Create the database
        this.dbManager = new DbManager(this, DbManager.DB_NAME, null, DbManager.DB_VERSION);
    }
    
	    
    /**
	 * Create the new user
	 */
	public void modifyUser(String login, String password) {
		UserDao dao = new UserDao(this.dbManager);
		dao.save(login, password);
	}


	
    
}
