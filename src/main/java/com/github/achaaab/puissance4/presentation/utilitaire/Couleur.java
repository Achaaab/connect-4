package com.github.achaaab.puissance4.presentation.utilitaire;

import java.awt.Color;

import javax.swing.ImageIcon;

import com.github.achaaab.puissance4.moteur.GrillePuissance4;

import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;

/**
 * @author Jonathan Guéhenneux
 */
public class Couleur {

	public static final Couleur JAUNE = new Couleur(GrillePuissance4.JAUNE, YELLOW);
	public static final Couleur ROUGE = new Couleur(GrillePuissance4.ROUGE, RED);

	public static final Couleur[] COULEURS = { JAUNE, ROUGE };

	private final int numero;
	private final ImageIcon icone;

	/**
	 * @param numero
	 * @param couleur
	 */
	private Couleur(int numero, Color couleur) {

		this.numero = numero;

		icone = new IconeCouleur(couleur);
	}

	/**
	 * @return the numero
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * @return the couleur
	 */
	public ImageIcon getIcone() {
		return icone;
	}

	public String toString() {
		return "";
	}

	/**
	 * @param numero
	 * @return
	 */
	public static Couleur getCouleur(int numero) {

		return switch (numero) {

			case GrillePuissance4.JAUNE -> JAUNE;
			case GrillePuissance4.ROUGE -> ROUGE;
			default -> null;
		};
	}

	/**
	 * @param couleur
	 * @return
	 */
	public static Couleur autre(Couleur couleur) {
		return couleur == JAUNE ? ROUGE : JAUNE;
	}
}
