package com.github.achaaab.puissance4.reseau.presentation;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

import com.github.achaaab.puissance4.application.ChargeurRessources;
import com.github.achaaab.puissance4.presentation.composants.PresentationOrdonnancement;
import com.github.achaaab.puissance4.reseau.controle.ServeurPuissance4;
import com.github.achaaab.utilitaire.GestionnaireException;
import com.github.achaaab.discussion.presentation.PresentationDiscussion;
import com.github.achaaab.exceptions.RessourceManquante;

/**
 * @author Jonathan Guéhenneux
 */
public class PresentationServeurPuissance4 extends JFrame {

	private final ServeurPuissance4 serveur;

	private JSplitPane panneauPrincipal;

	private JPanel parametrage;
	private PresentationDiscussion chat;

	private PresentationParametrageServeur parametrageServeur;
	private PresentationCommandesServeur commandes;
	private PresentationJoueurReseau parametrageJoueur1;
	private PresentationJoueurReseau parametrageJoueur2;
	private PresentationOrdonnancement ordonnancement;

	/**
	 * @param serveur
	 */
	public PresentationServeurPuissance4(ServeurPuissance4 serveur) {

		super("Hébergement");

		this.serveur = serveur;

		creerComposants();
		ajouterComposants();

		try {
			setIconImage(ChargeurRessources.getIcone("reseau_64.png"));
		} catch (RessourceManquante erreur) {
			GestionnaireException.traiter(erreur);
		}

		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * 
	 */
	private void creerComposants() {

		// panneau en deux parties, à gauche le paramétrage, à droite le chat

		panneauPrincipal = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
		panneauPrincipal.setOneTouchExpandable(true);
		panneauPrincipal.setContinuousLayout(true);
		panneauPrincipal.setDividerSize(10);

		// panneau de gauche pour les différents paramétrages

		parametrage = new JPanel();
		parametrage.setLayout(new BoxLayout(parametrage, BoxLayout.PAGE_AXIS));

		// paramétrage du serveur
		parametrageServeur = new PresentationParametrageServeur(serveur);

		// commandes du serveur
		commandes = new PresentationCommandesServeur(serveur);

		// paramétrage des joueurs
		parametrageJoueur1 = serveur.getParametrageJoueur1().getPresentation();
		parametrageJoueur2 = serveur.getParametrageJoueur2().getPresentation();

		// paramétrage de l'ordonnancement
		ordonnancement = serveur.getOrdonnancement().getPresentation();

		// chat
		chat = serveur.getDiscussion().getPresentation();
	}

	/**
	 * 
	 */
	private void ajouterComposants() {

		parametrage.add(parametrageServeur);
		parametrage.add(commandes);
		parametrage.add(parametrageJoueur1);
		parametrage.add(parametrageJoueur2);
		parametrage.add(ordonnancement);
		parametrage.add(Box.createVerticalGlue());

		panneauPrincipal.setLeftComponent(parametrage);
		panneauPrincipal.setRightComponent(chat);

		add(panneauPrincipal);
	}

	/**
	 * @return the commandes
	 */
	public PresentationCommandesServeur getCommandes() {
		return commandes;
	}

	/**
	 * @return la presentation du paramétrage du serveur
	 */
	public PresentationParametrageServeur getParametrageServeur() {
		return parametrageServeur;
	}
}
