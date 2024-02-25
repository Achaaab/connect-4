package com.github.achaaab.utilitaire.swing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JPanel;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.WEST;

/**
 * @author Jonathan Guéhenneux
 */
public class LabelComposant extends JPanel {

	private final JLabel label;
	private final Component composant;

	/**
	 * @param label
	 * @param composant
	 */
	public LabelComposant(String label, Component composant) {
		this(label, composant, 0);
	}

	/**
	 * @param label
	 * @param composant
	 * @param espacement
	 */
	public LabelComposant(String label, Component composant, int espacement) {

		setLayout(new BorderLayout(espacement, 0));

		this.label = new JLabel(label);
		this.composant = composant;

		add(this.label, WEST);
		add(composant, CENTER);
	}

	/**
	 * @param label
	 */
	public void setLabel(String label) {
		this.label.setText(label);
	}

	/**
	 * @param foreground
	 */
	public void setForegroundLabel(Color foreground) {
		label.setForeground(foreground);
	}

	/**
	 * @param foreground
	 */
	public void setForegroundComposant(Color foreground) {
		composant.setForeground(foreground);
	}

	@Override
	public void setForeground(Color foreground) {

		if (label != null) {
			label.setForeground(foreground);
		}

		if (composant != null) {
			composant.setForeground(foreground);
		}
	}
}
