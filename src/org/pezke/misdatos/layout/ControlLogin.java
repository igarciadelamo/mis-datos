package org.pezke.misdatos.layout;

import org.pezke.misdatos.R;
import org.pezke.misdatos.dao.DbManager;
import org.pezke.misdatos.dao.UserDao;
import org.pezke.misdatos.listener.LoginListener;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ControlLogin extends LinearLayout {

	//////////////////////////////////
	// Controls
	//////////////////////////////////
	
	private EditText txtUser;
	private EditText txtPassword;
	private Button btnLogin;
	private Button btnCreateAccount;
	private TextView message;

	
	//////////////////////////////////
	// Listener
	//////////////////////////////////
	private LoginListener listener;
	
	
	//////////////////////////////////
	// Database Manager
	//////////////////////////////////
	private DbManager dbManager;

	
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
	private void init() {
		
		// Use the control_login layout
		String infService = Context.LAYOUT_INFLATER_SERVICE;
		LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
		li.inflate(R.layout.control_login, this, true);

		// References to all control elements
		txtUser = (EditText) findViewById(R.id.txtLoginUser);
		txtPassword = (EditText) findViewById(R.id.txtLoginPassword);
		btnLogin = (Button) findViewById(R.id.buttonLogin);
		btnCreateAccount = (Button) findViewById(R.id.buttonNewAccount);
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
	 * Save the reference of the database manager
	 */
	public void setDbManager(DbManager dbManager){
		this.dbManager = dbManager;
	}

	/**
	 * Manage the events in each button
	 */
	private void manageEvents() {
		btnLogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onLogin(
						txtUser.getText().toString(), 
						txtPassword.getText().toString());
			}
		});
		
		btnCreateAccount.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				listener.onNewAccount();
			}
		});
	}
	
	
	/**
	 * Show the error message
	 */
	public void setMessage(String text) {
		message.setText(text);
	}
	
	/**
	 * Show the error messageuser
	 */
	public void setMessage(int text) {
		message.setText(text);
	}
	
	/**
	 * Check the login action
	 */
	public boolean checkLogin(String login, String password){
		UserDao dao = new UserDao(this.dbManager);
		boolean result = dao.checkByLoginAndPassword(login, password);
		return result;
	}
}
