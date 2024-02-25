package com.github.achaaab.puissance4.reseau.controle;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

import com.github.achaaab.puissance4.reseau.exceptions.CodeInvalide;
import com.github.achaaab.puissance4.reseau.exceptions.MessageInvalide;
import com.github.achaaab.puissance4.reseau.exceptions.ViolationProtocole;
import com.github.achaaab.puissance4.reseau.message.FabriqueMessagePuissance4;
import com.github.achaaab.puissance4.reseau.message.MessageDiscussion;
import com.github.achaaab.puissance4.reseau.message.MessageReponseIdentification;
import com.github.achaaab.puissance4.reseau.presentation.PresentationClientPuissance4;
import com.github.achaaab.reseau.contrat.Connexion;
import com.github.achaaab.reseau.contrat.Message;
import com.github.achaaab.reseau.contrat.StatutClient;
import com.github.achaaab.reseau.tcp.ClientTcp;
import com.github.achaaab.reseau.tcp.ConnexionTcp;
import com.github.achaaab.discussion.controle.Discussion;
import com.github.achaaab.discussion.controle.EcouteurIntervention;
import com.github.achaaab.discussion.controle.Intervention;

import static com.github.achaaab.reseau.contrat.StatutClient.CONNEXION_EN_COURS;
import static com.github.achaaab.reseau.contrat.StatutClient.CONNEXION_ETABLIE;
import static com.github.achaaab.reseau.contrat.StatutClient.DECONNECTE;

/**
 * @author Jonathan Guéhenneux
 */
public class ClientPuissance4 extends ClientTcp implements EcouteurIntervention {

	/**
	 * timout en millisecondes
	 */
	private static final int TIMEOUT_DEFAUT = 5000;

	private String nom;
	private String code;
	private int port;
	private String adresse;

	private final Discussion discussion;
	private final PresentationClientPuissance4 presentation;

	/**
	 * @param discussion
	 */
	public ClientPuissance4(Discussion discussion) {

		super(TIMEOUT_DEFAUT);

		nom = "Joueur 2";

		this.discussion = discussion;
		discussion.setIntervenant(nom);
		discussion.ajouterEcouteurIntervention(this);

		presentation = new PresentationClientPuissance4(this);
	}

	@Override
	public void recevoirMessage(Connexion connexion, Message message) {

		if (message instanceof MessageDiscussion messageDiscussion) {

			String intervenant = messageDiscussion.intervenant();
			String texte = messageDiscussion.texte();

			Intervention intervention = new Intervention(intervenant, texte);

			discussion.ajouterIntervention(intervention, false);
		}
	}

	@Override
	public void ajouterIntervention(Intervention intervention) {

		if (statut == CONNEXION_ETABLIE) {

			String intervenant = intervention.getIntervenant();
			String texte = intervention.getMessage();

			MessageDiscussion message = FabriqueMessagePuissance4.getInstance()
					.creerMessageDiscussion(intervenant, texte);

			connexion.ecrireMessage(message);
		}
	}

	/**
	 * @throws IOException
	 * @throws MessageInvalide
	 * @throws CodeInvalide
	 * @throws ViolationProtocole
	 */
	public void connecter() throws IOException, MessageInvalide, CodeInvalide, ViolationProtocole {

		lireParametresConnexion();
		setStatut(CONNEXION_EN_COURS);

		try {

			var socket = new Socket();
			socket.setSoTimeout(timeout);
			var adresseHote = new InetSocketAddress(adresse, port);
			socket.connect(adresseHote);
			connexion = new ConnexionTcp(socket);

		} catch (IOException erreur) {

			setStatut(DECONNECTE);
			throw erreur;
		}

		var messageIdentification = FabriqueMessagePuissance4.getInstance().creerMessageIdentification(nom, code);
		envoyerMessage(messageIdentification);

		try {

			var messageReponseIdentification = attendreMessageReponseIdentification();

			if (!messageReponseIdentification.isIdentificationValide()) {

				deconnecter();
				throw new CodeInvalide();
			}

		} catch (MessageInvalide | CodeInvalide | ViolationProtocole exception) {

			deconnecter();
			throw exception;
		}

		setStatut(CONNEXION_ETABLIE);
		connexion.attendreMessages(this);
	}

	/**
	 * @return
	 * @throws MessageInvalide
	 * @throws IOException
	 * @throws ViolationProtocole
	 */
	private MessageReponseIdentification attendreMessageReponseIdentification()
			throws MessageInvalide, IOException, ViolationProtocole {

		var message = attendreMessage();

		if (message instanceof MessageReponseIdentification messageReponseIdentification) {

			return messageReponseIdentification;

		} else {

			var messageSerialise = message.serialiser();
			throw new ViolationProtocole(messageSerialise, MessageReponseIdentification.TYPE);
		}
	}

	/**
	 * 
	 */
	private void lireParametresConnexion() {

		nom = presentation.getNom();
		adresse = presentation.getAdresse();
		port = presentation.getPort();
		code = presentation.getCode();
	}

	/**
	 * @param statut
	 */
	public void setStatut(StatutClient statut) {

		super.setStatut(statut);

		presentation.setStatut(statut.toString());
	}

	/**
	 * 
	 */
	public void afficher() {
		presentation.setVisible(true);
	}

	/**
	 * @return discussion
	 */
	public Discussion getDiscussion() {
		return discussion;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {

		this.nom = nom;

		discussion.setIntervenant(nom);
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * @return the adresse
	 */
	public String getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse = adresse;
	}
}
