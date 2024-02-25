package com.github.achaaab.puissance4.application;

import com.github.achaaab.puissance4.moteur.Puissance4;
import com.github.achaaab.utilitaire.GestionnaireException;
import com.github.achaaab.utilitaire.swing.FenetreApplication;

/**
 * @author Jonathan Guéhenneux
 */
public class ApplicationPuissance4 {

	/**
	 * @param arguments
	 */
	public static void main(String... arguments) {

		try {

			var puissance4 = new Puissance4();
			var presentation = puissance4.getPresentation();
			var fenetre = new FenetreApplication(presentation, "Puissance 4");

			fenetre.setIconImage(ChargeurRessources.getIcone("puissance4_64.png"));

		} catch (Exception exception) {

			GestionnaireException.traiter(exception);
		}
	}
}
