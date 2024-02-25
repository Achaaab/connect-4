package com.github.achaaab.discussion.presentation;

import com.github.achaaab.discussion.controle.Intervention;
import com.github.achaaab.discussion.interpretation.InterpreteurIntervention;
import com.github.achaaab.discussion.interpretation.InterpreteurInterventionSmileys;
import com.github.achaaab.utilitaire.GestionnaireException;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import java.awt.Dimension;
import java.awt.Insets;

import static com.github.achaaab.puissance4.presentation.utilitaire.SwingUtility.scale;

/**
 * @author Jonathan Guéhenneux
 */
public class PresentationMessages extends TexteSmileys {

	private final Document document;
	private final InterpreteurIntervention interpreteur;

	/**
	 * 
	 */
	public PresentationMessages() {

		setMinimumSize(new Dimension(scale(100), scale(100)));
		setPreferredSize(new Dimension(scale(300), scale(100)));

		setEditable(false);
		setMargin(new Insets(scale(5), scale(5), scale(5), scale(5)));
		document = getStyledDocument();

		interpreteur = new InterpreteurInterventionSmileys(this);
	}

	/**
	 * Supprime la premiere intervention.
	 * 
	 * @param nombreCaracteres
	 */
	public void supprimerPremiereIntervention(int nombreCaracteres) {

		try {
			document.remove(0, nombreCaracteres);
		} catch (BadLocationException erreur) {
			GestionnaireException.traiter(erreur);
		}
	}

	/**
	 * 
	 * @param intervention
	 * @return nombre de caractères ajoutés
	 */
	public int ajouterIntervention(Intervention intervention) {
		return interpreteur.interpreterIntervention(intervention);
	}
}
