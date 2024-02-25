package com.github.achaaab.puissance4.animation;

import com.github.achaaab.puissance4.moteur.GrillePuissance4;
import com.github.achaaab.utilitaire.Chronometre;

import static java.lang.Thread.sleep;

/**
 * Animation de rotation des jetons alignés.
 * 
 * @author Jonathan Guéhenneux
 */
public class RotationAlignement extends AnimationGrille {

	/**
	 * vitesse de rotation des jetons en radians par seconde
	 */
	private static final float VITESSE_ROTATION = 12.0f;

	private static final Chronometre CHRONOMETRE = Chronometre.DEFAULT_INSTANCE;

	private static final String CLE_CHRONOMETRE = "rotation_alignement";

	/**
	 * @param grille
	 */
	public RotationAlignement(GrillePuissance4 grille) {

		super(grille);

		demandeArret = false;
	}

	@Override
	public void run() {

		float angle;
		float t;

		CHRONOMETRE.start(CLE_CHRONOMETRE);

		while (!demandeArret) {

			t = CHRONOMETRE.tick(CLE_CHRONOMETRE);

			angle = t * VITESSE_ROTATION;
			grille.angleJetonsAlignes = angle;
			grille.getPresentation().recalculerImageAsynchrone();

			try {
				sleep(20);
			} catch (InterruptedException exception) {
				currentThread().interrupt();
			}
		}

		CHRONOMETRE.stop(CLE_CHRONOMETRE);
	}
}
