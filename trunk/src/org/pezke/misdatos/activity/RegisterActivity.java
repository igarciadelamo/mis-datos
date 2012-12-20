package org.pezke.misdatos.activity;

import org.pezke.misdatos.R;
import org.pezke.misdatos.dao.DbManager;
import org.pezke.misdatos.dao.UserDao;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends Activity {

	/**
	 * Activity Control
	 */
	private EditText txtUser;
	private EditText txtPassword1;
	private EditText txtPassword2;
	
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
        setContentView(R.layout.activity_register);
        
        // References to all control elements
		txtUser = (EditText) findViewById(R.id.txtRegisterUser);
		txtPassword1 = (EditText) findViewById(R.id.txtRegisterPassword1);
		txtPassword2 = (EditText) findViewById(R.id.txtRegisterPassword2);
				
		Button btnAccept = (Button) findViewById(R.id.buttonRegister);
		btnAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				boolean success = manageRegister(
						txtUser.getText().toString(), 
						txtPassword1.getText().toString(),
						txtPassword2.getText().toString());
				if(success){
					goToLogin();
				}
			}
		});
		
		Button btnBack = (Button) findViewById(R.id.buttonBackToLogin);
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				goToLogin();
			}
		});
       		
		//Create the database
		DbManager dbManager = DbManager.getInstance(this);
		this.userDao = new UserDao(dbManager);
    }
    
    
    /**
	 * Manage the registration process
	 */
	private boolean manageRegister(String login, String password1, String password2) {
		boolean success = false;
		boolean check = userDao.checkByLoginAndPassword(login, password1);
		if(!check){
			check = userDao.checkPasswords(password1, password2);
			if(check){
				userDao.save(login, password1);
				setMessage(R.string.success_register);
				success = true;
			}else{
				setMessage(R.string.error_password);
			}
		}else{
			setMessage(R.string.error_login_existent);
		}
		return success;
	}
	
	
	/**
	 * Go to the login activity
	 */
	private void goToLogin() {
		finish();
	}
	
	/**
	 * Show the message
	 */
	private void setMessage(int message) {
		Toast.makeText(RegisterActivity.this, message, Toast.LENGTH_LONG).show();
	}
   
}
