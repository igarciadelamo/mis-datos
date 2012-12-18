package org.pezke.misdatos.layout;

import org.pezke.misdatos.dao.UserDao;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public abstract class ControlUser extends LinearLayout {

	
	//////////////////////////////////
	// Controls
	//////////////////////////////////
	
	protected EditText txtUser;
	protected EditText txtPassword1;
	protected EditText txtPassword2;
	protected Button btnAccept;
	protected Button btnBack;
	
	
	//////////////////////////////////
	// DAOs
	//////////////////////////////////
	private UserDao userDao;

	
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
	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
	
	/**
	 * Show the message
	 */
	public void setMessage(String message) {
		Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * Show the message
	 */
	public void setMessage(int message) {
		Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * Check the login action
	 */
	public boolean checkLogin(String login, String password){
		boolean result = userDao.checkByLoginAndPassword(login, password);
		return result;
	}
	
	
	/**
	 * Check the existence of a user with the same login
	 */
	public boolean checkLogin(String login) {
		boolean result = userDao.checkByLogin(login);
		return result;
	}

	

	/**
	 * Create the new user
	 */
	public void addUser(String login, String password) {
		userDao.save(login, password);
	}
	
	/**
	 * Check the passwords
	 */
	public boolean checkPasswords(String password1, String password2) {
		return userDao.checkPasswords(password1, password2);
	}
	
}
