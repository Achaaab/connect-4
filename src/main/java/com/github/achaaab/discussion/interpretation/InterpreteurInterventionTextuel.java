package com.github.achaaab.discussion.interpretation;

import com.github.achaaab.discussion.presentation.TexteSmileys;

import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.Color;

import static java.lang.System.lineSeparator;

/**
 * @author Jonathan Guéhenneux
 */
public class InterpreteurInterventionTextuel extends InterpreteurInterventionAbstrait {

	private static final Color COULEUR_INTERVENANT = new Color(64, 64, 160);
	private static final Color COULEUR_INFORMATIONS = new Color(32, 32, 32);

	protected static final SimpleAttributeSet ATTRIBUTS_INTERVENANT;
	protected static final SimpleAttributeSet ATTRIBUTS_MESSAGE;
	protected static final SimpleAttributeSet ATTRIBUTS_INFORMATIONS;
	protected static final String SEPARATEUR_INTERVENANT_MESSAGE = " :" + lineSeparator();

	static {

		ATTRIBUTS_INTERVENANT = new SimpleAttributeSet();
		ATTRIBUTS_MESSAGE = new SimpleAttributeSet();
		ATTRIBUTS_INFORMATIONS = new SimpleAttributeSet();

		StyleConstants.setBold(ATTRIBUTS_INTERVENANT, true);
		StyleConstants.setFontSize(ATTRIBUTS_INTERVENANT, 16);
		StyleConstants.setFontFamily(ATTRIBUTS_INTERVENANT, "arial");
		StyleConstants.setForeground(ATTRIBUTS_INTERVENANT, COULEUR_INTERVENANT);

		StyleConstants.setFontSize(ATTRIBUTS_MESSAGE, 14);
		StyleConstants.setFontFamily(ATTRIBUTS_MESSAGE, "arial");

		StyleConstants.setFontFamily(ATTRIBUTS_INFORMATIONS, "arial");
		StyleConstants.setFontSize(ATTRIBUTS_INFORMATIONS, 12);
		StyleConstants.setForeground(ATTRIBUTS_INFORMATIONS, COULEUR_INFORMATIONS);
	}

	/**
	 * @param texteSmileys
	 */
	public InterpreteurInterventionTextuel(TexteSmileys texteSmileys) {
		super(texteSmileys, ATTRIBUTS_INTERVENANT, ATTRIBUTS_MESSAGE, ATTRIBUTS_INFORMATIONS);
	}

	@Override
	protected void ajouterSeparateurIntervenantMessage() {
		texteSmileys.ajouterTexte(SEPARATEUR_INTERVENANT_MESSAGE, ATTRIBUTS_INTERVENANT);
	}

	@Override
	protected void interpreterMessage(String message) {
		texteSmileys.ajouterTexte(message, ATTRIBUTS_MESSAGE);
	}

	@Override
	protected void interpreterTexte(Element elementTexte, DocumentEvent modification) throws BadLocationException {

	}

	@Override
	protected void interpreterIcone(Element elementIcone, DocumentEvent modification) throws BadLocationException {

	}
}
