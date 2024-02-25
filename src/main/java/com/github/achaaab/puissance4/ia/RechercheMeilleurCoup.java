package com.github.achaaab.puissance4.ia;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.github.achaaab.multithreading.Tache;

import static java.lang.Float.NEGATIVE_INFINITY;
import static com.github.achaaab.puissance4.moteur.Puissance4.GRILLE;
import static com.github.achaaab.puissance4.moteur.Puissance4.LARGEUR_GRILLE;
import static com.github.achaaab.utilitaire.ListeUtilitaire.choisirElementAleatoirement;

/**
 * @author Jonathan Guéhenneux
 */
public class RechercheMeilleurCoup implements Tache<EvaluationColonne> {

	private final List<EvaluationColonne> evaluationsColonne;
	private final Iterator<EvaluationColonne> iterateurColonnes;
	private final boolean deterministe;

	/**
	 * @param deterministe Indique si la recherche de la meilleure colonne doit se faire de facon deterministe.
	 * Si oui, la colonne la plus a gauche sera toujours choisie en cas d'egalite, sinon la colonne sera choisie au
	 * hasard parmi les colonnes donnant la meilleure evaluation.
	 */
	public RechercheMeilleurCoup(boolean deterministe) {

		this.deterministe = deterministe;

		evaluationsColonne = new ArrayList<>();

		EvaluationColonne evaluationColonne;

		for (var colonne = 0; colonne < LARGEUR_GRILLE; colonne++) {

			if (!GRILLE.colonneComplete(colonne)) {

				evaluationColonne = new EvaluationColonne(colonne);
				evaluationsColonne.add(evaluationColonne);
			}
		}

		iterateurColonnes = evaluationsColonne.iterator();
	}

	@Override
	public synchronized EvaluationColonne getTachePartielle() {
		return iterateurColonnes.next();
	}

	@Override
	public boolean isComplete() {
		return !iterateurColonnes.hasNext();
	}

	/**
	 * @return une des colonnes qui ont reçu la meilleure évaluation
	 */
	public int getMeilleureColonne() {

		var meilleuresColonnes = new ArrayList<Integer>();
		var meilleureValeur = NEGATIVE_INFINITY;

		int colonne;
		float valeur;

		for (var evaluationColonne : evaluationsColonne) {

			if (evaluationColonne == null) {
				continue;
			}

			colonne = evaluationColonne.getColonne();
			valeur = evaluationColonne.getValeur();

			if (valeur > meilleureValeur) {

				meilleuresColonnes.clear();
				meilleuresColonnes.add(colonne);
				meilleureValeur = valeur;

			} else if (valeur == meilleureValeur) {

				meilleuresColonnes.add(colonne);
			}
		}

		return deterministe ?
				meilleuresColonnes.getFirst() :
				choisirElementAleatoirement(meilleuresColonnes);
	}

	@Override
	public int getNombreTachePartielles() {
		return evaluationsColonne.size();
	}
}