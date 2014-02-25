package org.pezke.misdatos.model;

import java.io.Serializable;

public class ListElement implements Serializable {

	/** UUID */
	private static final long serialVersionUID = 1L;
	
	/** Attributes */
	private String name = null;
	private String numAccess = null;
	private String lastAccess = null;
	
	
	/**
	 * Constructor
	 */
	public ListElement(String name) {
		this.name = name;
	}
	

	/**
	 * Constructor
	 */
	public ListElement(String name, String numAccess, String lastAccess) {
		this.name = name;
		this.numAccess = numAccess;
		this.lastAccess = lastAccess;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}


	/**
	 * @return the numAccess
	 */
	public String getNumAccess() {
		return numAccess;
	}


	/**
	 * @param numAccess the numAccess to set
	 */
	public void setNumAccess(String numAccess) {
		this.numAccess = numAccess;
	}


	/**
	 * @return the lastAccess
	 */
	public String getLastAccess() {
		return lastAccess;
	}


	/**
	 * @param lastAccess the lastAccess to set
	 */
	public void setLastAccess(String lastAccess) {
		this.lastAccess = lastAccess;
	}


}
