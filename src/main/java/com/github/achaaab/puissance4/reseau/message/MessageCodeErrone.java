package com.github.achaaab.puissance4.reseau.message;

/**
 * @author Jonathan Guéhenneux
 */
public class MessageCodeErrone extends MessageReponseIdentification {

	/**
	 * 
	 */
	public static final String TYPE = "CER";

	@Override
	public String serialiser() {
		return TYPE;
	}

	@Override
	public boolean isIdentificationValide() {
		return false;
	}
}
