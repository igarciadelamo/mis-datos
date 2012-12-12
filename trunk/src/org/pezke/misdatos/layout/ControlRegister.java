package org.pezke.misdatos.layout;

import org.pezke.misdatos.R;
import org.pezke.misdatos.listener.BackListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ControlRegister extends ControlUser {

	
	//////////////////////////////////
	// Listener
	//////////////////////////////////

	private BackListener listener;


	//////////////////////////////////
	// Constructores
	//////////////////////////////////


	/**
	 * Constructor
	 */
	public ControlRegister(Context context) {
		super(context);
		init();
	}
	
	/**
	 * Constructor with attributes
	 */
	public ControlRegister(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}


	//////////////////////////////////
	// Control methods
	//////////////////////////////////
	
	/**
	 * Initialize the control
	 */
	protected void init() {
		
		// Use the control_login register
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
		li.inflate(R.layout.control_register, this, true);

		// References to all control elements
		txtUser = (EditText) findViewById(R.id.txtRegisterUser);
		txtPassword1 = (EditText) findViewById(R.id.txtRegisterPassword1);
		txtPassword2 = (EditText) findViewById(R.id.txtRegisterPassword2);
		btnAccept = (Button) findViewById(R.id.buttonRegister);
		btnBack = (Button) findViewById(R.id.buttonBackToLogin);
		message = (TextView) findViewById(R.id.labelRegisterMessage);

		// Create and manage events
		manageEvents();
	}

	/**
	 * Save the reference of the listener
	 */
	public void setBackListener(BackListener l) {
		listener = l;
	}

		
	/**
	 * Manage the events in the button
	 */
	private void manageEvents() {
		btnAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				manageRegister(
						txtUser.getText().toString(), 
						txtPassword1.getText().toString(),
						txtPassword2.getText().toString());
			}
		});
		
		btnBack.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.back();
			}
		});
	}
	
	
	
	/**
	 * Manage the registration process
	 */
	public void manageRegister(String login, String password1, String password2) {
		boolean check = checkLogin(login);
		if(!check){
			check = checkPasswords(password1, password2);
			if(check){
				addUser(login, password1);
				setInfoMessage(R.string.success_register);
			}else{
				setMessage(R.string.error_password_different);
			}
		}else{
			setMessage(R.string.error_login_existent);
		}
	}
	
}
