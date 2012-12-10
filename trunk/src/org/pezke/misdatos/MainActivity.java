package org.pezke.misdatos;

import org.pezke.misdatos.dao.DbManager;
import org.pezke.misdatos.layout.ControlLogin;
import org.pezke.misdatos.listener.LoginListener;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;

public class MainActivity extends Activity {

	/**
	 * Activity Control
	 */
	private ControlLogin controlLogin;

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
		setContentView(R.layout.activity_login);

		controlLogin = (ControlLogin) findViewById(R.id.CtlLogin);
		controlLogin.setLoginListener(new LoginListener() {
			
			/*
			 * (non-Javadoc)
			 * @see org.pezke.misdatos.listener.LoginListener#onLogin(java.lang.String, java.lang.String)
			 */
			public void onLogin(String user, String password) {
				boolean check = controlLogin.checkLogin(user, password);
				if(check){
					Intent intent = new Intent(MainActivity.this, DataActivity.class);
					startActivity(intent);
				}else{
					controlLogin.setMessage(R.string.error_login_incorrect);
				}
			}
			
			/*
			 * (non-Javadoc)
			 * @see org.pezke.misdatos.listener.LoginListener#onNewAccount()
			 */
			public void onNewAccount() {
				Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
				startActivity(intent);
			}
		});
		
		//Create the database
		dbManager = new DbManager(this, DbManager.DB_NAME, null, DbManager.DB_VERSION);
		controlLogin.setDbManager(dbManager);
	}


	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onCreateOptionsMenu(android.view.Menu)
	 */
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
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
					MainActivity.this.finish();
				}
		};
		
		return result;
	}
	

}
