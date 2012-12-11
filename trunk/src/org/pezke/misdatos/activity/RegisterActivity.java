package org.pezke.misdatos.activity;

import org.pezke.misdatos.R;
import org.pezke.misdatos.dao.DbManager;
import org.pezke.misdatos.layout.ControlRegister;
import org.pezke.misdatos.listener.RegisterListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;

public class RegisterActivity extends Activity {

	/**
	 * Control
	 */
	private ControlRegister controlRegister;

	/**
	 * DatabaseManager
	 */
	private DbManager dbManager;
	
	
    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreate(android.os.Bundle)
     */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
		controlRegister = (ControlRegister) findViewById(R.id.CtlRegister);
		controlRegister.setRegisterListener(new RegisterListener() {
			
			/*
			 * (non-Javadoc)
			 * @see org.pezke.misdatos.listener.RegisterListener#onRegister(java.lang.String, java.lang.String, java.lang.String)
			 */
			public void onRegister(String login, String password1, String password2) {
				boolean check = controlRegister.checkLogin(login);
				if(!check){
					check = controlRegister.checkPasswords(password1, password2);
					if(check){
						controlRegister.addUser(login, password1);
						controlRegister.setInfoMessage(R.string.success_register);
					}else{
						controlRegister.setMessage(R.string.error_register_different);
					}
				}else{
					controlRegister.setMessage(R.string.error_register_existent);
				}
			}

			/*
			 * (non-Javadoc)
			 * @see org.pezke.misdatos.listener.RegisterListener#backToLogin()
			 */
			public void backToLogin() {
				Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
				startActivity(intent);
			}
		
		});
		
		//Create the database
		dbManager = new DbManager(this, DbManager.DB_NAME, null, DbManager.DB_VERSION);
		controlRegister.setDbManager(dbManager);
    }

    /*
     * (non-Javadoc)
     * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
     */
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_register, menu);
        return true;
    }
}
