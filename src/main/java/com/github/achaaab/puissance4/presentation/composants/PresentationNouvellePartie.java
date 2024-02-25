package com.github.achaaab.puissance4.presentation.composants;

import com.github.achaaab.exceptions.RessourceManquante;
import com.github.achaaab.puissance4.application.ChargeurRessources;
import com.github.achaaab.puissance4.moteur.ParametragePartie;
import com.github.achaaab.puissance4.moteur.Puissance4;
import com.github.achaaab.utilitaire.GestionnaireException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;

import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.SOUTH;

/**
 * @author Jonathan Guéhenneux
 */
public class PresentationNouvellePartie extends JFrame {

	private final Puissance4 puissance4;
	private final ParametragePartie parametrage;

	private PresentationParametrage presentationParametrage;

	private JPanel boutons;
	private JButton commencer;
	private JButton annuler;

	/**
	 * @param puissance4
	 */
	public PresentationNouvellePartie(Puissance4 puissance4) {

		setTitle("Nouvelle partie");

		try {
			setIconImage(ChargeurRessources.getIcone("puissance4_64.png"));
		} catch (RessourceManquante exception) {
			GestionnaireException.traiter(exception);
		}

		this.puissance4 = puissance4;

		parametrage = puissance4.getParametrage();

		setLayout(new BorderLayout());

		creerComposants();
		ajouterComposants();
		ajouterEvenements();

		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * 
	 */
	public void creerComposants() {

		presentationParametrage = parametrage.getPresentation();

		boutons = new JPanel();
		commencer = new JButton("Commencer");
		annuler = new JButton("Annuler");
	}

	/**
	 * 
	 */
	public void ajouterComposants() {

		boutons.add(commencer);
		boutons.add(annuler);

		add(presentationParametrage, CENTER);
		add(boutons, SOUTH);
	}

	/**
	 * 
	 */
	public void ajouterEvenements() {

		commencer.addActionListener(evenement -> commencer());
		annuler.addActionListener(evenement -> annuler());
	}

	/**
	 * 
	 */
	public void annuler() {
		setVisible(false);
	}

	/**
	 * 
	 */
	public void commencer() {

		new Thread(() -> {

			setVisible(false);
			puissance4.nouvellePartie();

		}).start();
	}
}
