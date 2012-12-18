package org.pezke.misdatos.activity;

import org.pezke.misdatos.R;
import org.pezke.misdatos.dao.DbManager;
import org.pezke.misdatos.dao.UserDao;
import org.pezke.misdatos.layout.ControlRegister;
import org.pezke.misdatos.listener.BackListener;

import android.app.Activity;
import android.os.Bundle;

public class RegisterActivity extends Activity {

	/**
	 * Control
	 */
	private ControlRegister controlRegister;

	
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
		controlRegister = (ControlRegister) findViewById(R.id.CtlRegister);
		controlRegister.setBackListener(new BackListener() {
			
			/*
			 * (non-Javadoc)
			 * @see org.pezke.misdatos.listener.RegisterListener#backToLogin()
			 */
			public void back() {
				finish();
			}
		
		});
		
		//Create the database
		DbManager dbManager = DbManager.getInstance(this);
		UserDao userDao = new UserDao(dbManager);
		controlRegister.setUserDao(userDao);
    }
   
}
