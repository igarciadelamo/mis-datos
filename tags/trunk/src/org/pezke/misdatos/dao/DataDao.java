package org.pezke.misdatos.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.pezke.misdatos.model.Data;
import org.pezke.misdatos.model.User;
import org.pezke.misdatos.util.Password;
import org.pezke.misdatos.util.PasswordUtils;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DataDao extends Dao {

	/**
	 * Constructor
	 */
	public DataDao(DbManager dbManager) {
		super(dbManager);
	}
	
	/**
	 * Get the data by login and key
	 */
	public Data getByLoginAndKey(String login, String key){
		
		Data result = null;
		
		//Db Connector
		SQLiteDatabase db = getDbReader();
		if(db != null){
			//Query
			String[] args = new String[] {login, key};
			Cursor c = db.query(Data.TABLE_NAME, Data.FIELDS, 
					Data.WHERE_LOGIN_AND_KEY, args, null, null, null);
			if (c.moveToFirst()) {
				result = new Data(c);
			}
			
			//Close the cursor
			c.close();
			
			//Close the connection
			db.close();
		}
		
		return result;
	}
	

	/**
	 * Get the data by login and key
	 */
	public List<Data> getByLogin(String login){
		
		List<Data> result = new ArrayList<Data>();
		
		//Db Connector
		SQLiteDatabase db = getDbReader();
		if(db != null){
			//Query
			String[] args = new String[] {login};
			Cursor c = db.query(Data.TABLE_NAME, Data.FIELDS, 
					Data.WHERE_LOGIN, args, null, null, null);
			if (c.moveToFirst()) {
				do {
					Data item = new Data(c);
					result.add(item);
				} while(c.moveToNext());
			}
			
			//Close the cursor
			c.close();
			
			//Close the connection
			db.close();
		}
		
		return result;
	}
	
	
	/**
	 * Check if there is a data with the same login and key
	 */
	public boolean checkByLoginAndKey(String login, String key){
		boolean result = false;
		Data data = getByLoginAndKey(login, key);
		if(data!=null){
			result = true;
		}
		return result;
	}
	
	/**
	 * Create a new data in the database
	 */
	public Data save(User user, String key, String value){
		
		//Db Connector
		SQLiteDatabase db = getDbWriter();
		if(db != null){
			
			//Encode the password
			Password passwordHash = PasswordUtils.crypt(user, value);
			
			//Creation date
			Date creationDate = new Date();
			
			//Insert the new register
			ContentValues cv = new ContentValues();
			cv.put(Data.LOGIN, user.getLogin());
			cv.put(Data.KEY, key);
			cv.put(Data.SALT, passwordHash.getSalt());
			cv.put(Data.VALUE, passwordHash.getHash());
			cv.put(Data.CREATION_DATE, creationDate.getTime());
			db.insert(Data.TABLE_NAME, null, cv);

			//Close the connection
			db.close();
		}
		
		//Get the element
		Data data = getByLoginAndKey(user.getLogin(), key);
		return data;
	}
	
	/**
	 * Update the counter and the last access date for a existent data
	 */
	public Data update(String login, String key){
		
		//Db Connector
		Data data = getByLoginAndKey(login, key);
		SQLiteDatabase db = getDbWriter();
		if(db != null){
			
			//Getting information
			Integer count = data.getCount() + 1;
			Date lastAccess = new Date();
			
			data.setCount(count);
			data.setLastAccessDate(lastAccess);
						
			//Insert the new register
			ContentValues cv = new ContentValues();
			cv.put(Data.COUNT, data.getCount());
			cv.put(Data.LAST_ACCESS, data.getLastAccessDate().getTime());
			String[] args = new String[] {login, key};
			db.update(Data.TABLE_NAME, cv, Data.WHERE_LOGIN_AND_KEY, args);
			
			//Close the connection
			db.close();
		}
		
		return data;
	}

	/**
	 * Delete the specified data
	 */
	public void delete(String login, String key){
		SQLiteDatabase db = getDbWriter();
		if(db != null){
			
			//Delete in db
			String[] args = new String[] {login, key};
			db.delete(Data.TABLE_NAME, Data.WHERE_LOGIN_AND_KEY, args);
			
			//Close the connection
			db.close();
		}
	}
		
}
