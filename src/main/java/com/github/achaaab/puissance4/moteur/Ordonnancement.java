package com.github.achaaab.puissance4.moteur;

import com.github.achaaab.puissance4.presentation.composants.PresentationOrdonnancement;

/**
 * Règle déterminant l'ordre des joueurs à chaque nouvelle manche.
 * 
 * @author Jonathan Guéhenneux
 */
public class Ordonnancement {

	private final PresentationOrdonnancement presentation;

	private boolean joueur1Commence;
	private boolean joueur2Commence;
	private boolean premierJoueurAlterne;
	private boolean premierJoueurAleatoire;

	/**
	 * 
	 */
	public Ordonnancement() {

		joueur1Commence = true;
		joueur2Commence = false;
		premierJoueurAlterne = true;
		premierJoueurAleatoire = false;

		presentation = new PresentationOrdonnancement(this);
	}

	/**
	 * @return the joueur1Commence
	 */
	public boolean isJoueur1Commence() {
		return joueur1Commence;
	}

	/**
	 * @param joueur1Commence joueur1Commence to set
	 */
	public void setJoueur1Commence(boolean joueur1Commence) {
		this.joueur1Commence = joueur1Commence;
	}

	/**
	 * @return the joueur2Commence
	 */
	public boolean isJoueur2Commence() {
		return joueur2Commence;
	}

	/**
	 * @param joueur2Commence the joueur2Commence to set
	 */
	public void setJoueur2Commence(boolean joueur2Commence) {
		this.joueur2Commence = joueur2Commence;
	}

	/**
	 * @return the premierJoueurAlterne
	 */
	public boolean isPremierJoueurAlterne() {
		return premierJoueurAlterne;
	}

	/**
	 * @param premierJoueurAlterne the premierJoueurAlterne to set
	 */
	public void setPremierJoueurAlterne(boolean premierJoueurAlterne) {
		this.premierJoueurAlterne = premierJoueurAlterne;
	}

	/**
	 * @return the premierJoueurAleatoire
	 */
	public boolean isPremierJoueurAleatoire() {
		return premierJoueurAleatoire;
	}

	/**
	 * @param premierJoueurAleatoire the premierJoueurAleatoire to set
	 */
	public void setPremierJoueurAleatoire(boolean premierJoueurAleatoire) {
		this.premierJoueurAleatoire = premierJoueurAleatoire;
	}

	/**
	 * @return the presentation
	 */
	public PresentationOrdonnancement getPresentation() {
		return presentation;
	}

	/**
	 * 
	 */
	public void lire() {

		joueur1Commence = presentation.isJoueur1Commence();
		joueur2Commence = presentation.isJoueur2Commence();
		premierJoueurAleatoire = presentation.isPremierJoueurAleatoire();
		premierJoueurAlterne = presentation.isPremierJoueurAlterne();
	}
}
