package com.github.achaaab.reseau.contrat;

/**
 * @author Jonathan Guéhenneux
 */
public interface Entite {

	/**
	 * @param connexion
	 * @param message
	 */
	void recevoirMessage(Connexion connexion, Message message);
}
