package com.github.achaaab.puissance4.reseau.exceptions;

import java.text.MessageFormat;

/**
 * @author Jonathan Guéhenneux
 */
public class ViolationProtocole extends Exception {

	private static final MessageFormat FORMAT_MESSAGE = new MessageFormat(
			"Le message reçu \"{0}\" n''est pas du type attendu : {1}.");

	/**
	 * 
	 * @param messageRecu
	 * @param typeAttendu
	 * @return
	 */
	private static String fabriquerMessage(String messageRecu, String typeAttendu) {

		String[] parametresMessage = { messageRecu, typeAttendu };
		return FORMAT_MESSAGE.format(parametresMessage);
	}

	/**
	 * 
	 * @param messageRecu
	 * @param typeAttendu
	 */
	public ViolationProtocole(String messageRecu, String typeAttendu) {
		super(fabriquerMessage(messageRecu, typeAttendu));
	}

	/**
	 * @param messageRecu
	 * @param typeAttendu
	 * @param cause
	 */
	public ViolationProtocole(String messageRecu, String typeAttendu, Exception cause) {
		super(fabriquerMessage(messageRecu, typeAttendu), cause);
	}
}
