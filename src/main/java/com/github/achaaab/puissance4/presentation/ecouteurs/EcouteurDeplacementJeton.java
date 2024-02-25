package com.github.achaaab.puissance4.presentation.ecouteurs;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.github.achaaab.puissance4.moteur.GrillePuissance4;
import com.github.achaaab.puissance4.presentation.composants.PresentationGrille;

/**
 * @author Jonathan Guéhenneux
 */
public class EcouteurDeplacementJeton extends MouseAdapter {

	private final GrillePuissance4 grille;
	private final PresentationGrille presentation;

	/**
	 * @param grille
	 * @param presentation
	 */
	public EcouteurDeplacementJeton(GrillePuissance4 grille, PresentationGrille presentation) {

		this.grille = grille;
		this.presentation = presentation;
	}

	@Override
	public void mouseMoved(MouseEvent evenement) {

		int x = evenement.getX();
		int colonneVisee = presentation.getColonne(x);

		if (grille.colonneVisee != colonneVisee) {

			grille.colonneVisee = colonneVisee;
			grille.getPresentation().recalculerImageAsynchrone();
		}
	}

	@Override
	public void mouseReleased(MouseEvent evenement) {
		grille.lacherJeton();
	}
}
