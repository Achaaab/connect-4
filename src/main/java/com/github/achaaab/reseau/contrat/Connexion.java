package com.github.achaaab.reseau.contrat;

import com.github.achaaab.puissance4.reseau.exceptions.MessageInvalide;

import java.io.IOException;

/**
 * @author Jonathan Guéhenneux
 */
public interface Connexion {

	/**
	 * @param message
	 */
	void ecrireMessage(Message message);

	/**
	 * @param texte
	 */
	void ecrire(String texte);

	/**
	 * @return
	 * @throws IOException
	 */
	String lireLigne() throws IOException;

	/**
	 * @return
	 * @throws MessageInvalide
	 * @throws IOException
	 */
	Message lireMessage() throws MessageInvalide, IOException;

	/**
	 * @param destinataire
	 */
	void attendreMessages(Entite destinataire);

	/**
	 * @throws IOException
	 */
	void fermer() throws IOException;
}
