package com.github.achaaab.puissance4.moteur;

import com.github.achaaab.puissance4.ia.NiveauIa;
import com.github.achaaab.puissance4.presentation.composants.PresentationParametrage;
import com.github.achaaab.puissance4.presentation.utilitaire.Couleur;

import static com.github.achaaab.puissance4.ia.NiveauIa.DEBUTANT;
import static com.github.achaaab.puissance4.presentation.utilitaire.Couleur.JAUNE;
import static com.github.achaaab.puissance4.presentation.utilitaire.Couleur.ROUGE;

/**
 * @author Jonathan Guéhenneux
 */
public class ParametragePartie {

	private final Ordonnancement ordonnancement;
	private final PresentationParametrage presentation;

	private String nomJoueur1;
	private Couleur couleurJoueur1;
	private boolean joueur1Ordinateur;
	private NiveauIa niveauJoueur1;
	private boolean joueur1Deterministe;

	private String nomJoueur2;
	private Couleur couleurJoueur2;
	private boolean joueur2Ordinateur;
	private NiveauIa niveauJoueur2;
	private boolean joueur2Deterministe;

	/**
	 * 
	 */
	public ParametragePartie() {

		ordonnancement = new Ordonnancement();

		nomJoueur1 = "Joueur 1";
		couleurJoueur1 = JAUNE;
		joueur1Ordinateur = false;
		niveauJoueur1 = DEBUTANT;
		joueur1Deterministe = false;

		nomJoueur2 = "Joueur 2";
		couleurJoueur2 = ROUGE;
		joueur2Ordinateur = false;
		niveauJoueur2 = DEBUTANT;
		joueur2Deterministe = false;

		presentation = new PresentationParametrage(this);
	}

	/**
	 * lit le paramétrage de la nouvelle partie dans la présentation
	 */
	public void lire() {

		ordonnancement.lire();

		nomJoueur1 = presentation.getNomJoueur1();
		couleurJoueur1 = presentation.getCouleurJoueur1();
		joueur1Ordinateur = presentation.isJoueur1Ordinateur();
		niveauJoueur1 = presentation.getNiveauJoueur1();
		joueur1Deterministe = presentation.isJoueur1Deterministe();

		nomJoueur2 = presentation.getNomJoueur2();
		couleurJoueur2 = presentation.getCouleurJoueur2();
		joueur2Ordinateur = presentation.isJoueur2Ordinateur();
		niveauJoueur2 = presentation.getNiveauJoueur2();
		joueur2Deterministe = presentation.isJoueur2Deterministe();
	}

	/**
	 * @return the ordonnancement
	 */
	public Ordonnancement getOrdonnancement() {
		return ordonnancement;
	}

	/**
	 * @return the nomJoueur1
	 */
	public String getNomJoueur1() {
		return nomJoueur1;
	}

	/**
	 * @param nomJoueur1 the nomJoueur1 to set
	 */
	public void setNomJoueur1(String nomJoueur1) {
		this.nomJoueur1 = nomJoueur1;
	}

	/**
	 * @return the nomJoueur2
	 */
	public String getNomJoueur2() {
		return nomJoueur2;
	}

	/**
	 * @param nomJoueur2 the nomJoueur2 to set
	 */
	public void setNomJoueur2(String nomJoueur2) {
		this.nomJoueur2 = nomJoueur2;
	}

	/**
	 * @return the joueur1Ordinateur
	 */
	public boolean isJoueur1Ordinateur() {
		return joueur1Ordinateur;
	}

	/**
	 * @param joueur1Ordinateur the joueur1Ordinateur to set
	 */
	public void setJoueur1Ordinateur(boolean joueur1Ordinateur) {
		this.joueur1Ordinateur = joueur1Ordinateur;
	}

	/**
	 * @return the joueur2Ordinateur
	 */
	public boolean isJoueur2Ordinateur() {
		return joueur2Ordinateur;
	}

	/**
	 * @param joueur2Ordinateur the joueur2Ordinateur to set
	 */
	public void setJoueur2Ordinateur(boolean joueur2Ordinateur) {
		this.joueur2Ordinateur = joueur2Ordinateur;
	}

	/**
	 * @return the niveauJoueur1
	 */
	public NiveauIa getNiveauJoueur1() {
		return niveauJoueur1;
	}

	/**
	 * @param niveauJoueur1 the niveauJoueur1 to set
	 */
	public void setNiveauJoueur1(NiveauIa niveauJoueur1) {
		this.niveauJoueur1 = niveauJoueur1;
	}

	/**
	 * @return the niveauJoueur2
	 */
	public NiveauIa getNiveauJoueur2() {
		return niveauJoueur2;
	}

	/**
	 * @param niveauJoueur2 the niveauJoueur2 to set
	 */
	public void setNiveauJoueur2(NiveauIa niveauJoueur2) {
		this.niveauJoueur2 = niveauJoueur2;
	}

	/**
	 * @return the couleurJoueur1
	 */
	public Couleur getCouleurJoueur1() {
		return couleurJoueur1;
	}

	/**
	 * @param couleurJoueur1 the couleurJoueur1 to set
	 */
	public void setCouleurJoueur1(Couleur couleurJoueur1) {
		this.couleurJoueur1 = couleurJoueur1;
	}

	/**
	 * @return the couleurJoueur2
	 */
	public Couleur getCouleurJoueur2() {
		return couleurJoueur2;
	}

	/**
	 * @param couleurJoueur2 the couleurJoueur2 to set
	 */
	public void setCouleurJoueur2(Couleur couleurJoueur2) {
		this.couleurJoueur2 = couleurJoueur2;
	}

	/**
	 * @return the joueur1Deterministe
	 */
	public boolean isJoueur1Deterministe() {
		return joueur1Deterministe;
	}

	/**
	 * @param joueur1Deterministe the joueur1Deterministe to set
	 */
	public void setJoueur1Deterministe(boolean joueur1Deterministe) {
		this.joueur1Deterministe = joueur1Deterministe;
	}

	/**
	 * @return the joueur2Deterministe
	 */
	public boolean isJoueur2Deterministe() {
		return joueur2Deterministe;
	}

	/**
	 * @param joueur2Deterministe the joueur2Deterministe to set
	 */
	public void setJoueur2Deterministe(boolean joueur2Deterministe) {
		this.joueur2Deterministe = joueur2Deterministe;
	}

	/**
	 * @return the presentation
	 */
	public PresentationParametrage getPresentation() {
		return presentation;
	}
}
