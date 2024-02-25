package com.github.achaaab.puissance4.reseau.controle;

import java.io.IOException;

import com.github.achaaab.puissance4.moteur.Ordonnancement;
import com.github.achaaab.puissance4.presentation.utilitaire.Couleur;
import com.github.achaaab.puissance4.reseau.exceptions.MessageInvalide;
import com.github.achaaab.puissance4.reseau.exceptions.ViolationProtocole;
import com.github.achaaab.puissance4.reseau.message.FabriqueMessagePuissance4;
import com.github.achaaab.puissance4.reseau.message.MessageDiscussion;
import com.github.achaaab.puissance4.reseau.message.MessageIdentification;
import com.github.achaaab.puissance4.reseau.presentation.PresentationServeurPuissance4;
import com.github.achaaab.reseau.contrat.Connexion;
import com.github.achaaab.reseau.contrat.Message;
import com.github.achaaab.reseau.tcp.ServeurTcp;
import com.github.achaaab.utilitaire.GestionnaireException;
import com.github.achaaab.discussion.controle.Discussion;
import com.github.achaaab.discussion.controle.EcouteurIntervention;
import com.github.achaaab.discussion.controle.Intervention;

import static com.github.achaaab.puissance4.presentation.utilitaire.Couleur.JAUNE;
import static com.github.achaaab.puissance4.presentation.utilitaire.Couleur.ROUGE;

/**
 * @author Jonathan Guéhenneux
 */
public class ServeurPuissance4 extends ServeurTcp implements EcouteurIntervention {

	private final ParametrageJoueurReseau parametrageJoueur1;
	private final ParametrageJoueurReseau parametrageJoueur2;
	private final Ordonnancement ordonnancement;
	private final Discussion discussion;
	private final PresentationServeurPuissance4 presentation;

	private String code;

	/**
	 * @param discussion
	 * @throws IOException
	 */
	public ServeurPuissance4(Discussion discussion) throws IOException {

		this.discussion = discussion;
		discussion.ajouterEcouteurIntervention(this);

		port = 1000;
		code = "";

		ordonnancement = new Ordonnancement();

		parametrageJoueur1 = new ParametrageJoueurReseau("Joueur 1", "Joueur 1", true, JAUNE);
		parametrageJoueur1.setDiscussion(discussion);
		parametrageJoueur1.setServeur(this);

		parametrageJoueur2 = new ParametrageJoueurReseau("", "Joueur 2", false, ROUGE);
		parametrageJoueur2.setServeur(this);
		parametrageJoueur2.setConnecte(false);

		discussion.setIntervenant(parametrageJoueur1.getNom());
		presentation = new PresentationServeurPuissance4(this);
	}

	@Override
	public void ajouterIntervention(Intervention intervention) {

		String intervenant = intervention.getIntervenant();
		String texte = intervention.getMessage();

		var message = FabriqueMessagePuissance4.getInstance().creerMessageDiscussion(intervenant, texte);

		diffuser(message, null);
	}

	/**
	 * @param parametrageJoueurReseau
	 * @return
	 */
	public ParametrageJoueurReseau getParametrageAutreJoueur(ParametrageJoueurReseau parametrageJoueurReseau) {

		ParametrageJoueurReseau parametrageAutreJoueur;

		if (parametrageJoueurReseau == parametrageJoueur2) {
			parametrageAutreJoueur = parametrageJoueur1;
		} else {
			parametrageAutreJoueur = parametrageJoueur2;
		}

		return parametrageAutreJoueur;
	}

	/**
	 * @param connexion
	 */
	public void envoyerMessageCodeErrone(Connexion connexion) {

		var messageCodeErrone = FabriqueMessagePuissance4.getInstance().creerMessageCodeErrone();
		connexion.ecrireMessage(messageCodeErrone);
	}

	/**
	 * @param connexion
	 */
	public void envoyerMessageCodeValide(Connexion connexion) {

		var messageCodeValide = FabriqueMessagePuissance4.getInstance().creerMessageCodeValide();
		connexion.ecrireMessage(messageCodeValide);
	}

	/**
	 * @param connexion
	 * @return
	 * @throws MessageInvalide
	 * @throws IOException
	 * @throws ViolationProtocole
	 */
	public MessageIdentification attendreMessageIdentification(Connexion connexion)
			throws MessageInvalide, IOException, ViolationProtocole {

		var message = attendreMessage(connexion);

		if (message instanceof MessageIdentification messageIdentification) {

			return messageIdentification;

		} else {

			var messageSerialise = message.serialiser();
			throw new ViolationProtocole(messageSerialise, MessageIdentification.TYPE);
		}
	}

	/**
	 * @param connexion
	 * @return
	 * @throws MessageInvalide
	 * @throws IOException
	 */
	public Message attendreMessage(Connexion connexion) throws MessageInvalide, IOException {
		return connexion.lireMessage();
	}

	@Override
	public void recevoirMessage(Connexion connexion, Message message) {

		if (message instanceof MessageDiscussion messageDiscussion) {

			var intervenant = messageDiscussion.intervenant();
			var texte = messageDiscussion.texte();

			var intervention = new Intervention(intervenant, texte);
			discussion.ajouterIntervention(intervention, false);
			diffuser(messageDiscussion, connexion);
		}
	}

	/**
	 * @param parametrageJoueurReseau
	 * @param couleur
	 */
	public void setCouleur(ParametrageJoueurReseau parametrageJoueurReseau, Couleur couleur) {
		getParametrageAutreJoueur(parametrageJoueurReseau).setCouleur(Couleur.autre(couleur), false);
	}

	/**
	 * @param connexion
	 */
	public void ajouterConnexion(Connexion connexion) {

		try {

			var messageIdentification = attendreMessageIdentification(connexion);

			var nomClient = messageIdentification.getNom();
			var codeClient = messageIdentification.getCode();

			if (codeClient.equals(code)) {

				super.ajouterConnexion(connexion);
				fermer();

				parametrageJoueur2.setNom(nomClient);
				parametrageJoueur2.setConnecte(true);

				envoyerMessageCodeValide(connexion);

				presentation.getCommandes().setCommencerAutorise(true);

				connexion.attendreMessages(this);

			} else {

				envoyerMessageCodeErrone(connexion);
				connexion.fermer();
			}

		} catch (Exception exception) {

			GestionnaireException.traiter(exception);

			try {
				connexion.fermer();
			} catch (IOException ioException) {
				GestionnaireException.traiter(exception);
			}
		}
	}

	@Override
	public synchronized void fermer() throws IOException {

		super.fermer();

		if (!isComplet()) {
			presentation.getCommandes().setOuvrirAutorise(true);
		}

		presentation.getCommandes().setFermerAutorise(false);
		parametrageJoueur2.getPresentation().setBarreAttenteVisible(false);
	}

	/**
	 * @throws IOException
	 */
	public void ouvrir() throws IOException {

		port = presentation.getParametrageServeur().getPort();
		code = presentation.getParametrageServeur().getCode();

		ouvrir(port);
	}

	/**
	 * @return
	 */
	public boolean isComplet() {
		return connexions.size() == 1;
	}

	@Override
	public synchronized void ouvrir(int port) throws IOException {

		super.ouvrir(port);

		presentation.getCommandes().setOuvrirAutorise(false);
		presentation.getCommandes().setFermerAutorise(true);

		parametrageJoueur2.getPresentation().setBarreAttenteVisible(true);
	}

	/**
	 * @return
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 
	 */
	public void afficher() {
		presentation.setVisible(true);
	}

	/**
	 * 
	 */
	public void masquer() {
		presentation.setVisible(false);
	}

	/**
	 * @return the ordonnancement
	 */
	public Ordonnancement getOrdonnancement() {
		return ordonnancement;
	}

	/**
	 * @return the parametrageJoueurServeur
	 */
	public ParametrageJoueurReseau getParametrageJoueur1() {
		return parametrageJoueur1;
	}

	/**
	 * @return the parametrageJoueurClient
	 */
	public ParametrageJoueurReseau getParametrageJoueur2() {
		return parametrageJoueur2;
	}

	/**
	 * @return the com.github.achaaab.discussion
	 */
	public Discussion getDiscussion() {
		return discussion;
	}

	/**
	 * @return the presentation
	 */
	public PresentationServeurPuissance4 getPresentation() {
		return presentation;
	}
}
