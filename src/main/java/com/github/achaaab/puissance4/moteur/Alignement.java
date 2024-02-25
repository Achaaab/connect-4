package com.github.achaaab.puissance4.moteur;

import static com.github.achaaab.puissance4.moteur.GrillePuissance4.COULEUR_INDEFINIE;
import static com.github.achaaab.puissance4.moteur.GrillePuissance4.JAUNE;
import static com.github.achaaab.puissance4.moteur.Puissance4.HAUTEUR_GRILLE;
import static com.github.achaaab.puissance4.moteur.Puissance4.LARGEUR_GRILLE;
import static com.github.achaaab.puissance4.moteur.Puissance4.LONGUEUR_ALIGNEMENT;

/**
 * @author Jonathan Guéhenneux
 */
public class Alignement {

	static final float VALEUR_ALIGNEMENT_1 = 1.0f;
	static final float COEFFICIENT_AMELIORATION_ALIGNEMENT = 10.0f;
	static final float[] VALEURS_ALIGNEMENTS;
	static final float[] AUGMENTATIONS_ALIGNEMENTS;
	static final float[] DIMINUTIONS_ALIGNEMENTS;

	public static final float VALEUR_VICTOIRE;

	static final int VIDE = 0;
	static final int OUVERT = 1;
	static final int FERME = 2;

	static {

		VALEURS_ALIGNEMENTS = new float[LONGUEUR_ALIGNEMENT + 1];

		VALEURS_ALIGNEMENTS[0] = 0;

		float valeurAlignement = VALEUR_ALIGNEMENT_1;
		VALEURS_ALIGNEMENTS[1] = valeurAlignement;

		for (int longueurAlignement = 2; longueurAlignement < LONGUEUR_ALIGNEMENT; longueurAlignement++) {

			valeurAlignement *= COEFFICIENT_AMELIORATION_ALIGNEMENT;
			VALEURS_ALIGNEMENTS[longueurAlignement] = valeurAlignement;
		}

		VALEUR_VICTOIRE = LARGEUR_GRILLE * HAUTEUR_GRILLE * 4 * valeurAlignement;
		VALEURS_ALIGNEMENTS[LONGUEUR_ALIGNEMENT] = VALEUR_VICTOIRE;

		DIMINUTIONS_ALIGNEMENTS = new float[LONGUEUR_ALIGNEMENT + 1];

		for (int longueurAlignement = 1; longueurAlignement <= LONGUEUR_ALIGNEMENT; longueurAlignement++) {

			DIMINUTIONS_ALIGNEMENTS[longueurAlignement] =
					VALEURS_ALIGNEMENTS[longueurAlignement] - VALEURS_ALIGNEMENTS[longueurAlignement - 1];
		}

		AUGMENTATIONS_ALIGNEMENTS = new float[LONGUEUR_ALIGNEMENT];

		for (int longueurAlignement = 1; longueurAlignement < LONGUEUR_ALIGNEMENT; longueurAlignement++) {

			AUGMENTATIONS_ALIGNEMENTS[longueurAlignement] =
					VALEURS_ALIGNEMENTS[longueurAlignement + 1] - VALEURS_ALIGNEMENTS[longueurAlignement];
		}
	}

	final int[] cases;

	int etat;
	int position;
	int couleur;

	/**
	 * Crée un nouvel alignement de cases. Il n'appartient à personne, ne contient aucun jeton et n'est pas fermé.
	 * 
	 * @param cases
	 */
	public Alignement(int[] cases) {

		this.cases = cases;

		etat = VIDE;
		position = 0;
		couleur = COULEUR_INDEFINIE;
	}

	/**
	 * Recharge les attributs précédents de l'alignement suite à l'annulation d'un coup, ne pas appeler cette methode si
	 * le coup joué n'avait pas entraîné de modification sur l'état de l'alignement.
	 * 
	 * @param grille
	 * @param couleur couleur courante
	 */
	public void annuler(GrillePuissance4 grille, int couleur) {

		if (etat == FERME) {

			if (couleur == JAUNE) {
				grille.noteRouge += VALEURS_ALIGNEMENTS[position];
			} else {
				grille.noteJaune += VALEURS_ALIGNEMENTS[position];
			}

			etat = OUVERT;

		} else {

			if (couleur == JAUNE) {
				grille.noteJaune -= DIMINUTIONS_ALIGNEMENTS[position--];
			} else {
				grille.noteRouge -= DIMINUTIONS_ALIGNEMENTS[position--];
			}

			if (position == 0) {
				etat = VIDE;
			}
		}
	}

	/**
	 * @param grille
	 * @param couleur couleur courante
	 * @return {@code true} si la valeur de l'alignement a ete modifiée pour les jaunes ou bien les rouges
	 */
	public boolean jouer(GrillePuissance4 grille, int couleur) {

		boolean ajustementModifie = false;

		if (etat == VIDE) {

			this.couleur = couleur;
			etat = OUVERT;
			position = 1;

			if (couleur == JAUNE) {
				grille.noteJaune += VALEUR_ALIGNEMENT_1;
			} else {
				grille.noteRouge += VALEUR_ALIGNEMENT_1;
			}

			ajustementModifie = true;

		} else if (etat == OUVERT) {

			if (this.couleur == couleur) {

				if (couleur == JAUNE) {
					grille.noteJaune += AUGMENTATIONS_ALIGNEMENTS[position++];
				} else {
					grille.noteRouge += AUGMENTATIONS_ALIGNEMENTS[position++];
				}

			} else {

				etat = FERME;

				if (couleur == JAUNE) {
					grille.noteRouge -= VALEURS_ALIGNEMENTS[position];
				} else {
					grille.noteJaune -= VALEURS_ALIGNEMENTS[position];
				}
			}

			ajustementModifie = true;
		}

		return ajustementModifie;
	}

	/**
	 * @return {@code true} si l'alignement est fermé, c'est-à-dire, s'il n'est plus possible pour aucun des 2 joueurs
	 * de le compléter
	 */
	public boolean isFerme(){
		return etat == FERME;
	}

	/**
	 * @return copie de cet alignement
	 */
	public Alignement copier() {

		Alignement clone = new Alignement(cases);
		clone.couleur = couleur;
		clone.etat = etat;
		clone.position = position;
		return clone;
	}
}
