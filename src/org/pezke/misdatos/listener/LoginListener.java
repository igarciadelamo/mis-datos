package org.pezke.misdatos.listener;

public interface LoginListener {
	
	/**
	 * Manage the login action by user and password
	 */
	void onLogin(String usuario, String password);
	
	/**
	 * Show the screen to be able to create a new account
	 */
	void onNewAccount();
}
