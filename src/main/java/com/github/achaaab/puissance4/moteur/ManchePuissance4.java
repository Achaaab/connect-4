package com.github.achaaab.puissance4.moteur;

import java.awt.Point;
import java.util.Set;

import static com.github.achaaab.puissance4.moteur.EtatManche.ARRETEE;
import static com.github.achaaab.puissance4.moteur.EtatManche.CREE;
import static com.github.achaaab.puissance4.moteur.EtatManche.EN_COURS;
import static com.github.achaaab.puissance4.moteur.EtatManche.TERMINEE;
import static com.github.achaaab.puissance4.moteur.Puissance4.GRILLE;

/**
 * @author Jonathan Guéhenneux
 */
public class ManchePuissance4 {

	private final Joueur joueur1;
	private final Joueur joueur2;
	private final boolean joueur1Commence;

	private Joueur joueurCourant;
	private EtatManche etat;

	/**
	 * @param joueur1
	 * @param joueur2
	 * @param joueur1Commence
	 */
	public ManchePuissance4(Joueur joueur1, Joueur joueur2, boolean joueur1Commence) {

		this.joueur1 = joueur1;
		this.joueur2 = joueur2;
		this.joueur1Commence = joueur1Commence;

		etat = CREE;
	}

	/**
	 *
	 */
	public void commencer() {

		GRILLE.initialiser();

		Set<Point> jetonsAlignes;

		if (joueur1Commence) {
			joueurCourant = joueur1;
		} else {
			joueurCourant = joueur2;
		}

		etat = EN_COURS;
		GRILLE.couleurCourante = joueurCourant.getCouleur();

		do {

			joueurCourant.demanderJouer();
			GRILLE.getPresentation().recalculerImageAsynchrone();

			if (etat == EN_COURS) {

				jetonsAlignes = GRILLE.jetonsAlignes;

				if (!jetonsAlignes.isEmpty()) {

					int score = joueurCourant.getScore();
					joueurCourant.setScore(score + 1);
					etat = TERMINEE;

				} else if (GRILLE.isComplete()) {

					etat = TERMINEE;

				} else {

					joueurSuivant();
				}
			}

		} while (etat == EN_COURS);
	}

	/**
	 * 
	 */
	public void arreter() {

		etat = ARRETEE;

		joueur1.arreterJouer();
		joueur2.arreterJouer();

		GRILLE.arreterAnimations();
	}

	/**
	 * change le joueur courant
	 */
	private void joueurSuivant() {

		if (joueurCourant == joueur1) {
			joueurCourant = joueur2;
		} else {
			joueurCourant = joueur1;
		}
	}
}
