package com.github.achaaab.reseau.tcp;

import com.github.achaaab.puissance4.reseau.exceptions.MessageInvalide;
import com.github.achaaab.puissance4.reseau.message.FabriqueMessagePuissance4;
import com.github.achaaab.reseau.contrat.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 * @author Jonathan Guéhenneux
 */
public class SocketReader {

	private final BufferedReader flux;

	/**
	 * @param socket
	 * @throws IOException
	 */
	public SocketReader(Socket socket) throws IOException {

		var fluxDirect = socket.getInputStream();
		var reader = new InputStreamReader(fluxDirect);
		flux = new BufferedReader(reader);
	}

	/**
	 * @return
	 * @throws IOException
	 */
	public String lireLigne() throws IOException {
		return flux.readLine();
	}

	/**
	 * @return
	 * @throws IOException
	 * @throws MessageInvalide
	 */
	public Message lireMessage() throws IOException, MessageInvalide {

		var messageSerialise = lireLigne();

		return FabriqueMessagePuissance4.getInstance().deserialiser(messageSerialise);
	}

	/**
	 * ferme le flux de lecture sur le socket
	 * 
	 * @throws IOException
	 */
	public void fermer() throws IOException {
		flux.close();
	}
}
