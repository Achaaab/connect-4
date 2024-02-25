package com.github.achaaab.puissance4.ia;

import com.github.achaaab.multithreading.GestionnaireTache;

import java.util.ArrayList;

import static com.github.achaaab.puissance4.moteur.Puissance4.GRILLE;

/**
 * @author Jonathan Guéhenneux
 */
public class GestionnaireRechercheMeilleurCoup extends GestionnaireTache<EvaluationColonne, RechercheMeilleurCoup> {

	private final int profondeurMaximum;
	private final boolean deterministe;

	private long nombrePositionsEvaluees;

	/**
	 * @param tache
	 * @param profondeurMaximum
	 * @param deterministe
	 */
	public GestionnaireRechercheMeilleurCoup(RechercheMeilleurCoup tache, int profondeurMaximum, boolean deterministe) {

		super(tache);

		this.profondeurMaximum = profondeurMaximum;
		this.deterministe = deterministe;
	}

	@Override
	public void executer() throws InterruptedException {

		nombrePositionsEvaluees = 1;

		var listeProcessus = new ArrayList<ProcessusEvaluationColonne>();

		EvaluationColonne evaluationColonne;

		while (!tache.isComplete()) {

			evaluationColonne = tache.getTachePartielle();

			var processus = new ProcessusEvaluationColonne(evaluationColonne, GRILLE.clone(),
					profondeurMaximum, deterministe);

			processus.start();

			listeProcessus.add(processus);
		}

		for (var processus : listeProcessus) {

			processus.join();
			nombrePositionsEvaluees += processus.getNombrePositionsEvaluees();
		}
	}

	/**
	 * @return nombre de positions évaluées
	 */
	public long getNombrePositionsEvaluees() {
		return nombrePositionsEvaluees;
	}
}
