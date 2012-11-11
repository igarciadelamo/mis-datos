package org.pezke.misdatos.model;

import java.io.Serializable;
import java.util.Date;

import android.database.Cursor;

public class User implements Serializable {

	/** UUID */
	private static final long serialVersionUID = 1L;
	
	
	/** Sentence to create the table */
	public static final String TABLE = 
		"CREATE TABLE mis_datos_user_(id_ INTEGER PRIMARY KEY AUTOINCREMENT, " +
		"login_ TEXT, password_ TEXT, date_ LONG)";
	
	/** Querys */
	public static final String TABLE_NAME = "mis_datos_user_";
	public static final String ID = "id_";
	public static final String LOGIN = "login_";
	public static final String PASSWORD = "password_";
	public static final String DATE = "date_";
	public static final String[] FIELDS = {ID, LOGIN, PASSWORD, DATE}; 
	public static final String WHERE_LOGIN = LOGIN + " = ?"; 
	public static final String WHERE_LOGIN_PASS = LOGIN + " = ? and " + PASSWORD + " = ?"; 

	
	/** Attributes */
	private Integer id;
	private String login;
	private String password;
	private Date creationDate;

	
	
	/**
	 * Constructor
	 */
	public User() {
		this.id = null;
	}
	

	/**
	 * Constructor
	 */
	public User(String login, String password) {
		this.id = null;
		this.login = login;
		this.password = password;
		this.creationDate = new Date();
	}
	
	/**
	 * Constructor
	 */
	public User(Integer id, String login, String password, Date creationDate) {
		this.id = id;
		this.login = login;
		this.password = password;
		this.creationDate = creationDate;
	}
	
	/**
	 * Constructor
	 */
	public User(Cursor c) {
		this.id = c.getInt(0);
		this.login = c.getString(1);
		this.password = c.getString(2);
		this.creationDate = new Date(c.getLong(3));
	}
	
	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login
	 *            the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the creationDate
	 */
	public Date getCreationDate() {
		return creationDate;
	}

	/**
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
