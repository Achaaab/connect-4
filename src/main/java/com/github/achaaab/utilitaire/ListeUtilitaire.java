package com.github.achaaab.utilitaire;

import java.util.List;
import java.util.Random;

/**
 * @author Jonathan Guéhenneux
 */
public class ListeUtilitaire {

	/**
	 * générateur pseudo-aléatoire
	 */
	private static final Random RANDOM = new Random();

	/**
	 * @param liste
	 * @param tailleMaximale
	 */
	public static List<?> tronquer(List<?> liste, int tailleMaximale) {

		List<?> listeTronquee;

		int tailleListe = liste.size();

		if (tailleListe > tailleMaximale) {
			listeTronquee = liste.subList(0, tailleMaximale);
		} else {
			listeTronquee = liste;
		}

		return listeTronquee;
	}

	/**
	 * @param liste une liste non vide
	 * @return un élément choisi arbitrairement dans la liste
	 */
	public static <E> E choisirElementArbitrairement(List<E> liste) {
		return liste.getFirst();
	}

	/**
	 * 
	 * @param liste
	 * @return
	 */
	public static <E> E choisirElementAleatoirement(List<E> liste) {

		var tailleListe = liste.size();
		var indexElement = RANDOM.nextInt(tailleListe);
		return liste.get(indexElement);
	}
}
