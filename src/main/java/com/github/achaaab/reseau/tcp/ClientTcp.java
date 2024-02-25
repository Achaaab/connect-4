package com.github.achaaab.reseau.tcp;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.github.achaaab.puissance4.reseau.exceptions.MessageInvalide;
import com.github.achaaab.reseau.contrat.Client;
import com.github.achaaab.reseau.contrat.Connexion;
import com.github.achaaab.reseau.contrat.Message;
import com.github.achaaab.reseau.contrat.StatutClient;

import static com.github.achaaab.reseau.contrat.StatutClient.CONNEXION_ETABLIE;
import static com.github.achaaab.reseau.contrat.StatutClient.DECONNECTE;

/**
 * @author Jonathan Guéhenneux
 */
public class ClientTcp implements Client {

	protected StatutClient statut;
	protected Connexion connexion;
	protected int timeout;

	/**
	 * @param timeout
	 */
	public ClientTcp(int timeout) {

		this.timeout = timeout;

		statut = DECONNECTE;
	}

	@Override
	public void envoyerMessage(Message message) {
		connexion.ecrireMessage(message);
	}

	@Override
	public Message attendreMessage() throws MessageInvalide, IOException {
		return connexion.lireMessage();
	}

	@Override
	public void recevoirMessage(Connexion connexion, Message message) {

	}

	@Override
	public void connecter(String adresseHote, int port) throws IOException {

		setStatut(StatutClient.CONNEXION_EN_COURS);

		try {

			var socket = new Socket();
			socket.setSoTimeout(timeout);
			var adresse = new InetSocketAddress(adresseHote, port);
			socket.connect(adresse);
			connexion = new ConnexionTcp(socket);

		} catch (IOException erreur) {

			setStatut(DECONNECTE);
			throw erreur;
		}

		setStatut(CONNEXION_ETABLIE);
	}

	@Override
	public Connexion getConnexion() {
		return connexion;
	}

	@Override
	public void deconnecter() throws IOException {

		connexion.fermer();
		setStatut(DECONNECTE);
	}

	@Override
	public StatutClient getStatut() {
		return statut;
	}

	@Override
	public void setStatut(StatutClient statut) {
		this.statut = statut;
	}
}
