package com.github.achaaab.utilitaire.swing;

import javax.swing.JFrame;
import java.awt.Container;

/**
 * @author Jonathan Guéhenneux
 */
public class FenetreApplication extends JFrame {

	/**
	 * @param composant
	 */
	public FenetreApplication(Container composant) {
		this(composant, "Fenêtre de test");
	}

	/**
	 * @param composant
	 * @param titre
	 */
	public FenetreApplication(Container composant, String titre) {

		super(titre);

		setContentPane(composant);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
