package com.github.achaaab.puissance4.reseau.message;

/**
 * @author Jonathan Guéhenneux
 */
public class MessageCodeValide extends MessageReponseIdentification {

	/**
	 * 
	 */
	public static final String TYPE = "CVA";

	@Override
	public String serialiser() {
		return TYPE;
	}

	@Override
	public boolean isIdentificationValide() {
		return true;
	}
}
