package org.pezke.misdatos.listener;

public interface RegisterListener {
	
	/**
	 * Manage the registration by user and password
	 */
	void onRegister(String usuario, String password1, String password2);
	
	/**
	 * Back to login
	 */
	void backToLogin();
}
