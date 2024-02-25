package com.github.achaaab.puissance4.animation;

import com.github.achaaab.puissance4.moteur.GrillePuissance4;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class AnimationGrille extends Thread {

	protected GrillePuissance4 grille;
	protected boolean demandeArret;

	/**
	 * @param grille
	 */
	public AnimationGrille(GrillePuissance4 grille) {

		this.grille = grille;

		demandeArret = false;
	}

	/**
	 *
	 */
	public void arreter() {

		demandeArret = true;
		attendre();
	}

	/**
	 * Attend la fin de l'animation.
	 */
	public void attendre() {

		try {
			join();
		} catch (InterruptedException interruptedException) {
			currentThread().interrupt();
		}
	}

	/**
	 * @return
	 */
	public GrillePuissance4 getGrille() {
		return grille;
	}
}
