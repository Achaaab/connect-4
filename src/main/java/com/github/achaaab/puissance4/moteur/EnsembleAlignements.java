package com.github.achaaab.puissance4.moteur;

import java.util.HashMap;

import static com.github.achaaab.puissance4.moteur.Puissance4.HAUTEUR_GRILLE;
import static com.github.achaaab.puissance4.moteur.Puissance4.LARGEUR_GRILLE;
import static com.github.achaaab.puissance4.moteur.Puissance4.LONGUEUR_ALIGNEMENT;

/**
 * @author Jonathan Guéhenneux
 */
public class EnsembleAlignements {

	private ListeAlignements[][] alignementsListe;

	Alignement[][][] alignements;

	/**
	 * construit l'ensemble des alignements en fonction de la taille de la
	 * grille et de la longueur des alignements
	 */
	public EnsembleAlignements() {
		calculerAlignements();
	}

	/**
	 * Elimine definitivement de la liste tous les alignements fermes, a savoir
	 * tous les alignements dans lesquels les 2 joueurs ont joues au moins un
	 * jeton.
	 */
	public void eliminerAlignementsFermes() {

		int colonne;
		int ligne;

		ListeAlignements alignementsCase;

		for (colonne = 0; colonne < LARGEUR_GRILLE; colonne++) {

			for (ligne = 0; ligne < HAUTEUR_GRILLE; ligne++) {

				alignementsCase = alignementsListe[colonne][ligne];
				alignementsCase.eliminerAlignementsFermes();
				alignements[colonne][ligne] = alignementsCase.toArray();
			}
		}
	}

	/**
	 * Pré-calcule la liste de tous les alignements possibles dans la grille.
	 */
	private void calculerAlignements() {

		alignements = new Alignement[LARGEUR_GRILLE][HAUTEUR_GRILLE][];

		/*
		 * dans un premier temps on travaille avec des listes afin de faciliter
		 * l'insertion d'alignements
		 */

		alignementsListe = new ListeAlignements[LARGEUR_GRILLE][HAUTEUR_GRILLE];

		int colonnePremiereCase;
		int lignePremiereCase;

		for (colonnePremiereCase = 0; colonnePremiereCase < LARGEUR_GRILLE; colonnePremiereCase++) {

			for (lignePremiereCase = 0; lignePremiereCase < HAUTEUR_GRILLE; lignePremiereCase++) {
				alignementsListe[colonnePremiereCase][lignePremiereCase] = new ListeAlignements();
			}
		}

		for (colonnePremiereCase = 0; colonnePremiereCase < LARGEUR_GRILLE; colonnePremiereCase++) {

			for (lignePremiereCase = 0; lignePremiereCase < HAUTEUR_GRILLE; lignePremiereCase++) {

				ajouterAlignements(colonnePremiereCase, lignePremiereCase, alignementsListe);
			}
		}

		// enfin on convertit les listes en tableaux (parcourt plus rapide)

		int colonne;
		int ligne;

		ListeAlignements alignementsCase;

		for (colonne = 0; colonne < LARGEUR_GRILLE; colonne++) {

			for (ligne = 0; ligne < HAUTEUR_GRILLE; ligne++) {

				alignementsCase = alignementsListe[colonne][ligne];
				alignements[colonne][ligne] = alignementsCase.toArray();
			}
		}
	}

	/**
	 * @return
	 */
	public Alignement[][][] clonerAlignements() {

		var alignementsClones = new Alignement[LARGEUR_GRILLE][HAUTEUR_GRILLE][];

		int colonne;
		int ligne;
		int indexAlignement;

		Alignement[] alignementsCase;
		int nombreAlignementsCase;

		Alignement[] alignementsCaseClones;
		Alignement alignementCase;
		Alignement alignementCaseClone;

		var clones = new HashMap<Alignement, Alignement>();

		for (colonne = 0; colonne < LARGEUR_GRILLE; colonne++) {

			for (ligne = 0; ligne < HAUTEUR_GRILLE; ligne++) {

				alignementsCase = alignements[colonne][ligne];
				nombreAlignementsCase = alignementsCase.length;
				alignementsCaseClones = new Alignement[nombreAlignementsCase];
				alignementsClones[colonne][ligne] = alignementsCaseClones;

				for (indexAlignement = 0; indexAlignement < nombreAlignementsCase; indexAlignement++) {

					alignementCase = alignementsCase[indexAlignement];

					alignementCaseClone = clones.get(alignementCase);

					if (alignementCaseClone == null) {

						alignementCaseClone = alignementCase.copier();
						clones.put(alignementCase, alignementCaseClone);
					}

					alignementsCaseClones[indexAlignement] = alignementCaseClone;
				}
			}
		}

		return alignementsClones;
	}

	/**
	 * Ajoute les alignements possibles partant de la case specifiée par sa colonne et sa ligne.
	 * 
	 * @param colonnePremiereCase
	 * @param lignePremiereCase
	 * @param alignements
	 */
	private void ajouterAlignements(int colonnePremiereCase, int lignePremiereCase, ListeAlignements[][] alignements) {

		int[] casesAlignement;
		int indexCase;
		int colonne;
		int ligne;

		/*-
		 *
		 *
		 *
		 *       O o o o
		 *
		 *
		 *
		 */

		if (colonnePremiereCase + LONGUEUR_ALIGNEMENT <= LARGEUR_GRILLE) {

			casesAlignement = new int[LONGUEUR_ALIGNEMENT * 2];

			for (indexCase = 0; indexCase < LONGUEUR_ALIGNEMENT; indexCase++) {

				colonne = colonnePremiereCase + indexCase;
				ligne = lignePremiereCase;

				casesAlignement[indexCase] = colonne;
				casesAlignement[indexCase + LONGUEUR_ALIGNEMENT] = ligne;
			}

			ajouterAlignement(casesAlignement, alignements);
		}

		/*-
		 *       o
		 *       o
		 *       o
		 *       O
		 *
		 *
		 *
		 */

		if (lignePremiereCase + LONGUEUR_ALIGNEMENT <= HAUTEUR_GRILLE) {

			casesAlignement = new int[LONGUEUR_ALIGNEMENT * 2];

			for (indexCase = 0; indexCase < LONGUEUR_ALIGNEMENT; indexCase++) {

				colonne = colonnePremiereCase;
				ligne = lignePremiereCase + indexCase;

				casesAlignement[indexCase] = colonne;
				casesAlignement[indexCase + LONGUEUR_ALIGNEMENT] = ligne;
			}

			ajouterAlignement(casesAlignement, alignements);
		}

		/*-
		 *             o
		 *           o
		 *         o
		 *       O
		 *          
		 *
		 *
		 */

		if (colonnePremiereCase + LONGUEUR_ALIGNEMENT <= LARGEUR_GRILLE
				&& lignePremiereCase + LONGUEUR_ALIGNEMENT <= HAUTEUR_GRILLE) {

			casesAlignement = new int[LONGUEUR_ALIGNEMENT * 2];

			for (indexCase = 0; indexCase < LONGUEUR_ALIGNEMENT; indexCase++) {

				colonne = colonnePremiereCase + indexCase;
				ligne = lignePremiereCase + indexCase;

				casesAlignement[indexCase] = colonne;
				casesAlignement[indexCase + LONGUEUR_ALIGNEMENT] = ligne;
			}

			ajouterAlignement(casesAlignement, alignements);
		}

		/*-
		 *
		 *
		 *
		 *       O
		 *         o
		 *           o
		 *             o
		 */

		if (colonnePremiereCase + LONGUEUR_ALIGNEMENT <= LARGEUR_GRILLE
				&& lignePremiereCase - LONGUEUR_ALIGNEMENT + 1 >= 0) {

			casesAlignement = new int[LONGUEUR_ALIGNEMENT * 2];

			for (indexCase = 0; indexCase < LONGUEUR_ALIGNEMENT; indexCase++) {

				colonne = colonnePremiereCase + indexCase;
				ligne = lignePremiereCase - indexCase;

				casesAlignement[indexCase] = colonne;
				casesAlignement[indexCase + LONGUEUR_ALIGNEMENT] = ligne;
			}

			ajouterAlignement(casesAlignement, alignements);
		}
	}

	/**
	 * Associe un alignement à toutes les cases de l'alignement.
	 * 
	 * @param casesAlignement
	 */
	private void ajouterAlignement(int[] casesAlignement, ListeAlignements[][] alignements) {

		int colonne;
		int ligne;

		var alignement = new Alignement(casesAlignement);

		for (int indexCase = 0; indexCase < LONGUEUR_ALIGNEMENT; indexCase++) {

			colonne = casesAlignement[indexCase];
			ligne = casesAlignement[indexCase + LONGUEUR_ALIGNEMENT];
			alignements[colonne][ligne].ajouterAlignement(alignement);
		}
	}
}
