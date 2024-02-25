package com.github.achaaab.puissance4.reseau.message;

import com.github.achaaab.reseau.contrat.Message;

/**
 * @author Jonathan Guéhenneux
 */
public class MessageCoup implements Message {

	/**
	 * 
	 */
	public static final String TYPE = "COU";

	private final int colonne;

	/**
	 * 
	 * @param colonne
	 */
	public MessageCoup(int colonne) {
		this.colonne = colonne;
	}

	@Override
	public String serialiser() {
		return TYPE + SEPARATEUR + colonne;
	}
}
