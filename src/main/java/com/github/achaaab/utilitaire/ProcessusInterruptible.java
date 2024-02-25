package com.github.achaaab.utilitaire;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class ProcessusInterruptible extends Thread {

	protected boolean demandeInterruption;

	/**
	 *
	 */
	public ProcessusInterruptible() {
		demandeInterruption = false;
	}

	/**
	 * demande l'interruption du processus
	 */
	public void interrompre() {
		demandeInterruption = true;
	}

	@Override
	public void run() {

		while (!demandeInterruption) {
			boucle();
		}
	}

	/**
	 * effectue une iteration, l'iteration est l'operation atomique effectuee
	 * par le processus et ne peut etre interrompue
	 */
	public abstract void boucle();
}
