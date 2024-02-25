package com.github.achaaab.puissance4.reseau.presentation;

import com.github.achaaab.puissance4.reseau.controle.ServeurPuissance4;
import com.github.achaaab.utilitaire.GestionnaireException;

import javax.swing.JButton;
import javax.swing.JPanel;
import java.io.IOException;

/**
 * @author Jonathan Guéhenneux
 */
public class PresentationCommandesServeur extends JPanel {

	private JButton ouvrir;
	private JButton fermer;
	private JButton annuler;
	private JButton commencer;

	private final ServeurPuissance4 serveur;

	/**
	 * @param serveur
	 */
	public PresentationCommandesServeur(ServeurPuissance4 serveur) {

		this.serveur = serveur;

		creerComposants();
		ajouterComposants();
		ajouterEvenements();
	}

	/**
	 * 
	 */
	private void creerComposants() {

		ouvrir = new JButton("Ouvrir");
		ouvrir.setEnabled(!serveur.isOuvert());

		fermer = new JButton("Fermer");
		fermer.setEnabled(serveur.isOuvert());

		annuler = new JButton("Annuler");

		commencer = new JButton("Commencer");
		commencer.setEnabled(serveur.isComplet());
	}

	/**
	 * 
	 */
	private void ajouterComposants() {

		add(ouvrir);
		add(fermer);
		add(annuler);
		add(commencer);
	}

	/**
	 * 
	 */
	private void ajouterEvenements() {

		ouvrir.addActionListener(evenement -> ouvrir());
		fermer.addActionListener(evenement -> fermer());
		annuler.addActionListener(evenement -> annuler());
	}

	/**
	 * 
	 */
	private void ouvrir() {

		try {
			serveur.ouvrir();
		} catch (IOException erreur) {
			GestionnaireException.traiter(erreur);
		}
	}

	/**
	 * 
	 */
	private void fermer() {

		try {
			serveur.fermer();
		} catch (IOException erreur) {
			GestionnaireException.traiter(erreur);
		}
	}

	/**
	 * 
	 */
	private void annuler() {

		fermer();
		serveur.masquer();
	}

	/**
	 * 
	 * @param ouvrirAutorise
	 */
	public void setOuvrirAutorise(boolean ouvrirAutorise) {
		ouvrir.setEnabled(ouvrirAutorise);
	}

	/**
	 * 
	 * @param fermerAutorise
	 */
	public void setFermerAutorise(boolean fermerAutorise) {
		fermer.setEnabled(fermerAutorise);
	}

	/**
	 * 
	 * @param commencerAutoriser
	 */
	public void setCommencerAutorise(boolean commencerAutoriser) {
		commencer.setEnabled(commencerAutoriser);
	}
}
