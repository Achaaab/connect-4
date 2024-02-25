package com.github.achaaab.discussion.controle;

import com.github.achaaab.discussion.presentation.PresentationDiscussion;
import com.github.achaaab.discussion.presentation.PresentationMessage;
import com.github.achaaab.discussion.presentation.PresentationMessages;
import com.github.achaaab.utilitaire.GestionnaireException;

import javax.swing.text.BadLocationException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Jonathan Guéhenneux
 */
public class Discussion {

	private final List<EcouteurIntervention> ecouteursIntervention;
	private final LinkedList<Integer> tailleInterventions;
	private final int memoire;

	private final PresentationMessages presentationMessages;
	private final PresentationMessage presentationMessage;
	private final PresentationDiscussion presentation;

	private String intervenant;

	/**
	 * @param memoire
	 */
	public Discussion(int memoire) {

		this.memoire = memoire;

		tailleInterventions = new LinkedList<>();
		ecouteursIntervention = new ArrayList<>();
		presentationMessage = new PresentationMessage(this);
		presentationMessages = new PresentationMessages();
		presentation = new PresentationDiscussion(this);
	}

	/**
	 * @param ecouteurIntervention
	 */
	public void ajouterEcouteurIntervention(EcouteurIntervention ecouteurIntervention) {
		ecouteursIntervention.add(ecouteurIntervention);
	}

	/**
	 * 
	 * @param ecouteurIntervention
	 */
	public void supprimerEcouteurIntervention(EcouteurIntervention ecouteurIntervention) {
		ecouteursIntervention.remove(ecouteurIntervention);
	}

	/**
	 * 
	 * @param intervention
	 * @param signal
	 */
	public synchronized void ajouterIntervention(Intervention intervention, boolean signal) {

		int nombreInterventions = tailleInterventions.size();

		if (nombreInterventions == memoire) {
			supprimerPremiereIntervention();
		}

		int longueurIntervention = presentationMessages.ajouterIntervention(intervention);

		tailleInterventions.addLast(longueurIntervention);

		if (signal) {
			ecouteursIntervention.forEach(ecouteur -> ecouteur.ajouterIntervention(intervention));
		}
	}

	/**
	 * 
	 */
	public synchronized void supprimerPremiereIntervention() {

		int longueurPremiereIntervention = tailleInterventions.removeFirst();

		presentationMessages.supprimerPremiereIntervention(longueurPremiereIntervention);
	}

	/**
	 * @return the presentation
	 */
	public PresentationDiscussion getPresentation() {
		return presentation;
	}

	/**
	 * @return the presentationMessages
	 */
	public PresentationMessages getPresentationMessages() {
		return presentationMessages;
	}

	/**
	 * @return the presentationMessage
	 */
	public PresentationMessage getPresentationMessage() {
		return presentationMessage;
	}

	/**
	 * 
	 */
	public void envoyer() {

		var longueurTotale = presentationMessage.getLongueur();
		var longueurMessage = longueurTotale - 1;

		try {

			var message = presentationMessage.getText(0, longueurMessage);
			var intervention = new Intervention(intervenant, message);
			ajouterIntervention(intervention, true);

			presentationMessage.setText("");

		} catch (BadLocationException erreur) {

			GestionnaireException.traiter(erreur);
		}
	}

	/**
	 * 
	 * @param intervenant
	 */
	public void setIntervenant(String intervenant) {
		this.intervenant = intervenant;
	}
}
