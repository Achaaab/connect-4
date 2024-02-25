package com.github.achaaab.reseau.contrat;

import com.github.achaaab.puissance4.reseau.exceptions.MessageInvalide;

import java.io.IOException;

/**
 * @author Jonathan Guéhenneux
 */
public interface Client extends Entite {

	/**
	 * @return
	 */
	StatutClient getStatut();

	/**
	 * @param statut
	 */
	void setStatut(StatutClient statut);

	/**
	 * @param message
	 */
	void envoyerMessage(Message message);

	/**
	 * @return
	 * @throws MessageInvalide
	 * @throws IOException
	 */
	Message attendreMessage() throws MessageInvalide, IOException;

	/**
	 * @return
	 */
	Connexion getConnexion();

	/**
	 * 
	 * @param adresseHote
	 * @param port
	 * @throws IOException
	 */
	void connecter(String adresseHote, int port) throws IOException;

	/**
	 * @throws IOException
	 */
	void deconnecter() throws IOException;
}
