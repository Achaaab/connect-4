package com.github.achaaab.puissance4.moteur;

import com.github.achaaab.puissance4.presentation.composants.PresentationJoueur;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class JoueurAbstrait implements Joueur {

	protected int couleur;
	protected String nom;
	protected int score;
	protected boolean partieArretee;

	protected PresentationJoueur presentation;

	/**
	 * @param couleur
	 * @param nom
	 */
	public JoueurAbstrait(int couleur, String nom) {

		this.couleur = couleur;
		this.nom = nom;

		score = 0;
		partieArretee = false;
	}

	@Override
	public int getCouleur() {
		return couleur;
	}

	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public int getScore() {
		return score;
	}

	@Override
	public void setScore(int score) {

		this.score = score;

		presentation.setScore(score);
	}

	@Override
	public void arreterJouer() {
		partieArretee = true;
	}

	@Override
	public void recommencer() {
		partieArretee = false;
	}

	@Override
	public PresentationJoueur getPresentation() {
		return presentation;
	}
}
