package com.github.achaaab.puissance4.presentation.composants;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JLabel;

import com.github.achaaab.puissance4.ia.JoueurIa;
import com.github.achaaab.puissance4.ia.NiveauIa;

/**
 * @author Jonathan Guéhenneux
 */
public class PresentationJoueurIa extends PresentationJoueur {

	private final JoueurIa joueur;

	private JLabel labelNiveau;
	private JLabel niveau;

	private JLabel labelDeterministe;
	private JLabel deterministe;

	private JLabel labelVitesse;
	private JLabel vitesse;

	/**
	 * @param joueur
	 */
	public PresentationJoueurIa(JoueurIa joueur) {

		super(joueur);

		this.joueur = joueur;

		creerComposants();
		ajouterComposants();
	}

	/**
	 * 
	 */
	protected void creerComposants() {

		super.creerComposants();

		labelNiveau = new JLabel("Niveau : ");

		NiveauIa niveauJoueur = joueur.getNiveau();

		niveau = new JLabel(niveauJoueur.toString());
		niveau.setForeground(Color.BLUE);

		labelDeterministe = new JLabel("Déterministe : ");

		boolean deterministeJoueur = joueur.isDeterministe();

		deterministe = new JLabel(deterministeJoueur ? "Oui" : "Non");
		deterministe.setForeground(Color.BLUE);

		labelVitesse = new JLabel("Vitesse : ");

		String vitesseJoueur = joueur.getVitesseCalcul();

		vitesse = new JLabel(vitesseJoueur);
		vitesse.setForeground(Color.BLUE);

		labels.setLayout(new GridLayout(5, 1, 0, 5));
		valeurs.setLayout(new GridLayout(5, 1, 0, 5));
	}

	/**
	 * 
	 */
	protected void ajouterComposants() {

		super.ajouterComposants();

		labels.add(labelNiveau);
		labels.add(labelDeterministe);
		labels.add(labelVitesse);

		valeurs.add(niveau);
		valeurs.add(deterministe);
		valeurs.add(vitesse);
	}

	/**
	 * @param vitesseCalcul
	 */
	public void setVitesseCalcul(String vitesseCalcul) {
		vitesse.setText(vitesseCalcul);
	}
}
