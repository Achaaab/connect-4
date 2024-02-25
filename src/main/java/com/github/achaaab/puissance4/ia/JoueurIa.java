package com.github.achaaab.puissance4.ia;

import com.github.achaaab.puissance4.moteur.GrillePuissance4;
import com.github.achaaab.puissance4.moteur.JoueurAbstrait;
import com.github.achaaab.puissance4.presentation.composants.PresentationJoueurIa;
import com.github.achaaab.utilitaire.Chronometre;

import static java.lang.Thread.currentThread;
import static com.github.achaaab.puissance4.moteur.Puissance4.GRILLE;

/**
 * @author Jonathan Guéhenneux
 */
public class JoueurIa extends JoueurAbstrait {

	private final boolean deterministe;
	private NiveauIa niveau;
	private String vitesseCalcul;

	/**
	 * @param couleur Indique la couleur du joueur, c'est-à-dire, la couleur de ses jetons.
	 * @param nom Indique le nom du joueur.
	 * @param niveau Indique le niveau de l'ordinateur, plus le niveau est élevé, plus le temps de calcul est grand.
	 * @param deterministe Indique si l'ordinateur joue de facon deterministe, c'est-à-dire s'il joue toujours le même
	 * coup dans une situation donnée. Si cette valeur est renseignée à {@code true}, l'ordinateur choisira la colonne
	 * la plus à gauche donnant la meilleure évaluation, sinon il choisira au hasard parmi les colonnes qui donnent la
	 * meilleure évaluation.
	 */
	public JoueurIa(int couleur, String nom, NiveauIa niveau, boolean deterministe) {

		super(couleur, nom);

		this.niveau = niveau;
		this.deterministe = deterministe;

		vitesseCalcul = "-";

		presentation = new PresentationJoueurIa(this);
	}

	public void demanderJouer() {

		var chronometre = Chronometre.DEFAULT_INSTANCE;
		chronometre.start("recherche");

		var recherche = new RechercheMeilleurCoup(deterministe);

		int meilleureColonne;

		int profondeurMaximum = niveau.getProfondeurMaximum(GRILLE.nombreJetonsJoues);
		var gestionnaire = new GestionnaireRechercheMeilleurCoup(recherche, profondeurMaximum, deterministe);

		try {
			gestionnaire.executer();
		} catch (InterruptedException exception) {
			currentThread().interrupt();
		}

		double tempsRecherche = chronometre.stop("recherche");

		long nombrePositionsEvaluees = gestionnaire.getNombrePositionsEvaluees();

		long vitesse = Math.round(nombrePositionsEvaluees / tempsRecherche);

		setVitesseCalcul((vitesse / 1000) + "K");

		meilleureColonne = recherche.getMeilleureColonne();

		if (!partieArretee) {

			GRILLE.colonneVisee = meilleureColonne;
			GRILLE.jouer();
		}
	}

	/**
	 * @return the vitesseCalcul
	 */
	public String getVitesseCalcul() {
		return vitesseCalcul;
	}

	/**
	 * @param vitesseCalcul the vitesseCalcul to set
	 */
	public void setVitesseCalcul(String vitesseCalcul) {

		this.vitesseCalcul = vitesseCalcul;

		((PresentationJoueurIa) presentation).setVitesseCalcul(vitesseCalcul);
	}

	@Override
	public String toString() {

		return nom + " (IA niveau " + niveau + ") ["
				+ (couleur == GrillePuissance4.JAUNE ? "JAUNE" : "ROUGE") + "]";
	}

	/**
	 * @return niveau
	 */
	public NiveauIa getNiveau() {
		return niveau;
	}

	/**
	 * @param niveau niveau à définir
	 */
	public void setNiveau(NiveauIa niveau) {
		this.niveau = niveau;
	}

	/**
	 * @return the deterministe
	 */
	public boolean isDeterministe() {
		return deterministe;
	}
}
