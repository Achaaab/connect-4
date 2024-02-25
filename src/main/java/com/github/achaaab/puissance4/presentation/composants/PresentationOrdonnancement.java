package com.github.achaaab.puissance4.presentation.composants;

import com.github.achaaab.puissance4.moteur.Ordonnancement;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import java.awt.GridLayout;

import static javax.swing.BorderFactory.createTitledBorder;

/**
 * @author Jonathan Guéhenneux
 */
public class PresentationOrdonnancement extends JPanel {

	private final Ordonnancement ordonnancement;

	private JCheckBox joueur1Commence;
	private JCheckBox joueur2Commence;
	private JCheckBox premierJoueurAlterne;
	private JCheckBox premierJoueurAleatoire;

	/**
	 * 
	 * @param ordonnancement
	 */
	public PresentationOrdonnancement(Ordonnancement ordonnancement) {

		this.ordonnancement = ordonnancement;

		setBorder(createTitledBorder("Partie"));
		setLayout(new GridLayout(2, 2, 5, 5));

		creerComposants();
		ajouterComposants();
		ajouterEvenements();
	}

	/**
	 * 
	 */
	private void creerComposants() {

		joueur1Commence = new JCheckBox("Joueur 1 commence");
		joueur1Commence.setSelected(ordonnancement.isJoueur1Commence());

		joueur2Commence = new JCheckBox("Joueur 2 commence");
		joueur2Commence.setSelected(ordonnancement.isJoueur2Commence());

		premierJoueurAleatoire = new JCheckBox("Premier joueur aléatoire");
		premierJoueurAleatoire.setSelected(ordonnancement.isPremierJoueurAleatoire());

		premierJoueurAlterne = new JCheckBox("Premier joueur alterné");
		premierJoueurAlterne.setSelected(ordonnancement.isPremierJoueurAlterne());
	}

	/**
	 * 
	 */
	private void ajouterComposants() {

		add(joueur1Commence);
		add(joueur2Commence);
		add(premierJoueurAleatoire);
		add(premierJoueurAlterne);
	}

	/**
	 * 
	 */
	private void ajouterEvenements() {

		joueur1Commence.addActionListener(evenement -> actualiserJoueur1Commence());
		joueur2Commence.addActionListener(evenement -> actualiserJoueur2Commence());
		premierJoueurAleatoire.addActionListener(evenement -> actualiserPremierJoueurAleatoire());
		premierJoueurAlterne.addActionListener(evenement -> actualiserPremierJoueurAlterne());
	}

	/**
	 * 
	 */
	public void actualiserJoueur1Commence() {

		if (joueur1Commence.isSelected()) {

			joueur2Commence.setSelected(false);
			premierJoueurAleatoire.setSelected(false);

		} else {

			joueur2Commence.setSelected(true);
		}
	}

	/**
	 * 
	 */
	public void actualiserJoueur2Commence() {

		if (joueur2Commence.isSelected()) {

			joueur1Commence.setSelected(false);
			premierJoueurAleatoire.setSelected(false);

		} else {

			joueur1Commence.setSelected(true);
		}
	}

	/**
	 * 
	 */
	public void actualiserPremierJoueurAleatoire() {

		if (premierJoueurAleatoire.isSelected()) {

			joueur1Commence.setSelected(false);
			joueur2Commence.setSelected(false);
			premierJoueurAlterne.setSelected(false);

		} else {

			joueur1Commence.setSelected(true);
		}
	}

	/**
	 * 
	 */
	public void actualiserPremierJoueurAlterne() {

		if (premierJoueurAlterne.isSelected()) {

			if (premierJoueurAleatoire.isSelected()) {

				premierJoueurAleatoire.setSelected(false);
				joueur1Commence.setSelected(true);
			}
		}
	}

	/**
	 * @return the joueur1Commence
	 */
	public boolean isJoueur1Commence() {
		return joueur1Commence.isSelected();
	}

	/**
	 * @return the joueur2Commence
	 */
	public boolean isJoueur2Commence() {
		return joueur2Commence.isSelected();
	}

	/**
	 * @return the premierJoueurAlterne
	 */
	public boolean isPremierJoueurAlterne() {
		return premierJoueurAlterne.isSelected();
	}

	/**
	 * @return the premierJoueurAleatoire
	 */
	public boolean isPremierJoueurAleatoire() {
		return premierJoueurAleatoire.isSelected();
	}
}
