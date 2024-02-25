package com.github.achaaab.puissance4.reseau.message;

import com.github.achaaab.reseau.contrat.Message;

/**
 * @author Jonathan Guéhenneux
 */
public class MessageIdentification implements Message {

	/**
	 * 
	 */
	public static final String TYPE = "IDE";

	private String nom;
	private String code;

	/**
	 * @param nom
	 * @param code
	 */
	public MessageIdentification(String nom, String code) {

		this.nom = nom;
		this.code = code;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String serialiser() {
		return TYPE + SEPARATEUR + nom + SEPARATEUR + code;
	}
}
