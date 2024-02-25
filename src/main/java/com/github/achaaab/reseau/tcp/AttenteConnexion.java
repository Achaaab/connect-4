package com.github.achaaab.reseau.tcp;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;

import com.github.achaaab.utilitaire.GestionnaireException;
import com.github.achaaab.utilitaire.ProcessusInterruptible;

/**
 * @author Jonathan Guéhenneux
 */
public class AttenteConnexion extends ProcessusInterruptible {

	private final ServerSocket socket;
	private final ServeurTcp serveur;

	/**
	 * @param socket
	 * @param serveur
	 */
	public AttenteConnexion(ServerSocket socket, ServeurTcp serveur) {

		this.socket = socket;
		this.serveur = serveur;
	}

	@Override
	public void boucle() {

		try {

			var socketClient = socket.accept();
			var connexion = new ConnexionTcp(socketClient);
			serveur.ajouterConnexion(connexion);

		} catch (SocketException erreur) {

			// cause probable : fermeture du socket

		} catch (IOException erreur) {

			GestionnaireException.traiter(erreur);
		}
	}
}
