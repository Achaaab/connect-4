package com.github.achaaab.discussion.interpretation;

import com.github.achaaab.discussion.controle.Intervention;
import com.github.achaaab.discussion.presentation.TexteSmileys;

import javax.swing.event.DocumentEvent;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class InterpreteurInterventionAbstrait implements InterpreteurIntervention {

	protected TexteSmileys texteSmileys;
	protected AttributeSet attributsIntervenant;
	protected AttributeSet attributsMessage;
	protected AttributeSet attributsInformations;

	/**
	 * @param texte
	 * @param attributsIntervenant
	 * @param attributsMessage
	 * @param attributsInformations
	 */
	public InterpreteurInterventionAbstrait(TexteSmileys texte, AttributeSet attributsIntervenant,
			AttributeSet attributsMessage, AttributeSet attributsInformations) {

		this.texteSmileys = texte;
		this.attributsIntervenant = attributsIntervenant;
		this.attributsMessage = attributsMessage;
		this.attributsInformations = attributsInformations;
	}

	@Override
	public int interpreterIntervention(Intervention intervention) {

		intervention.dater();

		int longueurDiscussionAvant = texteSmileys.getLongueur();

		String intervenant = intervention.getIntervenant();
		String message = intervention.getMessage();
		String date = intervention.getDate();

		interpreterIntervenant(intervenant);
		interpreterInformations(date);
		ajouterSeparateurIntervenantMessage();
		interpreterMessage(message);
		texteSmileys.nouvelleLigne();

		int longueurDiscussionApres = texteSmileys.getLongueur();

		return longueurDiscussionApres - longueurDiscussionAvant;
	}

	@Override
	public void interpreterDocument(DocumentEvent modification) throws BadLocationException {

		texteSmileys.supprimerEcouteur();

		StyledDocument document = texteSmileys.getStyledDocument();

		Element[] elementsRacines = document.getRootElements();

		for (Element elementRacine : elementsRacines) {
			interpreterElement(elementRacine, modification);
		}

		texteSmileys.ajouterEcouteur();
	}

	/**
	 * @param element
	 * @param modification
	 * @throws BadLocationException
	 */
	private void interpreterElement(Element element, DocumentEvent modification) throws BadLocationException {

		if (element != null) {

			if (element.isLeaf()) {

				String nomElement = element.getName();

				if (nomElement.equals(AbstractDocument.ContentElementName)) {
					interpreterTexte(element, modification);
				} else if (nomElement.equals(StyleConstants.IconElementName)) {
					interpreterIcone(element, modification);
				}

			} else {

				int nombreElementsFils = element.getElementCount();
				Element elementFils;

				for (int indexElementFils = 0; indexElementFils < nombreElementsFils; indexElementFils++) {

					elementFils = element.getElement(indexElementFils);
					interpreterElement(elementFils, modification);
				}
			}
		}
	}

	/**
	 * @param elementTexte
	 * @param modification
	 * @throws BadLocationException
	 */
	protected abstract void interpreterTexte(Element elementTexte, DocumentEvent modification)
			throws BadLocationException;

	/**
	 * @param elementIcone
	 * @param modification
	 * @throws BadLocationException
	 */
	protected abstract void interpreterIcone(Element elementIcone, DocumentEvent modification)
			throws BadLocationException;

	/**
	 * @param intervenant
	 */
	protected void interpreterIntervenant(String intervenant) {
		texteSmileys.ajouterTexte(intervenant, attributsIntervenant);
	}

	/**
	 * @param date
	 */
	protected void interpreterInformations(String date) {

		texteSmileys.ajouterTexte(" a dit ", attributsInformations);
		texteSmileys.ajouterTexte(date, attributsInformations);
	}

	/**
	 * 
	 */
	protected abstract void ajouterSeparateurIntervenantMessage();

	/**
	 * 
	 * @param message
	 */
	protected abstract void interpreterMessage(String message);
}
