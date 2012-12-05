package org.pezke.misdatos.util;

import org.pezke.misdatos.model.User;


public class PasswordUtils {

	/**
	 * Encode the password
	 */
	public static Password encode(String password){
		String salt = PasswordHasher.getSalt();
		Password result = PasswordHasher.hash(password, salt);
		return result;
	}
	
	/**
	 * Check if the password is correct for this user
	 */
	public static boolean comparePassword(String value, User user){
		boolean result = PasswordHasher.isValid(value, user.getPassword(), user.getSalt());
		return result;
	}
	
}
