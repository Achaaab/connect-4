package com.github.achaaab.puissance4.moteur;

import java.util.concurrent.Semaphore;

import com.github.achaaab.puissance4.presentation.composants.PresentationJoueurHumain;

import static java.lang.Thread.currentThread;
import static com.github.achaaab.puissance4.moteur.GrillePuissance4.JAUNE;
import static com.github.achaaab.puissance4.moteur.Puissance4.GRILLE;

/**
 * @author Jonathan Guéhenneux
 */
public class JoueurHumain extends JoueurAbstrait {

	private final Semaphore semaphoreJeton;

	/**
	 * @param couleur
	 * @param nom
	 */
	public JoueurHumain(int couleur, String nom) {

		super(couleur, nom);

		semaphoreJeton = new Semaphore(0);
		presentation = new PresentationJoueurHumain(this);
	}

	@Override
	public void demanderJouer() {

		try {
			GRILLE.controlerJeton(semaphoreJeton);
		} catch (InterruptedException exception) {
			currentThread().interrupt();
		}

		if (!partieArretee) {
			GRILLE.jouer();
		}
	}

	@Override
	public String toString() {
		return nom + " (Humain) [" + (couleur == JAUNE ? "JAUNE" : "ROUGE") + "]";
	}

	@Override
	public void arreterJouer() {

		super.arreterJouer();

		GRILLE.retirerControleJeton();
	}
}
