package com.github.achaaab.puissance4.reseau.exceptions;


/**
 * @author Jonathan Guéhenneux
 */
public class CodeInvalide extends Exception {

	private static final String MESSAGE = "Le serveur a refusé votre code.";

	/**
	 * 
	 */
	public CodeInvalide() {
		super(MESSAGE);
	}

	/**
	 * 
	 * @param cause
	 */
	public CodeInvalide(Exception cause) {
		super(MESSAGE, cause);
	}
}
