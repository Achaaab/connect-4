package com.github.achaaab.puissance4.moteur;

import com.github.achaaab.discussion.controle.Discussion;
import com.github.achaaab.puissance4.ia.JoueurIa;
import com.github.achaaab.puissance4.presentation.composants.PresentationPuissance4;
import com.github.achaaab.puissance4.reseau.controle.ClientPuissance4;
import com.github.achaaab.puissance4.reseau.controle.ServeurPuissance4;

import java.io.IOException;

/**
 * 
 * @author GUEHENNEUX
 * 
 */
public class Puissance4 {

	public static final int LARGEUR_GRILLE = 7;
	public static final int HAUTEUR_GRILLE = 6;
	public static final int LONGUEUR_ALIGNEMENT = 4;

	public static final GrillePuissance4 GRILLE = new GrillePuissance4();

	private final ParametragePartie parametrage;
	private final Discussion discussion;
	private final PresentationPuissance4 presentation;

	private PartiePuissance4 partieCourante;
	private ServeurPuissance4 serveur;
	private ClientPuissance4 client;

	/**
	 * 
	 */
	public Puissance4() {

		discussion = new Discussion(50);
		parametrage = new ParametragePartie();
		presentation = new PresentationPuissance4(this);
	}

	/**
	 * 
	 */
	public void autoriserMancheSuivante() {
		presentation.autoriserMancheSuivante();
	}

	/**
	 * 
	 */
	public void interdireMancheSuivante() {
		presentation.interdireMancheSuivante();
	}

	/**
	 * 
	 */
	public void rejoindrePartie() {

		if (client == null) {
			client = new ClientPuissance4(discussion);
		}

		client.afficher();
	}

	/**
	 * @throws IOException
	 * 
	 */
	public void hebergerPartie() throws IOException {

		if (serveur == null) {
			serveur = new ServeurPuissance4(discussion);
		}

		serveur.afficher();
	}

	/**
	 * 
	 */
	public void nouvellePartie() {

		if (partieCourante != null) {
			arreterPartie();
		}

		// lecture du paramétrage de la partie dans la présentation
		parametrage.lire();

		// creation du joueur1

		Joueur joueur1;

		int couleurJoueur1 = parametrage.getCouleurJoueur1().getNumero();
		String nomJoueur1 = parametrage.getNomJoueur1();

		if (parametrage.isJoueur1Ordinateur()) {

			var niveauJoueur1 = parametrage.getNiveauJoueur1();
			var joueur1Deterministe = parametrage.isJoueur1Deterministe();

			joueur1 = new JoueurIa(couleurJoueur1, nomJoueur1, niveauJoueur1, joueur1Deterministe);

		} else {

			joueur1 = new JoueurHumain(couleurJoueur1, nomJoueur1);
		}

		// creation du joueur2

		Joueur joueur2;

		var couleurJoueur2 = parametrage.getCouleurJoueur2().getNumero();
		var nomJoueur2 = parametrage.getNomJoueur2();

		if (parametrage.isJoueur2Ordinateur()) {

			var niveauJoueur2 = parametrage.getNiveauJoueur2();
			var joueur2Deterministe = parametrage.isJoueur2Deterministe();

			joueur2 = new JoueurIa(couleurJoueur2, nomJoueur2, niveauJoueur2, joueur2Deterministe);

		} else {

			joueur2 = new JoueurHumain(couleurJoueur2, nomJoueur2);
		}

		// création de la partie
		partieCourante = new PartiePuissance4(this, joueur1, joueur2);

		// paramétrage de l'ordonnancement
		var ordonnancement = parametrage.getOrdonnancement();

		partieCourante.setJoueur1Commence(ordonnancement.isJoueur1Commence());
		partieCourante.setJoueur2Commence(ordonnancement.isJoueur2Commence());
		partieCourante.setPremierJoueurAleatoire(ordonnancement.isPremierJoueurAleatoire());
		partieCourante.setPremierJoueurAlterne(ordonnancement.isPremierJoueurAlterne());

		// affichage de la partie
		presentation.ajouterPartie(partieCourante.getPresentation());

		// lancement de la première manche
		mancheSuivante();
	}

	/**
	 * 
	 */
	public void arreterPartie() {
		partieCourante.arreter();
	}

	/**
	 * 
	 */
	public void mancheSuivante() {
		partieCourante.mancheSuivante();
	}

	/**
	 * 
	 */
	public void quitter() {
		System.exit(0);
	}

	/**
	 * @return the presentation
	 */
	public PresentationPuissance4 getPresentation() {
		return presentation;
	}

	/**
	 * @return the parametrage
	 */
	public ParametragePartie getParametrage() {
		return parametrage;
	}
}
