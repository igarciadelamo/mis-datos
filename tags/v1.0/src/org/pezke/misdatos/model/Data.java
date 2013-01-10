package org.pezke.misdatos.model;

import java.io.Serializable;
import java.util.Date;

import android.database.Cursor;

public class Data implements Serializable {

	/** UUID */
	private static final long serialVersionUID = 1L;
	
	
	/** Sentence to create the table */
	public static final String CREATE_TABLE = 
		"CREATE TABLE mis_datos_data_(id_ INTEGER PRIMARY KEY AUTOINCREMENT, " +
		"login_ TEXT, key_ TEXT, salt_ TEXT, value_ TEXT, count_ INTEGER, " +
		"creation_date_ LONG, last_access_ LONG)";
	
	/** Sentence to create the table */
	public static final String DELETE_TABLE = 
		"DROP TABLE IF EXISTS mis_datos_data_";
	
	/** Querys */
	public static final String TABLE_NAME = "mis_datos_data_";
	public static final String ID = "id_";
	public static final String LOGIN = "login_";
	public static final String KEY = "key_";
	public static final String SALT = "salt_";
	public static final String VALUE = "value_";
	public static final String COUNT = "count_";
	public static final String CREATION_DATE = "creation_date_";
	public static final String LAST_ACCESS = "last_access_";
	public static final String[] FIELDS = {ID, LOGIN, KEY, SALT, VALUE, COUNT, CREATION_DATE, LAST_ACCESS}; 
	public static final String WHERE_LOGIN = LOGIN + " = ?"; 
	public static final String WHERE_LOGIN_AND_KEY = LOGIN + " = ?" + "AND " + KEY + " = ?"; 

	
	/** Atributos */
	private Integer id;
	private String login;
	private String key;
	private String salt;
	private String value;
	private Integer count;
	private Date creationDate;
	private Date lastAccessDate;

	

	/**
	 * Constructor
	 */
	public Data() {
		this.id = null;
	}
	
	/**
	 * Constructor
	 */
	public Data(Cursor c) {
		this.id = c.getInt(0);
		this.login = c.getString(1);
		this.key = c.getString(2);
		this.salt = c.getString(3);
		this.value = c.getString(4);
		this.count = c.getInt(5);
		this.creationDate = new Date(c.getLong(6));
		this.lastAccessDate = new Date(c.getLong(7));
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

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the count
	 */
	public Integer getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(Integer count) {
		this.count = count;
	}

	/**
	 * @return the lastAccessDate
	 */
	public Date getLastAccessDate() {
		return lastAccessDate;
	}

	/**
	 * @param lastAccessDate the lastAccessDate to set
	 */
	public void setLastAccessDate(Date lastAccessDate) {
		this.lastAccessDate = lastAccessDate;
	}

	/**
	 * @return the salt
	 */
	public String getSalt() {
		return salt;
	}

	/**
	 * @param salt the salt to set
	 */
	public void setSalt(String salt) {
		this.salt = salt;
	}
}
