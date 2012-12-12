package org.pezke.misdatos.layout;

import org.pezke.misdatos.R;
import org.pezke.misdatos.listener.LoginListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ControlLogin extends ControlUser {

		
	//////////////////////////////////
	// Listener
	//////////////////////////////////
	private LoginListener listener;
	
	

	//////////////////////////////////
	// Constructors
	//////////////////////////////////


	/**
	 * Constructor
	 */
	public ControlLogin(Context context) {
		super(context);
		init();
	}
	
	/**
	 * Constructor with attributes
	 */
	public ControlLogin(Context context, AttributeSet attrs) {
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
		
		// Use the control_login layout
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
		li.inflate(R.layout.control_login, this, true);

		// References to all control elements
		txtUser = (EditText) findViewById(R.id.txtLoginUser);
		txtPassword1 = (EditText) findViewById(R.id.txtLoginPassword);
		btnAccept = (Button) findViewById(R.id.buttonLogin);
		btnBack = (Button) findViewById(R.id.buttonNewAccount);
		message = (TextView) findViewById(R.id.labelLoginMessage);

		// Create and manage events
		manageEvents();
	}

	/**
	 * Save the reference of the listener
	 */
	public void setLoginListener(LoginListener listener) {
		this.listener = listener;
	}
	

	/**
	 * Manage the events in each button
	 */
	private void manageEvents() {
		btnAccept.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				String login = txtUser.getText().toString();
				String password  = txtPassword1.getText().toString();
				boolean success = manageLogin(login, password);
				if(success){
					listener.doLogin(login);
				}
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
	 * Manage the login action
	 */
	private boolean manageLogin(String user, String password) {
		boolean result = checkLogin(user, password);
		if(!result){
			setMessage(R.string.error_login_incorrect);
		}
		return result;
	}
	
}
