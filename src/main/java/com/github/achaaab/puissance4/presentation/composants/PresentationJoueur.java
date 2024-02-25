package com.github.achaaab.puissance4.presentation.composants;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.github.achaaab.puissance4.moteur.Joueur;
import com.github.achaaab.puissance4.presentation.utilitaire.Couleur;

import static java.awt.Color.BLUE;
import static javax.swing.BorderFactory.createTitledBorder;
import static javax.swing.BoxLayout.LINE_AXIS;
import static javax.swing.SwingConstants.LEFT;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class PresentationJoueur extends JPanel {

	private final Joueur joueur;

	protected JPanel labels;
	protected JPanel valeurs;

	protected JLabel labelCouleur;
	protected JLabel couleur;

	protected JLabel labelScore;
	protected JLabel score;

	/**
	 * @param joueur
	 */
	public PresentationJoueur(Joueur joueur) {

		this.joueur = joueur;

		String nom = joueur.getNom();

		setBorder(createTitledBorder(nom));
		setLayout(new BoxLayout(this, LINE_AXIS));
	}

	/**
	 * 
	 */
	protected void creerComposants() {

		labels = new JPanel();
		valeurs = new JPanel();

		labelCouleur = new JLabel("Couleur : ");

		Couleur couleurJoueur = Couleur.getCouleur(joueur.getCouleur());

		couleur = new JLabel(couleurJoueur.getIcone());
		couleur.setHorizontalAlignment(LEFT);

		labelScore = new JLabel("Score : ");

		int scoreJoueur = joueur.getScore();

		score = new JLabel(Integer.toString(scoreJoueur));
		score.setForeground(BLUE);
	}

	/**
	 * 
	 */
	protected void ajouterComposants() {

		labels.add(labelCouleur);
		labels.add(labelScore);

		valeurs.add(couleur);
		valeurs.add(score);

		add(labels);
		add(valeurs);
		add(Box.createHorizontalGlue());
	}

	/**
	 * @param score
	 */
	public void setScore(int score) {
		this.score.setText(Integer.toString(score));
	}
}
