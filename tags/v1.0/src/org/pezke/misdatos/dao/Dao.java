package org.pezke.misdatos.dao;

import android.database.sqlite.SQLiteDatabase;


public class Dao {

	//Database Manager
	private DbManager dbManager;

	
	/**
	 * Constructor
	 */
	public Dao(DbManager dbManager) {
		this.dbManager = dbManager;
	}
	
	/**
	 * Get the database connection in writing mode
	 */
	public SQLiteDatabase getDbWriter(){
		return dbManager.getWritableDatabase();
	}
	
	/**
	 * Get the database connection in reading mode
	 */
	public SQLiteDatabase getDbReader(){
		return dbManager.getReadableDatabase();
	}


}
