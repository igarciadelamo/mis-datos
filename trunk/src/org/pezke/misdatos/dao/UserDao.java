package org.pezke.misdatos.dao;

import java.util.Date;

import org.pezke.misdatos.model.User;
import org.pezke.misdatos.util.Password;
import org.pezke.misdatos.util.PasswordUtils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserDao extends Dao {

	/**
	 * Constructor
	 */
	public UserDao(DbManager dbManager) {
		super(dbManager);
	}
	
	/**
	 * Get the user by login
	 */
	public User getByLogin(String login){
		
		User result = null;
		
		//Db Connector
		SQLiteDatabase db = getDbReader();
		if(db != null){
			//Query
			String[] args = new String[] {login};
			Cursor c = db.query(User.TABLE_NAME, User.FIELDS, 
					User.WHERE_LOGIN, args, null, null, null);
			if (c.moveToFirst()) {
				result = new User(c);
			}
			
			//Close the cursor
			c.close();
			
			//Close the connection
			db.close();
		}
		
		return result;
	}
	
	/**
	 * Check if there is a user with the same login and password 
	 */
	public boolean checkByLoginAndPassword(String login, String password){
		boolean result = false;
		User user = getByLogin(login);
		if(user!=null){
			result = PasswordUtils.comparePassword(password, user);
		}
		return result;
	}
	
	/**
	 * Check if there is a user with the same login 
	 */
	public boolean checkByLogin(String login){
		boolean result = false;
		User user = getByLogin(login);
		if(user!=null){
			result = true;
		}
		return result;
	}
	
	/**
	 * Create a new user in the database
	 */
	public void save(String login, String password){
		
		//Db Connector
		SQLiteDatabase db = getDbWriter();
		if(db != null){
			
			//Encode the password
			Password passwordHash = PasswordUtils.encode(password);
						
			//Creation date
			Date creationDate = new Date();
			
			//Insert the new register
			ContentValues cv = new ContentValues();
			cv.put(User.LOGIN, login);
			cv.put(User.SALT, passwordHash.getSalt());
			cv.put(User.PASSWORD, passwordHash.getHash());
			cv.put(User.DATE, creationDate.getTime());
			db.insert(User.TABLE_NAME, null, cv);

			//Close the connection
			db.close();
		}
	}
	
	/**
	 * Update the password for a existent user
	 */
	public void update(String login, String password){
		
		//Db Connector
		User user = getByLogin(login);
		SQLiteDatabase db = getDbWriter();
		if(db != null){
			
			//Encode the password
			Password passwordHash = PasswordUtils.encode(password, user.getSalt());
						
			//Insert the new register
			ContentValues cv = new ContentValues();
			cv.put(User.PASSWORD, passwordHash.getHash());
			String[] args = new String[] {login};
			db.update(User.TABLE_NAME, cv, User.WHERE_LOGIN, args);
			
			//Close the connection
			db.close();
		}
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
	 * Delete the user row
	 */
	public void delete(String login){
		SQLiteDatabase db = getDbWriter();
		if(db != null){
			
			//Delete in db
			String[] args = new String[] {login};
			db.delete(User.TABLE_NAME, User.WHERE_LOGIN, args);
			
			//Close the connection
			db.close();
		}
	}
	
}
