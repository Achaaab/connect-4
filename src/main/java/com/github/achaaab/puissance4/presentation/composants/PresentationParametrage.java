package com.github.achaaab.puissance4.presentation.composants;

import com.github.achaaab.puissance4.ia.NiveauIa;
import com.github.achaaab.puissance4.moteur.ParametragePartie;
import com.github.achaaab.puissance4.presentation.utilitaire.Couleur;
import com.github.achaaab.puissance4.presentation.utilitaire.RenduJeton;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.GridLayout;

import static javax.swing.BorderFactory.createTitledBorder;
import static com.github.achaaab.puissance4.presentation.utilitaire.Couleur.COULEURS;
import static com.github.achaaab.puissance4.presentation.utilitaire.Couleur.autre;

/**
 * @author Jonathan Guéhenneux
 */
public class PresentationParametrage extends JPanel {

	private final ParametragePartie parametres;

	private JPanel joueur1;
	private JPanel joueur2;

	private JTextField nomJoueur1;
	private JTextField nomJoueur2;

	private JComboBox<Couleur> couleurJoueur1;
	private JComboBox<Couleur> couleurJoueur2;

	private JCheckBox joueur1Ordinateur;
	private JCheckBox joueur2Ordinateur;

	private JComboBox<NiveauIa> niveauJoueur1;
	private JComboBox<NiveauIa> niveauJoueur2;

	private JCheckBox joueur1Deterministe;
	private JCheckBox joueur2Deterministe;

	private PresentationOrdonnancement ordonnancement;

	/**
	 * 
	 * @param parametres
	 */
	public PresentationParametrage(ParametragePartie parametres) {

		this.parametres = parametres;

		/*
		 * Les 3 panneaux d'options (joueur1, joueur2 et partie) seront placés dans une grille
		 * de 3 lignes et 1 colonne. Les colonnes seront espacées de 0 pixel et les lignes seront espacées de 10 pixels.
		 */

		setLayout(new GridLayout(3, 1, 0, 10));
		setBorder(createTitledBorder("Paramètres"));

		creerComposants();
		ajouterComposants();
		ajouterEvenements();
	}

	/**
	 * 
	 */
	public void creerComposants() {

		joueur1 = new JPanel();
		joueur1.setBorder(createTitledBorder("Joueur 1"));
		joueur1.setLayout(new GridLayout(2, 3, 5, 5));

		joueur2 = new JPanel();
		joueur2.setBorder(createTitledBorder("Joueur 2"));
		joueur2.setLayout(new GridLayout(2, 3, 5, 5));

		ordonnancement = parametres.getOrdonnancement().getPresentation();

		nomJoueur1 = new JTextField(parametres.getNomJoueur1());
		nomJoueur2 = new JTextField(parametres.getNomJoueur2());

		couleurJoueur1 = new JComboBox<>(COULEURS);
		couleurJoueur1.setRenderer(RenduJeton.INSTANCE);
		couleurJoueur1.setSelectedItem(parametres.getCouleurJoueur1());

		couleurJoueur2 = new JComboBox<>(COULEURS);
		couleurJoueur2.setRenderer(RenduJeton.INSTANCE);
		couleurJoueur2.setSelectedItem(parametres.getCouleurJoueur2());

		joueur1Ordinateur = new JCheckBox("IA");
		joueur1Ordinateur.setSelected(parametres.isJoueur1Ordinateur());

		joueur2Ordinateur = new JCheckBox("IA");
		joueur2Ordinateur.setSelected(parametres.isJoueur2Ordinateur());

		niveauJoueur1 = new JComboBox<>(NiveauIa.values());
		niveauJoueur1.setSelectedItem(parametres.getNiveauJoueur1());
		niveauJoueur1.setVisible(parametres.isJoueur1Ordinateur());

		niveauJoueur2 = new JComboBox<>(NiveauIa.values());
		niveauJoueur2.setSelectedItem(parametres.getNiveauJoueur2());
		niveauJoueur2.setVisible(parametres.isJoueur2Ordinateur());

		joueur1Deterministe = new JCheckBox("Déterministe");
		joueur1Deterministe.setSelected(parametres.isJoueur1Deterministe());
		joueur1Deterministe.setVisible(parametres.isJoueur1Ordinateur());

		joueur2Deterministe = new JCheckBox("Déterministe");
		joueur2Deterministe.setSelected(parametres.isJoueur2Deterministe());
		joueur2Deterministe.setVisible(parametres.isJoueur2Ordinateur());
	}

	/**
	 * 
	 */
	public void ajouterComposants() {

		joueur1.add(nomJoueur1);
		joueur1.add(couleurJoueur1);
		joueur1.add(joueur1Ordinateur);
		joueur1.add(niveauJoueur1);
		joueur1.add(joueur1Deterministe);

		joueur2.add(nomJoueur2);
		joueur2.add(couleurJoueur2);
		joueur2.add(joueur2Ordinateur);
		joueur2.add(niveauJoueur2);
		joueur2.add(joueur2Deterministe);

		add(joueur1);
		add(joueur2);
		add(ordonnancement);
	}

	/**
	 * 
	 */
	public void ajouterEvenements() {

		couleurJoueur1.addActionListener(evenement -> actualiserCouleurJoueur1());
		couleurJoueur2.addActionListener(evenement -> actualiserCouleurJoueur2());
		joueur1Ordinateur.addChangeListener(evenement -> actualiserJoueur1Ordinateur());
		joueur2Ordinateur.addChangeListener(evenement -> actualiserJoueur2Ordinateur());
	}

	/**
	 * 
	 */
	public void actualiserCouleurJoueur1() {

		var couleur = getCouleurJoueur1();
		var autreCouleur = autre(couleur);
		couleurJoueur2.setSelectedItem(autreCouleur);
	}

	/**
	 * 
	 */
	public void actualiserCouleurJoueur2() {

		var couleur = getCouleurJoueur2();
		var autreCouleur = autre(couleur);
		couleurJoueur1.setSelectedItem(autreCouleur);
	}

	/**
	 * 
	 */
	public void actualiserJoueur1Ordinateur() {

		var joueurOrdinateur = joueur1Ordinateur.isSelected();

		niveauJoueur1.setVisible(joueurOrdinateur);
		joueur1Deterministe.setVisible(joueurOrdinateur);
	}

	/**
	 * 
	 */
	public void actualiserJoueur2Ordinateur() {

		var joueurOrdinateur = joueur2Ordinateur.isSelected();

		niveauJoueur2.setVisible(joueurOrdinateur);
		joueur2Deterministe.setVisible(joueurOrdinateur);
	}

	/**
	 * @return the nomJoueur1
	 */
	public String getNomJoueur1() {
		return nomJoueur1.getText();
	}

	/**
	 * @return the nomJoueur2
	 */
	public String getNomJoueur2() {
		return nomJoueur2.getText();
	}

	/**
	 * @return the joueur1Ordinateur
	 */
	public boolean isJoueur1Ordinateur() {
		return joueur1Ordinateur.isSelected();
	}

	/**
	 * @return the joueur2Ordinateur
	 */
	public boolean isJoueur2Ordinateur() {
		return joueur2Ordinateur.isSelected();
	}

	/**
	 * @return the niveauJoueur1
	 */
	public NiveauIa getNiveauJoueur1() {
		return niveauJoueur1.getItemAt(niveauJoueur1.getSelectedIndex());
	}

	/**
	 * @return the niveauJoueur2
	 */
	public NiveauIa getNiveauJoueur2() {
		return niveauJoueur2.getItemAt(niveauJoueur2.getSelectedIndex());
	}

	/**
	 * @return the couleurJoueur1
	 */
	public Couleur getCouleurJoueur1() {
		return couleurJoueur1.getItemAt(couleurJoueur1.getSelectedIndex());
	}

	/**
	 * @return the couleurJoueur2
	 */
	public Couleur getCouleurJoueur2() {
		return couleurJoueur2.getItemAt(couleurJoueur2.getSelectedIndex());
	}

	/**
	 * @return the joueur1Deterministe
	 */
	public boolean isJoueur1Deterministe() {
		return joueur1Deterministe.isSelected();
	}

	/**
	 * @return the joueur2Deterministe
	 */
	public boolean isJoueur2Deterministe() {
		return joueur2Deterministe.isSelected();
	}

	/**
	 * @return the joueur1Commence
	 */
	public boolean isJoueur1Commence() {
		return ordonnancement.isJoueur1Commence();
	}

	/**
	 * @return the joueur2Commence
	 */
	public boolean isJoueur2Commence() {
		return ordonnancement.isJoueur2Commence();
	}

	/**
	 * @return the premierJoueurAlterne
	 */
	public boolean isPremierJoueurAlterne() {
		return ordonnancement.isPremierJoueurAlterne();
	}

	/**
	 * @return the premierJoueurAleatoire
	 */
	public boolean isPremierJoueurAleatoire() {
		return ordonnancement.isPremierJoueurAleatoire();
	}
}
