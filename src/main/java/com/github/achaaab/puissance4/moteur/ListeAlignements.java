package com.github.achaaab.puissance4.moteur;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonathan Guéhenneux
 */
public class ListeAlignements {

	List<Alignement> alignements;

	/**
	 *
	 */
	public ListeAlignements() {
		alignements = new ArrayList<>();
	}

	/**
	 * @param alignement
	 */
	public void ajouterAlignement(Alignement alignement) {
		alignements.add(alignement);
	}

	/**
	 *
	 */
	public void eliminerAlignementsFermes() {
		alignements.removeIf(Alignement::isFerme);
	}

	/**
	 * @return
	 */
	public Alignement[] toArray() {

		var tableauAlignements = new Alignement[alignements.size()];
		return alignements.toArray(tableauAlignements);
	}
}
