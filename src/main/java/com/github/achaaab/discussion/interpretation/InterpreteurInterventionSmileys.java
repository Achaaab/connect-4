package com.github.achaaab.discussion.interpretation;

import com.github.achaaab.discussion.presentation.TexteSmileys;
import com.github.achaaab.exceptions.RessourceManquante;
import com.github.achaaab.utilitaire.GestionnaireException;

import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Jonathan Guéhenneux
 */
public class InterpreteurInterventionSmileys extends InterpreteurInterventionTextuel {

	private final DictionnaireSmileys dictionnaireSmileys;

	/**
	 * @param texteSmileys
	 * @throws IOException
	 */
	public InterpreteurInterventionSmileys(TexteSmileys texteSmileys) {

		super(texteSmileys);

		dictionnaireSmileys = new DictionnaireSmileys();
	}

	@Override
	protected void interpreterIcone(Element elementIcone, DocumentEvent modification) throws BadLocationException {
		interpreterTexte(elementIcone, modification);
	}

	@Override
	protected void interpreterTexte(Element elementTexte, DocumentEvent modification) throws BadLocationException {

		var document = elementTexte.getDocument();
		var texte = texteSmileys.supprimer(elementTexte);

		// on va maintenant chercher des smileys écrits dans le texte

		var patternSmileys = dictionnaireSmileys.getPatternSmileys();
		var matcherSmileys = patternSmileys.matcher(texte);

		String texteSansSmiley;
		String texteSmiley;
		int indexDebutTexteSansSmiley = 0;
		int indexFinTexteSansSmiley;

		int positionInsertion = elementTexte.getStartOffset();

		SimpleAttributeSet attributsSmiley;

		// on parcourt le texte tant qu'on trouve des smileys

		while (matcherSmileys.find()) {

			// on réécrit tel quels les morceaux de texte sans smileys

			indexFinTexteSansSmiley = matcherSmileys.start();

			if (indexFinTexteSansSmiley > indexDebutTexteSansSmiley) {

				texteSansSmiley = texte.substring(indexDebutTexteSansSmiley, indexFinTexteSansSmiley);
				document.insertString(positionInsertion, texteSansSmiley, ATTRIBUTS_MESSAGE);
				positionInsertion += texteSansSmiley.length();
			}

			// on récupère le texte du smiley
			texteSmiley = matcherSmileys.group();

			try {

				// on récupère l'icone correspondante au smiley
				var smiley = dictionnaireSmileys.getSmiley(texteSmiley);
				attributsSmiley = new SimpleAttributeSet(ATTRIBUTS_MESSAGE);
				StyleConstants.setIcon(attributsSmiley, smiley);

				// on insert l'icone avec son texte original
				document.insertString(positionInsertion, texteSmiley, attributsSmiley);
				positionInsertion += texteSmiley.length();

			} catch (RessourceManquante erreur) {

				GestionnaireException.traiter(erreur);
			}

			indexDebutTexteSansSmiley = matcherSmileys.end();
		}

		texteSansSmiley = texte.substring(indexDebutTexteSansSmiley);
		document.insertString(positionInsertion, texteSansSmiley, ATTRIBUTS_MESSAGE);
	}

	@Override
	protected void interpreterMessage(String message) {

		Pattern patternSmileys = dictionnaireSmileys.getPatternSmileys();
		Matcher matcherSmileys = patternSmileys.matcher(message);

		StringBuilder partieMessage;
		String texteSmiley;

		while (matcherSmileys.find()) {

			partieMessage = new StringBuilder();
			matcherSmileys.appendReplacement(partieMessage, "");
			texteSmileys.ajouterTexte(partieMessage.toString(), ATTRIBUTS_MESSAGE);

			texteSmiley = matcherSmileys.group();

			try {

				var smiley = dictionnaireSmileys.getSmiley(texteSmiley);
				texteSmileys.ajouterSmiley(smiley);

			} catch (RessourceManquante erreur) {

				GestionnaireException.traiter(erreur);
			}
		}

		partieMessage = new StringBuilder();
		matcherSmileys.appendTail(partieMessage);
		texteSmileys.ajouterTexte(partieMessage.toString(), ATTRIBUTS_MESSAGE);
	}
}
