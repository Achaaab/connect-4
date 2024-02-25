package com.github.achaaab.puissance4.ia;

/**
 * Ce comparateur de coups ajoute le déterminisme au tri.
 * 
 * @author Jonathan Guéhenneux
 */
public class ComparateurCoupDeterministe extends ComparateurCoup {

	@Override
	public int compare(Coup coup0, Coup coup1) {

		int comparaison;

		if (coup0 == null) {

			if (coup1 == null) {
				comparaison = 0;
			} else {
				comparaison = 1;
			}

		} else {

			if (coup1 == null) {

				comparaison = -1;

			} else {

				if (coup0.valeur < coup1.valeur) {

					comparaison = 1;

				} else if (coup0.valeur == coup1.valeur) {

					// comportement deterministe : la colonne la plus à gauche est considérée comme supérieure
					comparaison = coup0.colonne - coup1.colonne;

				} else {

					comparaison = -1;
				}
			}
		}

		return comparaison;
	}
}
