package com.github.achaaab.reseau.tcp;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import com.github.achaaab.reseau.contrat.Connexion;
import com.github.achaaab.reseau.contrat.Message;
import com.github.achaaab.reseau.contrat.Serveur;

/**
 * @author Jonathan Guéhenneux
 */
public class ServeurTcp implements Serveur {

	private final String adresse;

	protected int port;
	private ServerSocket socket;
	protected List<Connexion> connexions;
	private AttenteConnexion attenteClient;
	private boolean ouvert;

	/**
	 * @throws UnknownHostException
	 */
	public ServeurTcp() throws UnknownHostException {

		ouvert = false;

		var adresseINet = InetAddress.getLocalHost();
		adresse = adresseINet.getHostAddress();

		connexions = new ArrayList<>();
	}

	/**
	 * 
	 * @param connexion
	 */
	public void ajouterConnexion(Connexion connexion) {
		connexions.add(connexion);
	}

	@Override
	public String getAdresse() {
		return adresse;
	}

	@Override
	public int getPort() {
		return port;
	}

	/**
	 * @return
	 */
	public ServerSocket getSocket() {
		return socket;
	}

	@Override
	public synchronized void fermer() throws IOException {

		if (ouvert) {

			attenteClient.interrompre();
			socket.close();
			ouvert = false;
		}
	}

	@Override
	public synchronized void ouvrir(int port) throws IOException {

		fermer();

		this.port = port;

		socket = new ServerSocket(port);

		attenteClient = new AttenteConnexion(socket, this);
		ouvert = true;
		attenteClient.start();
	}

	@Override
	public boolean isOuvert() {
		return ouvert;
	}

	@Override
	public void diffuser(Message message, Connexion connexionExclue) {

		for (var connexion : connexions) {

			if (connexion != connexionExclue) {
				connexion.ecrireMessage(message);
			}
		}
	}

	@Override
	public List<Connexion> getConnexions() {
		return connexions;
	}

	@Override
	public void recevoirMessage(Connexion connexion, Message message) {
		// on ne fait rien dans le serveur TCP de base
	}
}
