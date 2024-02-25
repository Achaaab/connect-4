package com.github.achaaab.reseau.tcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

import com.github.achaaab.reseau.contrat.Message;

/**
 * @author Jonathan Guéhenneux
 */
public class SocketWriter {

	private final PrintWriter flux;

	/**
	 * @param socket
	 * @throws IOException
	 */
	public SocketWriter(Socket socket) throws IOException {
		flux = new PrintWriter(socket.getOutputStream(), true);
	}

	/**
	 * @param texte
	 */
	public void ecrire(String texte) {
		flux.println(texte);
	}

	/**
	 * 
	 * @param message
	 */
	public void ecrireMessage(Message message) {

		var messageSerialise = message.serialiser();
		ecrire(messageSerialise);
	}

	/**
	 * ferme le flux d'ecriture sur le socket
	 */
	public void fermer() {
		flux.close();
	}
}
