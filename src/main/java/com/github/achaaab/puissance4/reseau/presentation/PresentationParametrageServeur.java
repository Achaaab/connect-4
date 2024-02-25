package com.github.achaaab.puissance4.reseau.presentation;

import com.github.achaaab.puissance4.reseau.controle.ServeurPuissance4;
import com.github.achaaab.utilitaire.GestionnaireException;

import javax.swing.BoxLayout;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.text.DecimalFormat;
import java.text.Format;

import static javax.swing.BorderFactory.createTitledBorder;
import static javax.swing.Box.createHorizontalGlue;
import static javax.swing.BoxLayout.LINE_AXIS;

/**
 * @author Jonathan Guéhenneux
 */
public class PresentationParametrageServeur extends JPanel {

	private static final Format FORMAT_PORT = new DecimalFormat("#");

	private JPanel labels;
	private JPanel valeurs;

	private JLabel labelPort;
	private JLabel labelCode;

	private JFormattedTextField port;
	private JTextField code;

	private ServeurPuissance4 serveur;

	/**
	 * @param serveur
	 */
	public PresentationParametrageServeur(ServeurPuissance4 serveur) {

		this.serveur = serveur;

		setBorder(createTitledBorder("Serveur"));

		setLayout(new BoxLayout(this, LINE_AXIS));

		creerComposants();
		ajouterComposants();
	}

	/**
	 * 
	 */
	private void creerComposants() {

		labels = new JPanel();
		labels.setLayout(new GridLayout(2, 1, 5, 5));

		valeurs = new JPanel();
		valeurs.setLayout(new GridLayout(2, 1, 5, 5));

		labelPort = new JLabel("Port : ");
		labelCode = new JLabel("Code : ");

		port = new JFormattedTextField(FORMAT_PORT);
		
		port.setColumns(5);
		port.setText(Integer.toString(serveur.getPort()));

		code = new JTextField(8);
		code.setText(serveur.getCode());
	}

	/**
	 * 
	 */
	private void ajouterComposants() {

		labels.add(labelPort);
		labels.add(labelCode);

		valeurs.add(port);
		valeurs.add(code);

		add(labels);
		add(valeurs);

		add(createHorizontalGlue());
	}

	/**
	 * 
	 * @return
	 */
	public int getPort() {

		int portInteger = 0;

		try {
			portInteger = Integer.parseInt(port.getText());
		} catch (NumberFormatException erreur) {
			GestionnaireException.traiter(erreur);
		}

		return portInteger;
	}

	/**
	 * 
	 * @return
	 */
	public String getCode() {
		return code.getText();
	}
}
