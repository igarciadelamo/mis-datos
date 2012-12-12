package org.pezke.misdatos.activity;

import org.pezke.misdatos.R;
import org.pezke.misdatos.dao.DbManager;
import org.pezke.misdatos.dao.UserDao;
import org.pezke.misdatos.layout.ControlLogin;
import org.pezke.misdatos.listener.LoginListener;
import org.pezke.misdatos.model.GlobalData;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;

public class LoginActivity extends Activity {

	/**
	 * Activity Control
	 */
	private ControlLogin controlLogin;

	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreate(android.os.Bundle)
	 */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		GlobalData globalData = (GlobalData)getApplication();
		globalData.reset();

		controlLogin = (ControlLogin) findViewById(R.id.CtlLogin);
		controlLogin.setLoginListener(new LoginListener() {
			
			/*
			 * (non-Javadoc)
			 * @see org.pezke.misdatos.listener.LoginListener#doLogin(java.lang.String)
			 */
			public void doLogin(String login) {
				//Set the login in the application
				GlobalData globalData = (GlobalData)getApplication();
				globalData.setLogin(login);
				
				//Start the new activity
				Intent intent = new Intent(LoginActivity.this, DataActivity.class);
				startActivity(intent);
			}
			
			
			/*
			 * (non-Javadoc)
			 * @see org.pezke.misdatos.listener.BackListener#back()
			 */
			public void back() {
				Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
			
		});
		
		//Create the database
		DbManager dbManager = new DbManager(this, DbManager.DB_NAME, null, DbManager.DB_VERSION);
		UserDao userDao = new UserDao(dbManager);
		controlLogin.setUserDao(userDao);
	}


	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertDialog.Builder builder = createAlertDialog();
			builder.show();
			// The event has already been processed
			return true;
		}
		
		// In other case, follow the chain events
		return super.onKeyDown(keyCode, event);
	}
	
	
	/**
	 * Create alert to do the logout
	 */
	private AlertDialog.Builder createAlertDialog(){
		AlertDialog.Builder result = new AlertDialog.Builder(this)
		.setIcon(android.R.drawable.ic_dialog_alert)
		.setTitle(R.string.back_message)
		.setMessage(R.string.back_confirmation)
		
		//Button cancel with no listener
		.setNegativeButton(android.R.string.cancel, null)
		
		//Button ok with listener
		.setPositiveButton(android.R.string.ok, createOnClickListener());
				
		return result;
	}
	
	/**
	 * Create listener to finish the activity
	 */
	private DialogInterface.OnClickListener createOnClickListener(){
		DialogInterface.OnClickListener result = 
			new DialogInterface.OnClickListener() {
			
				/*
				 * (non-Javadoc)
				 * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
				 */
				public void onClick(DialogInterface dialog, int which) {
					// Logout
					LoginActivity.this.finish();
				}
		};
		
		return result;
	}
	

}
