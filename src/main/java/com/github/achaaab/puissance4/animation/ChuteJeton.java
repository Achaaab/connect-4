package com.github.achaaab.puissance4.animation;

import com.github.achaaab.puissance4.moteur.GrillePuissance4;
import com.github.achaaab.utilitaire.Chronometre;

import static com.github.achaaab.puissance4.moteur.Puissance4.HAUTEUR_GRILLE;

/**
 * @author Jonathan Guéhenneux
 */
public class ChuteJeton extends AnimationGrille {

	/**
	 * nombre de pixels par seconde par seconde (accéleration des jetons lors de leur chute)
	 */
	private static final float G = 200.0f;

	private static final Chronometre CHRONOMETRE = Chronometre.DEFAULT_INSTANCE;
	private static final String CLE_CHRONOMETRE = "chute_jeton";

	private final int ligne;

	/**
	 * @param grille
	 * @param ligne
	 */
	public ChuteJeton(GrillePuissance4 grille, int ligne) {

		super(grille);

		this.ligne = ligne;
	}

	@Override
	public void run() {

		float yMax = HAUTEUR_GRILLE - ligne;
		float t;
		float y = 0.0f;

		CHRONOMETRE.start(CLE_CHRONOMETRE);

		while (!demandeArret && y < yMax) {

			t = CHRONOMETRE.tick(CLE_CHRONOMETRE);
			y = Math.min(G * t * t / 2, yMax);

			grille.hauteurJetonJoue = y;
			grille.getPresentation().recalculerImageAsynchrone();

			try {
				sleep(20);
			} catch (InterruptedException interruptedException) {
				currentThread().interrupt();
			}
		}

		CHRONOMETRE.stop(CLE_CHRONOMETRE);
	}
}
