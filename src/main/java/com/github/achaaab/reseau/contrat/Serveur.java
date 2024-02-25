package com.github.achaaab.reseau.contrat;

import java.io.IOException;
import java.util.List;

/**
 * @author Jonathan Guéhenneux
 */
public interface Serveur extends Entite {

	/**
	 * @return
	 */
	String getAdresse();

	/**
	 * @return
	 */
	int getPort();

	/**
	 * @return
	 */
	List<Connexion> getConnexions();

	/**
	 * @param message message a diffuser
	 * @param connexionExclue connexion exclue de la diffusion,
	 * {@code null} si le message doit être diffusé sur toutes les connexions
	 */
	void diffuser(Message message, Connexion connexionExclue);

	/**
	 * @param port
	 * @throws IOException
	 */
	void ouvrir(int port) throws IOException;

	/**
	 * @throws IOException
	 */
	void fermer() throws IOException;

	/**
	 * @return
	 */
	boolean isOuvert();
}
