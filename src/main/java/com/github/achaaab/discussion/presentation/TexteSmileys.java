package com.github.achaaab.discussion.presentation;

import javax.swing.Icon;
import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Element;
import javax.swing.text.SimpleAttributeSet;

import com.github.achaaab.utilitaire.GestionnaireException;

import static java.lang.System.lineSeparator;

/**
 * @author Jonathan Guéhenneux
 */
public class TexteSmileys extends JTextPane {

	private static final AttributeSet POLICE_DEFAUT = new SimpleAttributeSet();
	protected static final String SAUT_LIGNE = lineSeparator();

	protected Document document;

	/**
	 * 
	 */
	public TexteSmileys() {
		document = getStyledDocument();
	}

	/**
	 * @param smiley
	 */
	public void ajouterSmiley(Icon smiley) {
		insererSmiley(document.getLength(), smiley);
	}

	/**
	 * @param position
	 * @param smiley
	 */
	public void insererSmiley(int position, Icon smiley) {

		setCaretPosition(position);
		insertIcon(smiley);
	}

	/**
	 * @param element
	 * @return
	 * @throws BadLocationException
	 */
	public String supprimer(Element element) throws BadLocationException {

		// on récupère les coordonnées de l'élément

		int indexDebutElement = element.getStartOffset();
		int indexFinElement = element.getEndOffset();
		int longueurElement = indexFinElement - indexDebutElement;

		if (indexFinElement > getLongueur()) {

			/*
			 * correction d'un comportement étrange de java
			 * 
			 * voir javadoc javaw.swing.text.Element getEndOffset()
			 */

			longueurElement--;
		}

		// on supprime le texte dans le document

		var texte = document.getText(indexDebutElement, longueurElement);
		document.remove(indexDebutElement, longueurElement);

		return texte;
	}

	/**
	 * 
	 */
	public void nouvelleLigne() {
		ajouterTexte(SAUT_LIGNE, POLICE_DEFAUT);
	}

	/**
	 * @param texte
	 * @param attributs
	 */
	public void ajouterTexte(String texte, AttributeSet attributs) {
		insererTexte(document.getLength(), texte, attributs);
	}

	/**
	 * 
	 * @param position
	 * @param texte
	 * @param attributs
	 */
	public void insererTexte(int position, String texte, AttributeSet attributs) {

		try {
			document.insertString(position, texte, attributs);
		} catch (BadLocationException erreur) {
			GestionnaireException.traiter(erreur);
		}
	}

	/**
	 * @return longueur de la discussion (c'est-à-dire, le nombre de caractères - noms des intervenants compris -
	 * chaque smiley compte pour 1 caractère)
	 */
	public int getLongueur() {
		return document.getLength();
	}

	/**
	 * 
	 */
	public void ajouterEcouteur() {

	}

	/**
	 * 
	 */
	public void supprimerEcouteur() {

	}
}
