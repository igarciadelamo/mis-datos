package org.pezke.misdatos.model;

import android.app.Application;

public class GlobalData extends Application {

	/**
	 * Login
	 */
	private String login;

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	
	/**
	 * Reset all fields of the object
	 */
	public void reset(){
		this.login = null;
	}
}
