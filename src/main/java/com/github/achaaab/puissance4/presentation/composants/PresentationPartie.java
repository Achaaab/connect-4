package com.github.achaaab.puissance4.presentation.composants;

import com.github.achaaab.puissance4.moteur.PartiePuissance4;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Component;

import static javax.swing.Box.createVerticalGlue;
import static javax.swing.BoxLayout.PAGE_AXIS;

/**
 * @author Jonathan Guéhenneux
 */
public class PresentationPartie extends JPanel {

	private final PartiePuissance4 partie;

	private JPanel boutonsPartie;
	private JButton mancheSuivante;
	private Component joueur1;
	private Component joueur2;

	/**
	 * @param partie
	 */
	public PresentationPartie(PartiePuissance4 partie) {

		this.partie = partie;

		setLayout(new BoxLayout(this, PAGE_AXIS));

		creerComposants();
		ajouterComposants();
		ajouterEvenements();
	}

	/**
	 * autorise le lancement de la manche suivante
	 */
	public void autoriserMancheSuivante() {
		mancheSuivante.setEnabled(true);
	}

	/**
	 * 
	 */
	private void creerComposants() {

		boutonsPartie = new JPanel();
		boutonsPartie.setBorder(BorderFactory.createTitledBorder("Partie"));

		mancheSuivante = new JButton("Manche suivante");
		mancheSuivante.setEnabled(false);

		var joueur1Partie = partie.getJoueur1();
		var joueur2Partie = partie.getJoueur2();

		joueur1 = joueur1Partie.getPresentation();
		joueur2 = joueur2Partie.getPresentation();
	}

	/**
	 * 
	 */
	private void ajouterComposants() {

		boutonsPartie.add(mancheSuivante);

		add(boutonsPartie);

		add(joueur1);
		add(joueur2);

		add(createVerticalGlue());
	}

	/**
	 * 
	 */
	private void ajouterEvenements() {
		mancheSuivante.addActionListener(evenement -> mancheSuivante());
	}

	/**
	 * 
	 */
	private void mancheSuivante() {
		new Thread(partie::mancheSuivante).start();
	}

	/**
	 * 
	 */
	public void interdireMancheSuivante() {
		mancheSuivante.setEnabled(false);
	}
}
