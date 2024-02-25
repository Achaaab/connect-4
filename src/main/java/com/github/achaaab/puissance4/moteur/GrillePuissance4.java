package com.github.achaaab.puissance4.moteur;

import com.github.achaaab.puissance4.animation.AnimationGrille;
import com.github.achaaab.puissance4.animation.ChuteJeton;
import com.github.achaaab.puissance4.animation.RotationAlignement;
import com.github.achaaab.puissance4.presentation.composants.PresentationGrille;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Semaphore;

import static java.lang.System.arraycopy;
import static com.github.achaaab.puissance4.moteur.Puissance4.HAUTEUR_GRILLE;
import static com.github.achaaab.puissance4.moteur.Puissance4.LARGEUR_GRILLE;
import static com.github.achaaab.puissance4.moteur.Puissance4.LONGUEUR_ALIGNEMENT;

/**
 * @author Jonathan Guéhenneux
 */
public class GrillePuissance4 {

	public static final int COULEUR_INDEFINIE = 0;
	public static final int ROUGE = 1;
	public static final int JAUNE = -1;

	private final int[][] cases;

	public int[] hauteurs;
	public int nombreCases;
	public int couleurCourante;
	public int nombreJetonsJoues;
	private PresentationGrille presentation;
	public float noteJaune;
	public float noteRouge;
	private EnsembleAlignements ensembleAlignements;
	private Alignement[][][] alignements;
	public boolean jetonPris;
	private Semaphore semaphoreJeton;
	public int colonneVisee;
	public float hauteurJetonJoue;
	public float angleJetonsAlignes;
	public Set<Point> jetonsAlignes;
	private List<AnimationGrille> animationsCourantes;
	private boolean animationChute;

	/**
	 * 
	 */
	public GrillePuissance4() {

		nombreCases = LARGEUR_GRILLE * HAUTEUR_GRILLE;

		cases = new int[LARGEUR_GRILLE][HAUTEUR_GRILLE];
		hauteurs = new int[LARGEUR_GRILLE];

		animationChute = true;
	}

	/**
	 * @return une copie de la grille
	 */
	public GrillePuissance4 clone() {

		var clone = new GrillePuissance4();

		// on clone

		for (int colonne = 0; colonne < LARGEUR_GRILLE; colonne++) {

			arraycopy(cases[colonne], 0, clone.cases[colonne], 0, HAUTEUR_GRILLE);
			clone.hauteurs[colonne] = hauteurs[colonne];
		}

		clone.couleurCourante = couleurCourante;
		clone.nombreJetonsJoues = nombreJetonsJoues;
		clone.alignements = ensembleAlignements.clonerAlignements();
		clone.noteJaune = noteJaune;
		clone.noteRouge = noteRouge;

		return clone;
	}

	/**
	 * @return
	 */
	public synchronized PresentationGrille getPresentation() {

		if (presentation == null) {
			presentation = new PresentationGrille(this);
		}

		return presentation;
	}

	/**
	 *
	 */
	public void initialiser() {

		// initialisation du contenu des cases et des hauteurs de colonne

		for (var colonne = 0; colonne < LARGEUR_GRILLE; colonne++) {

			for (var ligne = 0; ligne < HAUTEUR_GRILLE; ligne++) {
				cases[colonne][ligne] = COULEUR_INDEFINIE;
			}

			hauteurs[colonne] = 0;
		}

		// initialisation des alignements associés à chaque case

		ensembleAlignements = new EnsembleAlignements();
		alignements = ensembleAlignements.alignements;
		jetonsAlignes = new HashSet<>();

		// initialisation du nombre de jetons joués et des notes

		nombreJetonsJoues = 0;

		noteJaune = 0.0f;
		noteRouge = 0.0f;

		animationsCourantes = new ArrayList<>();
	}

	/**
	 * @param colonne
	 * @return
	 */
	public boolean colonneComplete(int colonne) {
		return hauteurs[colonne] == HAUTEUR_GRILLE;
	}

	/**
	 * @return true si la grille est complete
	 */
	public boolean isComplete() {
		return nombreJetonsJoues == nombreCases;
	}

	/**
	 * Introduit un jeton de la couleur courante dans la colonne specifiée.
	 * 
	 * @param colonne colonne dans laquelle le jeton doit être joué
	 * @return tableau associant a chaque alignement de la case un booléen indiquant si l'alignement en question
	 * a été modifié ou non par le coup
	 */
	public boolean[] jouer(int colonne) {

		// mise a jour de la grille

		var ligne = hauteurs[colonne]++;
		cases[colonne][ligne] = couleurCourante;
		nombreJetonsJoues++;

		// mise à jour des alignements

		var alignementsCase = alignements[colonne][ligne];
		var nombreAlignements = alignementsCase.length;
		Alignement alignement;

		var alignementsModifies = new boolean[nombreAlignements];

		boolean alignementModifie;

		for (var indexAlignement = 0; indexAlignement < nombreAlignements; indexAlignement++) {

			alignement = alignementsCase[indexAlignement];
			alignementModifie = alignement.jouer(this, couleurCourante);
			alignementsModifies[indexAlignement] = alignementModifie;
		}

		// mise à jour de la couleur courante
		couleurCourante = -couleurCourante;

		return alignementsModifies;
	}

	/**
	 * joue dans la colonne visée, puis valide le coup (pas de retour en arrière possible)
	 */
	public void jouer() {

		if (animationChute) {

			jetonPris = true;
			demarrerAnimationChute(hauteurs[colonneVisee]);
			jetonPris = false;
		}

		jouer(colonneVisee);

		verifierAlignements();

		if (jetonsAlignes.isEmpty()) {
			ensembleAlignements.eliminerAlignementsFermes();
		} else {
			demarrerAnimationAlignement();
		}
	}

	/**
	 * @param ligne
	 */
	public void demarrerAnimationChute(int ligne) {

		var animation = new ChuteJeton(this, ligne);
		animation.start();
		animation.attendre();
	}

	/**
	 * 
	 */
	public void demarrerAnimationAlignement() {

		var animation = new RotationAlignement(this);
		animation.start();

		animationsCourantes.add(animation);
	}

	/**
	 * 
	 */
	public void arreterAnimations() {

		animationsCourantes.forEach(AnimationGrille::arreter);
		animationsCourantes.clear();
	}

	/**
	 * retire le dernier jeton ajouté dans la colonne specifiée
	 * 
	 * @param colonne
	 */
	public final void annuler(int colonne, boolean[] alignementsModifies) {

		// mise à jour de la couleur courante
		couleurCourante = -couleurCourante;

		// mise à jour de la grille
		int ligne = --hauteurs[colonne];
		nombreJetonsJoues--;

		// mise à jour des alignements

		var alignementsCase = alignements[colonne][ligne];
		var nombreAlignements = alignementsCase.length;

		for (var indexAlignement = 0; indexAlignement < nombreAlignements; indexAlignement++) {

			if (alignementsModifies[indexAlignement]) {
				alignementsCase[indexAlignement].annuler(this, couleurCourante);
			}
		}
	}

	/**
	 * @param colonne
	 * @param ligne
	 * @return
	 */
	public int getCase(int colonne, int ligne) {
		return cases[colonne][ligne];
	}

	/**
	 * donne le contrôle du jeton à introduire
	 * 
	 * @param semaphoreJeton
	 * @throws InterruptedException
	 */
	public void controlerJeton(Semaphore semaphoreJeton) throws InterruptedException {

		this.semaphoreJeton = semaphoreJeton;

		jetonPris = true;
		hauteurJetonJoue = 0.0f;

		getPresentation().donnerControleJeton();
		semaphoreJeton.acquire();
	}

	/**
	 * retire le contrôle du jeton (nécessaire lors de l'arrêt forcé de la partie)
	 */
	public void retirerControleJeton() {

		getPresentation().retirerControleJeton();
		jetonPris = false;

		if (semaphoreJeton.hasQueuedThreads()) {
			semaphoreJeton.release();
		}
	}

	/**
	 * lâche le jeton au dessus de la colonne visée, s'il elle n'est pas pleine
	 */
	public void lacherJeton() {

		if (!colonneComplete(colonneVisee)) {

			getPresentation().retirerControleJeton();
			semaphoreJeton.release();
		}
	}

	/**
	 * met à jour l'ensemble des jetons alignés
	 */
	public void verifierAlignements() {

		Alignement[] alignementsCase;

		for (var colonne = 0; colonne < LARGEUR_GRILLE; colonne++) {

			for (var ligne = 0; ligne < HAUTEUR_GRILLE; ligne++) {

				alignementsCase = alignements[colonne][ligne];

				for (var alignement : alignementsCase) {

					if (alignement.position == LONGUEUR_ALIGNEMENT) {

						jetonsAlignes.add(new Point(colonne, ligne));
						break;
					}
				}
			}
		}
	}

	/**
	 * @param coordonneesJeton
	 * @return
	 */
	public float getAngleJeton(Point coordonneesJeton) {

		float angleJeton;

		if (jetonsAlignes.contains(coordonneesJeton)) {
			angleJeton = angleJetonsAlignes;
		} else {
			angleJeton = 0.0f;
		}

		return angleJeton;
	}

	/**
	 * @return the animationChute
	 */
	public boolean isAnimationChute() {
		return animationChute;
	}

	/**
	 * @param animationChute the animationChute to set
	 */
	public void setAnimationChute(boolean animationChute) {
		this.animationChute = animationChute;
	}
}
