package com.github.achaaab.puissance4.moteur;

import java.util.Random;

import com.github.achaaab.puissance4.presentation.composants.PresentationPartie;

import static com.github.achaaab.puissance4.moteur.Puissance4.GRILLE;

/**
 * @author Jonathan Guéhenneux
 */
public class PartiePuissance4 {

	private static final Random RANDOM = new Random();

	private final Puissance4 puissance4;
	private final PresentationPartie presentation;

	private Joueur joueur1;
	private Joueur joueur2;
	private ManchePuissance4 mancheCourante;
	private int nombreManches;

	private boolean joueur1Commence;
	private boolean joueur2Commence;
	private boolean premierJoueurAleatoire;
	private boolean premierJoueurAlterne;

	/**
	 * @param puissance4
	 * @param joueur1
	 * @param joueur2
	 */
	public PartiePuissance4(Puissance4 puissance4, Joueur joueur1, Joueur joueur2) {

		this.puissance4 = puissance4;
		this.joueur1 = joueur1;
		this.joueur2 = joueur2;

		nombreManches = 0;

		presentation = new PresentationPartie(this);
	}

	/**
	 * lance la manche suivante
	 */
	public void mancheSuivante() {

		if (mancheCourante != null) {

			GRILLE.arreterAnimations();
			puissance4.interdireMancheSuivante();
		}

		boolean joueur1CommenceManche;

		if (premierJoueurAlterne) {
			joueur1CommenceManche = nombreManches % 2 == 0 ? joueur1Commence : joueur2Commence;
		} else if (premierJoueurAleatoire) {
			joueur1CommenceManche = RANDOM.nextBoolean();
		} else {
			joueur1CommenceManche = joueur1Commence;
		}

		mancheCourante = new ManchePuissance4(joueur1, joueur2, joueur1CommenceManche);
		mancheCourante.commencer();
		nombreManches++;
		puissance4.autoriserMancheSuivante();
	}

	/**
	 * force l'arrêt de la partie
	 */
	public void arreter() {
		mancheCourante.arreter();
	}

	/**
	 * @return joueur1
	 */
	public Joueur getJoueur1() {
		return joueur1;
	}

	/**
	 * @param joueur1 joueur1 à définir
	 */
	public void setJoueur1(Joueur joueur1) {
		this.joueur1 = joueur1;
	}

	/**
	 * @return joueur2
	 */
	public Joueur getJoueur2() {
		return joueur2;
	}

	/**
	 * @param joueur2 joueur2 à définir
	 */
	public void setJoueur2(Joueur joueur2) {
		this.joueur2 = joueur2;
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
	 * @return the presentation
	 */
	public PresentationPartie getPresentation() {
		return presentation;
	}
}
