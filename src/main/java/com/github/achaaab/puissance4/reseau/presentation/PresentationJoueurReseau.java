package com.github.achaaab.puissance4.reseau.presentation;

import com.github.achaaab.puissance4.presentation.utilitaire.Couleur;
import com.github.achaaab.puissance4.presentation.utilitaire.RenduJeton;
import com.github.achaaab.puissance4.reseau.controle.ParametrageJoueurReseau;
import com.github.achaaab.utilitaire.swing.LabelComposant;
import com.github.achaaab.utilitaire.swing.PackablePanel;

import javax.swing.JComboBox;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.GridLayout;

import static javax.swing.BorderFactory.createTitledBorder;
import static com.github.achaaab.puissance4.presentation.utilitaire.Couleur.COULEURS;
import static com.github.achaaab.puissance4.presentation.utilitaire.SwingUtility.scale;

/**
 * @author Jonathan Guéhenneux
 */
public class PresentationJoueurReseau extends PackablePanel {

	private final boolean joueurHote;
	private final ParametrageJoueurReseau parametrage;

	private JProgressBar barreAttente;
	private JTextField nom;
	private JComboBox<Couleur> couleur;

	/**
	 * @param parametrage
	 * @param titre
	 * @param joueurHote
	 */
	public PresentationJoueurReseau(ParametrageJoueurReseau parametrage, String titre, boolean joueurHote) {

		this.parametrage = parametrage;
		this.joueurHote = joueurHote;

		setBorder(createTitledBorder(titre));

		int nombreLignes = joueurHote ? 2 : 3;

		setLayout(new GridLayout(nombreLignes, 1, 0, 5));

		creerComposants();
		ajouterComposants();
		ajouterEvenements();
	}

	/**
	 * @param barreAttenteVisible
	 */
	public void setBarreAttenteVisible(boolean barreAttenteVisible) {

		barreAttente.setVisible(barreAttenteVisible);
		pack();
	}

	/**
	 * 
	 */
	private void creerComposants() {

		if (!joueurHote) {

			barreAttente = new JProgressBar();
			barreAttente.setIndeterminate(true);
			barreAttente.setVisible(false);
			barreAttente.setString("En attente...");
			barreAttente.setStringPainted(true);
		}

		nom = new JTextField(16);
		nom.setEditable(joueurHote);
		nom.setText(parametrage.getNom());

		couleur = new JComboBox<>(COULEURS);
		couleur.setRenderer(RenduJeton.INSTANCE);
		couleur.setSelectedItem(parametrage.getCouleur());
	}

	/**
	 * 
	 */
	private void ajouterComposants() {

		if (!joueurHote) {
			add(barreAttente);
		}

		add(new LabelComposant("Nom : ", nom, scale(10)));
		add(new LabelComposant("Couleur : ", couleur, scale(10)));
	}

	/**
	 * 
	 */
	private void ajouterEvenements() {

		if (joueurHote) {

			var documentNom = nom.getDocument();

			documentNom.addDocumentListener(new DocumentListener() {

				@Override
				public void removeUpdate(DocumentEvent suppression) {
					parametrage.setNom(nom.getText());
				}

				@Override
				public void insertUpdate(DocumentEvent insertion) {
					parametrage.setNom(nom.getText());
				}

				@Override
				public void changedUpdate(DocumentEvent changement) {
					// changement d'attributs - pas de changement de contenu
				}
			});
		}

		couleur.addActionListener(evenement -> parametrage.setCouleur(getCouleur(), true));
	}

	/**
	 * @return couleur sélectionnée par le joueur
	 */
	public Couleur getCouleur() {
		return couleur.getItemAt(couleur.getSelectedIndex());
	}

	/**
	 * @param couleur
	 */
	public void setCouleur(Couleur couleur) {
		this.couleur.setSelectedItem(couleur);
	}

	/**
	 * 
	 * @param nom
	 */
	public void setNom(String nom) {
		this.nom.setText(nom);
	}
}
