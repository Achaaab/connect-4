package com.github.achaaab.apparence;

import com.github.achaaab.utilitaire.GestionnaireException;

import static java.awt.Window.getWindows;
import static javax.swing.SwingUtilities.updateComponentTreeUI;
import static javax.swing.UIManager.setLookAndFeel;

/**
 * @author Jonathan Guéhenneux
 */
public class LookAndFeelUtilitaire {

	/**
	 * @param classe
	 */
	public static void setLookAndFeelParClasse(String classe) {

		try {

			setLookAndFeel(classe);

			var fenetres = getWindows();

			for (var fenetre : fenetres) {

				updateComponentTreeUI(fenetre);
				fenetre.pack();
			}

		} catch (Exception erreur) {

			GestionnaireException.traiter(erreur);
		}
	}
}
