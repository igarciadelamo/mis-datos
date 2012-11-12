package org.pezke.misdatos.util;

import org.pezke.misdatos.model.User;

import android.util.Base64;

public class PasswordUtils {

	/**
	 * Encode the password
	 */
	public static String encode(String login, String password){
		String key = login + "@|@" + password;
		byte[] data = Base64.encode(key.getBytes(), Base64.DEFAULT);
		String result = new String(data);
		return result;
	}
	
	/**
	 * Check if the password is correct for this user
	 */
	public static boolean comparePassword(User user, String password){
		boolean result = false;
		String code = encode(user.getLogin(), password);
		if(code.equals(user.getPassword())){
			result = true;
		}
		return result;
	}
}
