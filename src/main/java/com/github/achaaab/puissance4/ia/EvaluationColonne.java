package com.github.achaaab.puissance4.ia;

import com.github.achaaab.multithreading.TachePartielle;

/**
 * @author Jonathan Guéhenneux
 */
public class EvaluationColonne implements TachePartielle {

	private final int colonne;
	private float valeur;

	/**
	 * @param colonne colonne à evaluer
	 */
	public EvaluationColonne(int colonne) {
		this.colonne = colonne;
	}

	/**
	 * @return colonne à evaluer
	 */
	public int getColonne() {
		return colonne;
	}

	/**
	 * @return valeur de la colonne évaluée
	 */
	public float getValeur() {
		return valeur;
	}

	/**
	 * @param valeur valeur de la colonne évaluée
	 */
	public void setValeur(float valeur) {
		this.valeur = valeur;
	}
}
