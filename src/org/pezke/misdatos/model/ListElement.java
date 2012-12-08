package org.pezke.misdatos.model;

import java.io.Serializable;

public class ListElement implements Serializable {

	/** UUID */
	private static final long serialVersionUID = 1L;
	
	/** Attributes */
	private String name = null;
	private String description = null;

	/**
	 * Constructor
	 * @param name
	 * @param description
	 */
	public ListElement(String name, String description) {
		super();
		this.name = name;
		this.description = description;
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
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description
	 *            the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

}
