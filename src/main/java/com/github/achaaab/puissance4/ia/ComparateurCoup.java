package com.github.achaaab.puissance4.ia;

import java.util.Comparator;

/**
 * Ce comparateur permet de trier des coups par ordre DÉCROISSANT de leur valeur,
 * les coups {@code null} sont acceptés et sont considérés comme inférieurs à n'importe quel autre coup
 * (simplement pour qu'ils soient placés en fin de liste à l'issue du tri).
 * 
 * @author Jonathan Guéhenneux
 */
public class ComparateurCoup implements Comparator<Coup> {

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
				comparaison = Float.compare(coup1.valeur, coup0.valeur);
			}
		}

		return comparaison;
	}
}
