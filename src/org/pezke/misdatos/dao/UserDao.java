package org.pezke.misdatos.dao;

import java.util.Date;

import org.pezke.misdatos.model.User;
import org.pezke.misdatos.utl.PasswordUtils;

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
		
		//Db Connector
		SQLiteDatabase db = getDbReader();
		if(db != null){
			//Query
			String key = PasswordUtils.encode(login, password);
			String[] args = new String[] {login, key};
			Cursor c = db.query(User.TABLE_NAME, User.FIELDS, 
					User.WHERE_LOGIN_PASS, args, null, null, null);
			if (c.moveToFirst()) {
				result = true;
			}
			
			//Close the connection
			db.close();
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
			
			//Insert the new register
			ContentValues cv = new ContentValues();
			cv.put(User.LOGIN, login);
			cv.put(User.PASSWORD, PasswordUtils.encode(login, password));
			cv.put(User.DATE, new Date().getTime());
			db.insert(User.TABLE_NAME, null, cv);

			//Close the connection
			db.close();
		}

	}

}
