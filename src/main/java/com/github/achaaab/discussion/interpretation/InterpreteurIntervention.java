package com.github.achaaab.discussion.interpretation;

import com.github.achaaab.discussion.controle.Intervention;

import javax.swing.event.DocumentEvent;
import javax.swing.text.BadLocationException;

/**
 * @author Jonathan Guéhenneux
 */
public interface InterpreteurIntervention {

	/**
	 * @param intervention
	 * @return
	 */
	int interpreterIntervention(Intervention intervention);

	/**
	 * @param modification
	 * @throws BadLocationException
	 */
	void interpreterDocument(DocumentEvent modification) throws BadLocationException;
}
