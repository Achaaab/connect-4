package com.github.achaaab.utilitaire.swing;

import java.awt.Container;
import java.awt.Window;

import javax.swing.JPanel;

/**
 * @author Jonathan Guéhenneux
 */
public class PackablePanel extends JPanel {

	/**
	 * 
	 */
	public void pack() {

		revalidate();

		Container parent = getParent();

		while (parent != null && !(parent instanceof Window)) {
			parent = parent.getParent();
		}

		if (parent != null) {

			var fenetre = (Window) parent;
			fenetre.pack();
		}
	}
}
