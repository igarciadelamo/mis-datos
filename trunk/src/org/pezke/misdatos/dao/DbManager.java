package org.pezke.misdatos.dao;

import org.pezke.misdatos.model.Data;
import org.pezke.misdatos.model.User;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbManager extends SQLiteOpenHelper {

	
	/**
	 * Version de la base de datos
	 */
	public final static int DB_VERSION	= 6;
	
	
	/**
	 * Nombre de la base de datos
	 */
	public final static String DB_NAME = "misDatosDb" + DB_VERSION;
	

	/**
	 * Instance of the DbManager
	 */
	public static DbManager getInstance(Context context) {
		DbManager db = new DbManager(context, DB_NAME, null, DB_VERSION);
		return db;
	}
	

	/**
	 * Private constructor
	 */
	private DbManager(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	/*
	 * (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(User.CREATE_TABLE);
		db.execSQL(Data.CREATE_TABLE);
	}

	/*
	 * (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		if(oldVersion<newVersion){
			//User
			db.execSQL(User.DELETE_TABLE);
			db.execSQL(User.CREATE_TABLE);
			//Data
			db.execSQL(Data.DELETE_TABLE);
			db.execSQL(Data.CREATE_TABLE);
		}
	}

}
