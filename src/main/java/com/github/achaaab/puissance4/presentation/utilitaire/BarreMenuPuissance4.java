package com.github.achaaab.puissance4.presentation.utilitaire;

import com.github.achaaab.apparence.MenuLookAndFeel;
import com.github.achaaab.puissance4.moteur.Puissance4;
import com.github.achaaab.utilitaire.GestionnaireException;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.io.IOException;

import static com.github.achaaab.puissance4.moteur.Puissance4.GRILLE;

/**
 * @author Jonathan Guéhenneux
 */
public class BarreMenuPuissance4 extends JMenuBar {

	private final Puissance4 puissance4;

	private JMenu menuPuissance4;
	private JMenu menuPartie;
	private JMenu menuOptions;
	private JMenu menuLnf;

	private JMenuItem quitter;
	private JMenuItem creer;
	private JMenuItem rejoindre;
	private JMenuItem heberger;

	private JMenuItem mancheSuivante;
	private JMenuItem animationChute;

	private final JFrame nouvellePartie;

	/**
	 * @param puissance4
	 * @param nouvellePartie
	 */
	public BarreMenuPuissance4(Puissance4 puissance4, JFrame nouvellePartie) {

		this.puissance4 = puissance4;
		this.nouvellePartie = nouvellePartie;

		creerComposants();
		ajouterComposants();
		ajouterEvenements();
	}

	/**
	 * 
	 */
	public void autoriserMancheSuivante() {
		mancheSuivante.setEnabled(true);
	}

	/**
	 * 
	 */
	private void creerComposants() {

		menuPuissance4 = new JMenu("Puissance 4");
		menuPartie = new JMenu("Partie");
		menuOptions = new JMenu("Options");
		menuLnf = new MenuLookAndFeel();

		quitter = new JMenuItem("Quitter");
		creer = new JMenuItem("Créer une partie");
		rejoindre = new JMenuItem("Rejoindre une partie");
		heberger = new JMenuItem("Héberger une partie");

		mancheSuivante = new JMenuItem("Manche suivante");
		mancheSuivante.setEnabled(false);

		animationChute = new JCheckBoxMenuItem("Animation de chute");
		animationChute.setSelected(GRILLE.isAnimationChute());
	}

	/**
	 * 
	 */
	private void ajouterComposants() {

		menuPuissance4.add(creer);
		menuPuissance4.add(rejoindre);
		menuPuissance4.add(heberger);
		menuPuissance4.addSeparator();
		menuPuissance4.add(quitter);

		menuPartie.add(mancheSuivante);

		menuOptions.add(animationChute);

		add(menuPuissance4);
		add(menuLnf);
		add(menuPartie);
		add(menuOptions);
	}

	/**
	 * 
	 */
	private void ajouterEvenements() {

		quitter.addActionListener(evenement -> quitter());
		creer.addActionListener(evenement -> new Thread(this::creerPartie).start());
		rejoindre.addActionListener(evenement -> rejoindrePartie());
		heberger.addActionListener(evenement -> hebergerPartie());
		mancheSuivante.addActionListener(evenement -> new Thread(this::mancheSuivante).start());
		animationChute.addChangeListener(evenement -> setAnimationChute(animationChute.isSelected()));
	}

	/**
	 * 
	 */
	private void quitter() {
		puissance4.quitter();
	}

	/**
	 * 
	 */
	public void creerPartie() {
		nouvellePartie.setVisible(true);
	}

	/**
	 * 
	 */
	public void rejoindrePartie() {
		puissance4.rejoindrePartie();
	}

	/**
	 * 
	 */
	public void hebergerPartie() {

		try {
			puissance4.hebergerPartie();
		} catch (IOException erreur) {
			GestionnaireException.traiter(erreur);
		}
	}

	/**
	 * 
	 */
	private void mancheSuivante() {
		puissance4.mancheSuivante();
	}

	/**
	 * 
	 */
	public void interdireMancheSuivante() {
		mancheSuivante.setEnabled(false);
	}

	/**
	 * 
	 * @param animationChute
	 */
	public void setAnimationChute(boolean animationChute) {
		GRILLE.setAnimationChute(animationChute);
	}
}
