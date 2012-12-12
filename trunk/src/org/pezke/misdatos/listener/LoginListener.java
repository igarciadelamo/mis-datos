package org.pezke.misdatos.listener;

public interface LoginListener extends BackListener{
	
	/**
	 * Manage the login action by user and password
	 */
	public void doLogin(String login);
	
}
