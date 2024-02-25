package com.github.achaaab.puissance4.presentation.utilitaire;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import static com.github.achaaab.puissance4.presentation.utilitaire.SwingUtility.scale;


/**
 * @author Jonathan Guéhenneux
 */
public class RenduJeton implements ListCellRenderer<Couleur> {

	public static final RenduJeton INSTANCE = new RenduJeton();
	private static final DefaultListCellRenderer RENDU_DEFAUT = new DefaultListCellRenderer();

	/**
	 * la largeur est donnée par la liste contenant la cellule
	 */
	private static final Dimension DIMENSION_OPTIMALE = new Dimension(0, scale(20));

	/**
	 * constructeur privé pour appliquer le patron de conception Singleton
	 */
	private RenduJeton() {

	}

	@Override
	public Component getListCellRendererComponent(
			JList<? extends Couleur> couleurs, Couleur couleur,
			int indexCouleur, boolean couleurSelectionnee, boolean focus) {

		var cellule = (JLabel) RENDU_DEFAUT.getListCellRendererComponent(
				couleurs, couleur, indexCouleur, couleurSelectionnee, focus);

		if (couleur != null) {

			cellule.setIcon(couleur.getIcone());
			cellule.setPreferredSize(DIMENSION_OPTIMALE);
		}

		return cellule;
	}
}
