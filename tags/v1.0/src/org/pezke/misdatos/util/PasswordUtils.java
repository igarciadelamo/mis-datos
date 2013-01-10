package org.pezke.misdatos.util;

import org.pezke.misdatos.model.Data;
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
	 * Encode the password with salt
	 */
	public static Password encode(String password, String salt){
		Password result = PasswordHasher.hash(password, salt);
		return result;
	}
	
	/**
	 * Crypt the password
	 */
	public static Password crypt(User user, String text){
		Password result = PasswordHasher.crypt(user, text);
		return result;
	}
	
	/**
	 * Decrypt the password
	 */
	public static String decrypt(User user, Data data){
		String result = PasswordHasher.decrypt(user, data);
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
