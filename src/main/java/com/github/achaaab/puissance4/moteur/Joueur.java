package com.github.achaaab.puissance4.moteur;

import com.github.achaaab.puissance4.presentation.composants.PresentationJoueur;

/**
 * @author Jonathan Guéhenneux
 */
public interface Joueur {

	/**
	 * @return score du joueur
	 */
	int getScore();

	/**
	 * modifie le score du joueur
	 * 
	 * @param score nouveau score du joueur
	 */
	void setScore(int score);

	/**
	 * @return couleur du joueur
	 */
	int getCouleur();

	/**
	 * @return nom du joueur
	 */
	String getNom();

	/**
	 * indique au joueur que c'est à son tour de jouer
	 */
	void demanderJouer();

	/**
	 * indique au joueur que la partie est terminée
	 */
	void arreterJouer();

	/**
	 * indique au joueur que la partie a recommencé
	 */
	void recommencer();

	/**
	 * 
	 * @return
	 */
	PresentationJoueur getPresentation();
}
