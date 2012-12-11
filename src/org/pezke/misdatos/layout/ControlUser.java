package org.pezke.misdatos.layout;

import org.pezke.misdatos.dao.DbManager;
import org.pezke.misdatos.dao.UserDao;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public abstract class ControlUser extends LinearLayout {

	
	//////////////////////////////////
	// Controls
	//////////////////////////////////
	
	protected EditText txtUser;
	protected EditText txtPassword1;
	protected EditText txtPassword2;
	protected Button btnAccept;
	protected Button btnBack;
	
	/** Text with the information for the user */
	protected TextView message;

	
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
	public ControlUser(Context context) {
		super(context);
		init();
	}
	
	/**
	 * Constructor with attributes
	 */
	public ControlUser(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	
	//////////////////////////////////
	// Control methods
	//////////////////////////////////
	
	/**
	 * Initialize the control
	 */
	protected abstract void init();
	
	
	/**
	 * Save the reference of the database manager
	 */
	public void setDbManager(DbManager dbManager){
		this.dbManager = dbManager;
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
	 * Show the info message
	 */
	public void setInfoMessage(int text) {
		message.setTextColor(Color.BLUE);
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
	
	
	/**
	 * Check the existence of a user with the same login
	 */
	public boolean checkLogin(String login) {
		UserDao dao = new UserDao(this.dbManager);
		boolean result = dao.checkByLogin(login);
		return result;
	}

	/**
	 * Check the passwords
	 */
	public boolean checkPasswords(String password1, String password2) {
		boolean result = false;
		if(password1!=null && !password1.equals("") && password2!=null && 
		   !password2.equals("") && password1.equals(password2)){
			result = true;
		}
		return result;
	}

	/**
	 * Create the new user
	 */
	public void addUser(String login, String password) {
		UserDao dao = new UserDao(this.dbManager);
		dao.save(login, password);
	}
	
}
