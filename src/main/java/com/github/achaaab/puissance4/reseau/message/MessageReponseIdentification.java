package com.github.achaaab.puissance4.reseau.message;

import com.github.achaaab.reseau.contrat.Message;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class MessageReponseIdentification implements Message {

	/**
	 * 
	 */
	public static final String TYPE = "CVA/CER";

	/**
	 * 
	 * @return
	 */
	public abstract boolean isIdentificationValide();
}
