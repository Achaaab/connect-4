package com.github.achaaab.utilitaire.swing;

import java.util.TimerTask;

/**
 * @author Jonathan Guéhenneux
 */
public class Rafraichissement extends TimerTask {

	private final Dessin panneau;

	/**
	 * @param panneau
	 */
	public Rafraichissement(Dessin panneau) {
		this.panneau = panneau;
	}

	@Override
	public void run() {
		panneau.repaint();
	}
}
