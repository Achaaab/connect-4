package com.github.achaaab.reseau.tcp;

import java.io.IOException;
import java.net.Socket;

import com.github.achaaab.puissance4.reseau.exceptions.MessageInvalide;
import com.github.achaaab.reseau.contrat.Connexion;
import com.github.achaaab.reseau.contrat.Entite;
import com.github.achaaab.reseau.contrat.Message;

/**
 * @author Jonathan Guéhenneux
 */
public class ConnexionTcp implements Connexion {

	private final Socket socket;
	private final SocketWriter socketWriter;
	private final SocketReader socketReader;

	/**
	 * @param socket
	 * @throws IOException
	 */
	public ConnexionTcp(Socket socket) throws IOException {

		this.socket = socket;

		socketWriter = new SocketWriter(socket);
		socketReader = new SocketReader(socket);
	}

	@Override
	public void ecrireMessage(Message message) {
		socketWriter.ecrireMessage(message);
	}

	@Override
	public void ecrire(String texte) {
		socketWriter.ecrire(texte);
	}

	@Override
	public String lireLigne() throws IOException {
		return socketReader.lireLigne();
	}

	@Override
	public Message lireMessage() throws MessageInvalide, IOException {
		return socketReader.lireMessage();
	}

	@Override
	public void fermer() throws IOException {

		socketReader.fermer();
		socketWriter.fermer();

		socket.close();
	}

	@Override
	public void attendreMessages(Entite destinataire) {
		new AttenteMessage(this, destinataire).start();
	}
}
