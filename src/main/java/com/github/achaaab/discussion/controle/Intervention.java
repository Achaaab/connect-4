package com.github.achaaab.discussion.controle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Jonathan Guéhenneux
 */
public class Intervention {

	private static final DateFormat FORMAT_DATE = new SimpleDateFormat("'('HH'h'mm')'");

	private String intervenant;
	private String message;
	private String date;

	/**
	 * @param intervenant
	 * @param message
	 */
	public Intervention(String intervenant, String message) {

		this.intervenant = intervenant;
		this.message = message;
	}

	/**
	 * 
	 */
	public void dater() {
		date = FORMAT_DATE.format(new Date());
	}

	/**
	 * @return the intervenant
	 */
	public String getIntervenant() {
		return intervenant;
	}

	/**
	 * @param intervenant the intervenant to set
	 */
	public void setIntervenant(String intervenant) {
		this.intervenant = intervenant;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}
}
