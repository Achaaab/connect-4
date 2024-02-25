package com.github.achaaab.puissance4.ia;

import com.github.achaaab.puissance4.moteur.GrillePuissance4;

import static java.lang.Float.NEGATIVE_INFINITY;
import static java.lang.Float.POSITIVE_INFINITY;
import static java.util.Arrays.sort;
import static com.github.achaaab.puissance4.moteur.Alignement.VALEUR_VICTOIRE;
import static com.github.achaaab.puissance4.moteur.GrillePuissance4.JAUNE;
import static com.github.achaaab.puissance4.moteur.Puissance4.HAUTEUR_GRILLE;
import static com.github.achaaab.puissance4.moteur.Puissance4.LARGEUR_GRILLE;

/**
 * @author Jonathan Guéhenneux
 */
public class ProcessusEvaluationColonne extends Thread {

	private final EvaluationColonne evaluationColonne;
	private final GrillePuissance4 grille;
	private final int profondeurMaximum;
	private final ComparateurCoup comparateurCoup;

	private long nombrePositionsEvaluees;

	/**
	 * @param evaluationColonne
	 * @param grille
	 * @param profondeurMaximum
	 * @param deterministe
	 */
	public ProcessusEvaluationColonne(EvaluationColonne evaluationColonne, GrillePuissance4 grille,
			int profondeurMaximum, boolean deterministe) {

		this.evaluationColonne = evaluationColonne;
		this.grille = grille;
		this.profondeurMaximum = profondeurMaximum;

		nombrePositionsEvaluees = 0;

		if (deterministe) {
			comparateurCoup = new ComparateurCoupDeterministe();
		} else {
			comparateurCoup = new ComparateurCoup();
		}
	}

	@Override
	public void run() {

		var colonneEvaluee = evaluationColonne.getColonne();
		grille.jouer(colonneEvaluee);
		var valeurColonne = -negaMax(profondeurMaximum, NEGATIVE_INFINITY, POSITIVE_INFINITY);
		evaluationColonne.setValeur(valeurColonne);
	}

	/**
	 * @param profondeur nombre de coups à explorer plus bas dans l'arbre
	 * @param alpha valeur minimale recherchée
	 * @param beta valeur maximale recherchée
	 * @return valeur de la position pour le joueur courant
	 */
	private float negaMax(int profondeur, float alpha, float beta) {

		nombrePositionsEvaluees++;

		float negaMax;

		if (grille.nombreJetonsJoues == grille.nombreCases) {

			// match nul
			negaMax = 0;

		} else {

			float note;
			float noteAdversaire;

			var jaune = grille.couleurCourante == JAUNE;

			if (jaune) {

				note = grille.noteJaune;
				noteAdversaire = grille.noteRouge;

			} else {

				note = grille.noteRouge;
				noteAdversaire = grille.noteJaune;
			}

			// test des 2 situations finales non nulles

			if (noteAdversaire >= VALEUR_VICTOIRE) {

				// victoire de l'adversaire
				negaMax = -VALEUR_VICTOIRE - profondeur;

			} else if (profondeur == 0) {

				// limite d'exploration de l'arbre atteinte (on evalue grossièrement la position du joueur courant)
				negaMax = note - noteAdversaire;

			} else {

				// nœud de l'arbre, position non finale
				negaMax = NEGATIVE_INFINITY;

				int colonne;
				float negaMaxColonne;
				boolean[] alignementsModifies;

				/*
				 * L'élagage alpha-beta fonctionne mieux si les coups sont joués dans l'ordre décroissant
				 * de leur valeur probable.
				 * 
				 * Jusqu'à une certaine profondeur, il est rentable de trier les coups en faisant une estimation très
				 * grossière de leur valeur.
				 */

				if (profondeur > 3) {

					/*
					 * Utiliser un tableau est un peu gênant car on ne connait pas à l'avance le nombre de coups
					 * jouables, cependant cela permet un léger gain par rapport à une ArrayList
					 * (pour le tri notamment).
					 */

					var coupsPossibles = new Coup[LARGEUR_GRILLE];

					for (colonne = 0; colonne < LARGEUR_GRILLE; colonne++) {

						// test pour vérifier que la colonne visée n'est pas complète

						if (grille.hauteurs[colonne] < HAUTEUR_GRILLE) {

							// on joue le coup...
							alignementsModifies = grille.jouer(colonne);

							/*
							 * ... et on évalue la situation sans descendre plus bas dans l'arbre, ça nous donne une valeur
							 * approximative du coup qui est suffisante pour que l'élagage alpha-beta soit presque
							 * optimal
							 */

							if (jaune) {

								if (grille.noteJaune >= VALEUR_VICTOIRE) {
									negaMaxColonne = VALEUR_VICTOIRE;
								} else {
									negaMaxColonne = grille.noteJaune - grille.noteRouge;
								}

							} else {

								if (grille.noteRouge >= VALEUR_VICTOIRE) {
									negaMaxColonne = VALEUR_VICTOIRE;
								} else {
									negaMaxColonne = grille.noteRouge - grille.noteJaune;
								}
							}

							// on annule le coup avant de l'enregistrer
							grille.annuler(colonne, alignementsModifies);

							coupsPossibles[colonne] = new Coup(colonne, negaMaxColonne);
						}
					}

					// on trie les coups selon leur valeur
					sort(coupsPossibles, comparateurCoup);

					/*
					 * on joue les coups par ordre décroissant de leur valeur estimée, ainsi l'élagage alpha-beta
					 * permettra d'éliminer plus de coups
					 */

					Coup coup;

					for (var indexCoup = 0; indexCoup < LARGEUR_GRILLE; indexCoup++) {

						coup = coupsPossibles[indexCoup];

						if (coup == null) {
							break;
						}

						colonne = coup.colonne;

						alignementsModifies = grille.jouer(colonne);
						negaMaxColonne = -negaMax(profondeur - 1, -beta, -alpha);
						grille.annuler(colonne, alignementsModifies);

						if (negaMaxColonne > negaMax) {

							negaMax = negaMaxColonne;

							// élagage alpha-beta

							if (negaMax > alpha) {

								alpha = negaMax;

								if (alpha >= beta) {
									return negaMax;
								}
							}
						}
					}

				} else {

					/*
					 * Profondeur faible, inutile de trier les coups (la limite de 3 a été évaluée avec une grille de 7
					 * colonnes). Le gain apporté ne compenserait pas le coup de l'estimation et du tri. On joue donc
					 * les coups dans un ordre arbitraire.
					 */

					for (colonne = 0; colonne < LARGEUR_GRILLE; colonne++) {

						if (grille.hauteurs[colonne] < HAUTEUR_GRILLE) {

							alignementsModifies = grille.jouer(colonne);
							negaMaxColonne = -negaMax(profondeur - 1, -beta, -alpha);
							grille.annuler(colonne, alignementsModifies);

							if (negaMaxColonne > negaMax) {

								negaMax = negaMaxColonne;

								// élagage alpha-beta

								if (negaMax > alpha) {

									alpha = negaMax;

									if (alpha >= beta) {
										return negaMax;
									}
								}
							}
						}
					}
				}
			}
		}

		return negaMax;
	}

	/**
	 * @return nombre de positions évaluées
	 */
	public long getNombrePositionsEvaluees() {
		return nombrePositionsEvaluees;
	}
}
