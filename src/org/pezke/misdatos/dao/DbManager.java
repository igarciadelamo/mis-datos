package org.pezke.misdatos.dao;

import org.pezke.misdatos.model.User;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbManager extends SQLiteOpenHelper {

	
	/**
	 * Version de la base de datos
	 */
	public final static int DB_VERSION	= 4;
	
	
	/**
	 * Nombre de la base de datos
	 */
	public final static String DB_NAME = "misDatosDb" + DB_VERSION;
	
	
	/**
	 * Sentencia crear tabla de datos
	 */
	private final String TABLE_DATA = 
		"CREATE TABLE mis_datos_data_(id_ INTEGER PRIMARY KEY AUTOINCREMENT, " +
		"login_ TEXT, key_ TEXT, value_ TEXT, date_ LONG)";

	
	/**
	 * Constructor
	 */
	public DbManager(Context context, String name, CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	/*
	 * (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onCreate(android.database.sqlite.SQLiteDatabase)
	 */
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(User.CREATE_TABLE);
		db.execSQL(TABLE_DATA);
	}

	/*
	 * (non-Javadoc)
	 * @see android.database.sqlite.SQLiteOpenHelper#onUpgrade(android.database.sqlite.SQLiteDatabase, int, int)
	 */
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
		if(oldVersion<newVersion){
			db.execSQL(User.DELETE_TABLE);
		}
	}

}
