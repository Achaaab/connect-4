package com.github.achaaab.reseau.contrat;

/**
 * 
 * @author guehenneux
 * 
 */
public interface Message {

	String SEPARATEUR = "|";
	String REGEX_SEPARATEUR = "\\|";

	/**
	 * @return
	 */
	String serialiser();
}
