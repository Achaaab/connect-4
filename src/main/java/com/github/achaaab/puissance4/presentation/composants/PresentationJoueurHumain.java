package com.github.achaaab.puissance4.presentation.composants;

import java.awt.GridLayout;

import javax.swing.JPanel;

import com.github.achaaab.puissance4.moteur.JoueurHumain;

/**
 * @author Jonathan Guéhenneux
 */
public class PresentationJoueurHumain extends PresentationJoueur {

	/**
	 * @param joueur
	 */
	public PresentationJoueurHumain(JoueurHumain joueur) {

		super(joueur);

		creerComposants();
		ajouterComposants();
	}

	/**
	 * 
	 */
	protected void creerComposants() {

		super.creerComposants();

		labels.setLayout(new GridLayout(2, 1, 0, 5));

		valeurs = new JPanel();
		valeurs.setLayout(new GridLayout(2, 1, 0, 5));
	}
}
