package com.github.achaaab.puissance4.reseau.exceptions;

import java.text.MessageFormat;

/**
 * @author Jonathan Guéhenneux
 */
public class MessageInvalide extends Exception {

	private static final MessageFormat FORMAT_MESSAGE = new MessageFormat(
			"Le message \"{0}\" n''est pas compris par le protocole réseau du Puissance 4.");

	/**
	 * 
	 * @param messageRecu
	 * @return
	 */
	private static String fabriquerMessage(String messageRecu) {

		String[] parametresMessage = { messageRecu };
		return FORMAT_MESSAGE.format(parametresMessage);
	}

	private String messageRecu;

	/**
	 * @param messageRecu
	 */
	public MessageInvalide(String messageRecu) {

		super(fabriquerMessage(messageRecu));

		this.messageRecu = messageRecu;
	}

	/**
	 * 
	 * @param messageRecu
	 * @param exceptionMere
	 */
	public MessageInvalide(String messageRecu, Exception exceptionMere) {

		super(fabriquerMessage(messageRecu), exceptionMere);

		this.messageRecu = messageRecu;
	}

	/**
	 * @return the messageRecu
	 */
	public String getMessageRecu() {
		return messageRecu;
	}
}
