package com.github.achaaab.puissance4.presentation.utilitaire;

import javax.swing.ImageIcon;
import java.awt.Color;
import java.awt.image.BufferedImage;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static com.github.achaaab.puissance4.presentation.utilitaire.SwingUtility.scale;

/**
 * @author Jonathan Guéhenneux
 */
public class IconeCouleur extends ImageIcon {

	private static final int TAILLE = scale(16);

	/**
	 * @param couleur
	 */
	public IconeCouleur(Color couleur) {

		var image = new BufferedImage(TAILLE, TAILLE, TYPE_INT_ARGB);

		var graphics = image.createGraphics();

		graphics.setColor(couleur);

		graphics.setRenderingHint(KEY_ANTIALIASING, VALUE_ANTIALIAS_ON);
		graphics.fillOval(0, 0, TAILLE, TAILLE);

		setImage(image);
	}
}
