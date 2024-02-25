package com.github.achaaab.puissance4.presentation.composants;

import java.awt.BorderLayout;

import com.github.achaaab.puissance4.moteur.Puissance4;
import com.github.achaaab.puissance4.presentation.utilitaire.BarreMenuPuissance4;
import com.github.achaaab.utilitaire.swing.PackablePanel;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.NORTH;
import static com.github.achaaab.puissance4.moteur.Puissance4.GRILLE;

/**
 * @author Jonathan Guéhenneux
 */
public class PresentationPuissance4 extends PackablePanel {

	private final Puissance4 puissance4;

	private BarreMenuPuissance4 barreMenu;
	private PresentationGrille grille;
	private PresentationPartie presentationPartie;

	/**
	 * 
	 */
	public PresentationPuissance4(Puissance4 puissance4) {

		this.puissance4 = puissance4;

		setLayout(new BorderLayout());

		creerComposants();
		ajouterComposants();
	}

	/**
	 * 
	 */
	public void autoriserMancheSuivante() {

		presentationPartie.autoriserMancheSuivante();
		barreMenu.autoriserMancheSuivante();
	}

	/**
	 * 
	 */
	public void interdireMancheSuivante() {

		presentationPartie.interdireMancheSuivante();
		barreMenu.interdireMancheSuivante();
	}

	/**
	 * 
	 */
	private void creerComposants() {

		var nouvellePartie = new PresentationNouvellePartie(puissance4);

		barreMenu = new BarreMenuPuissance4(puissance4, nouvellePartie);
		grille = GRILLE.getPresentation();
	}

	/**
	 * 
	 */
	private void ajouterComposants() {

		add(barreMenu, NORTH);
		add(grille, CENTER);
	}

	/**
	 * 
	 * @param presentationPartie
	 */
	public void ajouterPartie(PresentationPartie presentationPartie) {

		if (this.presentationPartie != null) {
			remove(this.presentationPartie);
		}

		add(presentationPartie, EAST);
		pack();

		this.presentationPartie = presentationPartie;
	}
}
