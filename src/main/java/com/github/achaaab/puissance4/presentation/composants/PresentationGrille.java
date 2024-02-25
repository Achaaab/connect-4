package com.github.achaaab.puissance4.presentation.composants;

import com.github.achaaab.puissance4.moteur.GrillePuissance4;
import com.github.achaaab.puissance4.presentation.ecouteurs.EcouteurDeplacementJeton;
import com.github.achaaab.utilitaire.swing.Dessin;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import static java.awt.AlphaComposite.Clear;
import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.lang.Math.round;
import static com.github.achaaab.puissance4.moteur.GrillePuissance4.COULEUR_INDEFINIE;
import static com.github.achaaab.puissance4.moteur.GrillePuissance4.JAUNE;
import static com.github.achaaab.puissance4.moteur.GrillePuissance4.ROUGE;
import static com.github.achaaab.puissance4.moteur.Puissance4.GRILLE;
import static com.github.achaaab.puissance4.moteur.Puissance4.HAUTEUR_GRILLE;
import static com.github.achaaab.puissance4.moteur.Puissance4.LARGEUR_GRILLE;
import static com.github.achaaab.puissance4.presentation.utilitaire.SwingUtility.scale;

/**
 * @author Jonathan Guéhenneux
 */
public class PresentationGrille extends Dessin {

	private static final Map<Integer, Color> COULEURS;
	private static final Color COULEUR_JAUNE = Color.YELLOW;
	private static final Color COULEUR_ROUGE = Color.RED;
	private static final Color COULEUR_GRILLE_1 = new Color(0, 0, 255);
	private static final Color COULEUR_GRILLE_2 = new Color(0, 0, 64);

	static {

		COULEURS = new HashMap<>();

		COULEURS.put(JAUNE, COULEUR_JAUNE);
		COULEURS.put(ROUGE, COULEUR_ROUGE);
	}

	private final GrillePuissance4 grille;

	private int largeurColonne;
	private int hauteurLigne;

	private int margeGauche;

	private int largeurJeton;
	private int hauteurJeton;

	private int margeJetonGauche;
	private int margeJetonHaut;

	private BufferedImage imageGrille;

	private final EcouteurDeplacementJeton ecouteurDeplacementJeton;

	/**
	 * @param grille
	 */
	public PresentationGrille(GrillePuissance4 grille) {

		this.grille = grille;

		ecouteurDeplacementJeton = new EcouteurDeplacementJeton(grille, this);
		setPreferredSize(new Dimension(scale(315), scale(315)));
	}

	/**
	 * donne le controle du jeton au dessus de la grille
	 */
	public void donnerControleJeton() {

		addMouseMotionListener(ecouteurDeplacementJeton);
		addMouseListener(ecouteurDeplacementJeton);

		Point positionPointeur = getMousePosition();

		int x;

		if (positionPointeur == null) {
			x = 0;
		} else {
			x = positionPointeur.x;
		}

		grille.colonneVisee = getColonne(x);

		recalculerImageAsynchrone();

	}

	/**
	 * retire le controle du jeton au dessus de la grille
	 */
	public void retirerControleJeton() {

		removeMouseMotionListener(ecouteurDeplacementJeton);
		removeMouseListener(ecouteurDeplacementJeton);

	}

	/**
	 * @param x coordonnée d'un pixel du panneau
	 * @return la colonne correspondante
	 */
	public int getColonne(int x) {

		int colonne;

		if (largeurColonne == 0) {

			colonne = 0;

		} else {

			colonne = (x - margeGauche) / largeurColonne;

			if (colonne < 0) {
				colonne = 0;
			}

			if (colonne >= LARGEUR_GRILLE) {
				colonne = LARGEUR_GRILLE - 1;
			}
		}

		return colonne;
	}

	@Override
	public void dessiner() {

		setAnticrenelage(true);

		var largeurPanel = getWidth();
		var hauteurPanel = getHeight();

		// on divise l'espace en colonnes
		largeurColonne = round(largeurPanel * 0.9f) / LARGEUR_GRILLE;

		// on divise l'espace en lignes + 1 ligne pour le jeton à introduire
		hauteurLigne = round(hauteurPanel * 0.9f) / (HAUTEUR_GRILLE + 1);

		// on en déduit les dimensions de la grille

		var largeurGrille = LARGEUR_GRILLE * largeurColonne;
		var hauteurGrille = HAUTEUR_GRILLE * hauteurLigne;

		/*
		 * on rajoute une marge en haut et à gauche pour centrer la grille
		 * (en tenant compte de la ligne pour le jeton à introduire)
		 */

		margeGauche = (largeurPanel - largeurGrille) / 2;
		var margeHaut = (hauteurPanel - hauteurGrille - hauteurLigne) / 2;

		/*
		 * on deduit les dimensions des jetons (ou des cases vides) en fonction
		 * de la largeur et de la hauteur des cases
		 */

		largeurJeton = largeurColonne * 4 / 5;
		hauteurJeton = hauteurLigne * 4 / 5;

		/*
		 * on rajoute une marge pour centrer le jeton dans sa case
		 */

		margeJetonGauche = (largeurColonne - largeurJeton) / 2;
		margeJetonHaut = (hauteurLigne - hauteurJeton) / 2;

		// on dessine les jetons et les cases vides

		int valeur;
		Color couleur;

		// coordonnées de la case

		int caseX;
		int caseY;

		// coordonnées du jeton

		int jetonX;
		int jetonY;

		Point coordonneesJeton;
		float angleJeton;

		for (int colonne = 0; colonne < LARGEUR_GRILLE; colonne++) {

			caseX = margeGauche + colonne * largeurColonne;
			jetonX = caseX + margeJetonGauche;

			for (int ligne = 0; ligne < HAUTEUR_GRILLE; ligne++) {

				caseY = margeHaut + (HAUTEUR_GRILLE - ligne) * hauteurLigne;
				jetonY = caseY + margeJetonHaut;

				valeur = grille.getCase(colonne, ligne);

				if (valeur != COULEUR_INDEFINIE) {

					couleur = COULEURS.get(valeur);
					coordonneesJeton = new Point(colonne, ligne);

					angleJeton = GRILLE.getAngleJeton(coordonneesJeton);

					dessinerJeton(graphique, jetonX, jetonY, couleur, angleJeton);
				}
			}
		}

		// on dessine le jeton en train d'être joué

		if (grille.jetonPris) {

			couleur = COULEURS.get(grille.couleurCourante);

			caseX = margeGauche + grille.colonneVisee * largeurColonne;
			jetonX = caseX + margeJetonGauche;

			caseY = margeHaut + round(grille.hauteurJetonJoue * hauteurLigne);
			jetonY = caseY + margeJetonHaut;

			dessinerJeton(graphique, jetonX, jetonY, couleur, 0);
		}

		// on dessine la grille

		if (largeurGrille > 0 && hauteurGrille > 0) {

			dessinerGrille(largeurGrille, hauteurGrille);
			graphique.drawImage(imageGrille, margeGauche, margeHaut + hauteurLigne, null);
		}
	}

	/**
	 * 
	 * @param largeurGrille
	 * @param hauteurGrille
	 * @return
	 */
	private void dessinerGrille(int largeurGrille, int hauteurGrille) {

		if (imageGrille == null ||
				largeurGrille != imageGrille.getWidth() || hauteurGrille != imageGrille.getHeight()) {

			imageGrille = new BufferedImage(largeurGrille, hauteurGrille, TYPE_INT_ARGB);

			Graphics2D graphiqueGrille = imageGrille.createGraphics();

			graphiqueGrille.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);

			graphiqueGrille.setPaint(new GradientPaint(
					new Point(0, 0), COULEUR_GRILLE_1,
					new Point(largeurGrille, hauteurGrille), COULEUR_GRILLE_2));

			graphiqueGrille.fillRoundRect(0, 0, largeurGrille, hauteurGrille,
					largeurGrille / 10, hauteurGrille / 10);

			// coordonnées de la case

			int caseX;
			int caseY;

			// coordonnées du jeton

			int jetonX;
			int jetonY;

			for (int colonne = 0; colonne < LARGEUR_GRILLE; colonne++) {

				caseX = colonne * largeurColonne;
				jetonX = caseX + margeJetonGauche;

				for (int ligne = 0; ligne < HAUTEUR_GRILLE; ligne++) {

					caseY = ligne * hauteurLigne;
					jetonY = caseY + margeJetonHaut;

					creuserCase(graphiqueGrille, jetonX, jetonY, largeurJeton, hauteurJeton);
				}
			}
		}
	}

	/**
	 * @param graphique
	 * @param jetonX
	 * @param jetonY
	 * @param largeurJeton
	 * @param hauteurJeton
	 */
	private void creuserCase(Graphics2D graphique, int jetonX, int jetonY, int largeurJeton, int hauteurJeton) {

		graphique.setComposite(Clear);
		graphique.fillOval(jetonX, jetonY, largeurJeton, hauteurJeton);
	}

	/**
	 * 
	 * @param graphique
	 * @param jetonX
	 * @param jetonY
	 * @param couleur
	 * @param angle
	 */
	private void dessinerJeton(Graphics2D graphique, int jetonX, int jetonY, Color couleur, float angle) {

		int bordJetonGauche = round(largeurJeton / 10.0f);
		int bordJetonHaut = round(hauteurJeton / 10.0f);

		float dilatationX = (float) Math.abs(Math.cos(angle));

		float largeurExterne = largeurJeton * dilatationX;
		float largeurInterne = (largeurJeton - 2 * bordJetonGauche)
				* dilatationX;

		float decalageExterne = (largeurJeton - largeurExterne) / 2;
		float decalageInterne = (largeurJeton - largeurInterne) / 2;

		graphique.setColor(couleur.darker());

		graphique.fillOval(round(jetonX + decalageExterne), jetonY, round(largeurExterne), hauteurJeton);

		graphique.setColor(couleur);

		graphique.fillOval(round(jetonX + decalageInterne), jetonY
				+ bordJetonHaut, round(largeurInterne), hauteurJeton - 2
				* bordJetonHaut);
	}
}
