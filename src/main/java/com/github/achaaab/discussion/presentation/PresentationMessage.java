package com.github.achaaab.discussion.presentation;

import com.github.achaaab.discussion.controle.Discussion;
import com.github.achaaab.discussion.interpretation.InterpreteurIntervention;
import com.github.achaaab.discussion.interpretation.InterpreteurInterventionSmileys;
import com.github.achaaab.utilitaire.GestionnaireException;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import java.awt.Dimension;

import static com.github.achaaab.puissance4.presentation.utilitaire.SwingUtility.scale;
import static javax.swing.SwingUtilities.invokeLater;

/**
 * @author Jonathan Guéhenneux
 */
public class PresentationMessage extends TexteSmileys {

	private final InterpreteurIntervention interpreteurIntervention;
	private final DocumentListener ecouteurInsertion;
	private final Discussion discussion;

	/**
	 * @param discussion
	 */
	public PresentationMessage(Discussion discussion) {

		this.discussion = discussion;

		setPreferredSize(new Dimension(scale(300), scale(50)));

		interpreteurIntervention = new InterpreteurInterventionSmileys(this);
		ecouteurInsertion = new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent suppression) {
				interpreterDocument(suppression, suppression.getOffset());
			}

			@Override
			public void insertUpdate(DocumentEvent insertion) {

				int indexDebutInsertion = insertion.getOffset();
				int longueurTexteInsere = insertion.getLength();

				String texteInsere = "";

				try {
					texteInsere = document.getText(indexDebutInsertion, longueurTexteInsere);
				} catch (BadLocationException erreur) {
					GestionnaireException.traiter(erreur);
				}

				if (texteInsere.equals("\n")) {
					envoyerIntervention();
				} else {
					interpreterDocument(insertion, indexDebutInsertion + longueurTexteInsere);
				}
			}

			@Override
			public void changedUpdate(DocumentEvent changement) {

			}
		};

		ajouterEcouteur();
	}

	/**
	 * 
	 */
	private void envoyerIntervention() {
		invokeLater(discussion::envoyer);
	}

	/**
	 * 
	 * @param modification
	 * @param positionCurseur
	 */
	public void interpreterDocument(DocumentEvent modification, int positionCurseur) {

		invokeLater(() -> {

			try {

				interpreteurIntervention.interpreterDocument(modification);
				setCaretPosition(positionCurseur);

			} catch (BadLocationException erreur) {
				GestionnaireException.traiter(erreur);
			}
		});
	}

	@Override
	public void ajouterEcouteur() {
		document.addDocumentListener(ecouteurInsertion);
	}

	@Override
	public void supprimerEcouteur() {
		document.removeDocumentListener(ecouteurInsertion);
	}
}
