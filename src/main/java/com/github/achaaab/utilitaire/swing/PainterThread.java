package com.github.achaaab.utilitaire.swing;

import com.github.achaaab.utilitaire.ProcessusInterruptible;

import java.awt.Component;

/**
 * @author Jonathan Guéhenneux
 */
public class PainterThread extends ProcessusInterruptible {

	private final Component composant;
	private final int fps;

	/**
	 * @param composant
	 */
	public PainterThread(Component composant, int fps) {

		this.composant = composant;
		this.fps = fps;
	}

	@Override
	public void boucle() {

		composant.repaint();

		try {
			sleep(1000 / fps);
		} catch (InterruptedException interruptedException) {
			currentThread().interrupt();
		}
	}
}
