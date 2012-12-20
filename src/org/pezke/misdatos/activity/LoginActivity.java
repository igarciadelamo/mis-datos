package org.pezke.misdatos.activity;

import org.pezke.misdatos.R;
import org.pezke.misdatos.dao.DbManager;
import org.pezke.misdatos.dao.UserDao;
import org.pezke.misdatos.util.CommonConstants;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	
	/**
	 * Activity Control
	 */
	private EditText txtUser;
	private EditText txtPassword;
	
	
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
		setContentView(R.layout.activity_login);
		
		//Reset login
		setLogin("");
		
		// References to all control elements
		txtUser = (EditText) findViewById(R.id.txtLoginUser);
		txtPassword = (EditText) findViewById(R.id.txtLoginPassword);
			
		// Create and manage events
		Button btnAccept = (Button) findViewById(R.id.buttonLogin);
		btnAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String login = txtUser.getText().toString();
				String password  = txtPassword.getText().toString();
				boolean success = manageLogin(login, password);
				if(success){
					doLogin(login);
				}
			}
		});
		
		Button btnBack = (Button) findViewById(R.id.buttonNewAccount);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goToRegister();
			}
		});
		
		//Create the database
		DbManager dbManager = DbManager.getInstance(this);
		this.userDao = new UserDao(dbManager);
	}


	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onResume()
	 */
	protected void onResume() {
		reset();
		super.onResume();
	}
	
	/*
	 * (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		//Press the back button
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
		.setCancelable(false)
		
		//Key listener
		.setOnKeyListener(createOnKeyListener())
		
		//Button cancel with listener
		.setNegativeButton(R.string.cancel, createOnClickListenerCancel())
		
		//Button ok with listener
		.setPositiveButton(R.string.accept, createOnClickListenerOk());
				
		return result;
	}
	
	
	/**
	 * Create listener to cencel the dialog
	 */
	private DialogInterface.OnClickListener createOnClickListenerCancel(){
		DialogInterface.OnClickListener result = 
			new DialogInterface.OnClickListener() {
			
				/*
				 * (non-Javadoc)
				 * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
				 */
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
		};
		
		return result;
	}
	
	
	/**
	 * Create key listener for the dialog
	 */
	private DialogInterface.OnKeyListener createOnKeyListener(){
		DialogInterface.OnKeyListener result = 
			new DialogInterface.OnKeyListener() {

				/*
				 * (non-Javadoc)
				 * @see android.content.DialogInterface.OnKeyListener#onKey(android.content.DialogInterface, int, android.view.KeyEvent)
				 */
				public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
					if(keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
						dialog.cancel();
						finish();
					}
					
					return false;	
				}
		};
		
		return result;
	}
	
	
	/**
	 * Create listener to finish the activity
	 */
	private DialogInterface.OnClickListener createOnClickListenerOk(){
		DialogInterface.OnClickListener result = 
			new DialogInterface.OnClickListener() {
			
				/*
				 * (non-Javadoc)
				 * @see android.content.DialogInterface.OnClickListener#onClick(android.content.DialogInterface, int)
				 */
				public void onClick(DialogInterface dialog, int which) {
					// Logout
					finish();
				}
		};
		
		return result;
	}
	
	
	/**
	 * Manage the login action
	 */
	private boolean manageLogin(String login, String password) {
		boolean result = userDao.checkByLoginAndPassword(login, password);
		if(!result){
			txtUser.requestFocus();
			setMessage(R.string.error_login_incorrect);
		}
		return result;
	}

	/**
	 * Reset form fields
	 */
	private void reset() {
		txtUser.setText("");
		txtPassword.setText("");
	}
	
	/**
	 * 
	 * @param login
	 */
	private void doLogin(String login) {
		//Save the login
		setLogin(login);
		
		//Start the new activity
		Intent intent = new Intent(LoginActivity.this, DataActivity.class);
		startActivity(intent);
	}

	/**
	 * Save the login
	 */
	private void setLogin(String login) {
		SharedPreferences preferences = 
			getSharedPreferences(CommonConstants.DATA, Context.MODE_PRIVATE);
		Editor editor = preferences.edit();
		editor.putString(CommonConstants.LOGIN, login);
		editor.commit();
	}

	
	/**
	 * Go to register activity
	 */
	private void goToRegister() {
		Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
		startActivity(intent);
	}
	
	/**
	 * Show the message
	 */
	private void setMessage(int message) {
		Toast.makeText(LoginActivity.this, message, Toast.LENGTH_LONG).show();
	}
}
