package com.github.achaaab.puissance4.reseau.presentation;

import com.github.achaaab.discussion.presentation.PresentationDiscussion;
import com.github.achaaab.exceptions.RessourceManquante;
import com.github.achaaab.puissance4.application.ChargeurRessources;
import com.github.achaaab.puissance4.reseau.controle.ClientPuissance4;
import com.github.achaaab.puissance4.reseau.exceptions.CodeInvalide;
import com.github.achaaab.puissance4.reseau.exceptions.MessageInvalide;
import com.github.achaaab.puissance4.reseau.exceptions.ViolationProtocole;
import com.github.achaaab.utilitaire.GestionnaireException;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;
import java.awt.Color;
import java.awt.GridLayout;
import java.io.IOException;

import static javax.swing.BorderFactory.createTitledBorder;
import static javax.swing.Box.createVerticalGlue;
import static javax.swing.BoxLayout.LINE_AXIS;
import static javax.swing.BoxLayout.PAGE_AXIS;
import static javax.swing.JSplitPane.HORIZONTAL_SPLIT;

/**
 * @author Jonathan Guéhenneux
 */
public class PresentationClientPuissance4 extends JFrame {

	private JSplitPane panneauPrincipal;

	private JPanel parametrage;
	private PresentationDiscussion chat;

	private JPanel panneauClient;
	private JPanel labelsClient;
	private JPanel valeursClient;

	private JPanel panneauServeur;
	private JPanel labelsServeur;
	private JPanel valeursServeur;

	private JLabel labelNom;
	private JTextField nom;

	private JLabel labelStatut;
	private JLabel statut;

	private JLabel labelAdresse;
	private JTextField adresse;

	private JLabel labelPort;
	private JTextField port;

	private JLabel labelCode;
	private JTextField code;

	private JPanel panneauBoutons;

	private JButton connecter;
	private JButton deconnecter;
	private JButton annuler;

	private final ClientPuissance4 client;

	/**
	 * @param client
	 */
	public PresentationClientPuissance4(ClientPuissance4 client) {

		super("Connexion");

		this.client = client;

		creerComposants();
		ajouterComposants();
		ajouterEcouteurs();

		try {
			setIconImage(ChargeurRessources.getIcone("reseau_64.png"));
		} catch (RessourceManquante erreur) {
			GestionnaireException.traiter(erreur);
		}

		pack();
		setLocationRelativeTo(null);
	}

	/**
	 * 
	 */
	private void ajouterEcouteurs() {

		connecter.addActionListener(evenement -> connecter());
		deconnecter.addActionListener(evenement -> deconnecter());

		Document documentNom = nom.getDocument();

		documentNom.addDocumentListener(new DocumentListener() {

			@Override
			public void removeUpdate(DocumentEvent suppression) {
				client.setNom(nom.getText());
			}

			@Override
			public void insertUpdate(DocumentEvent insertion) {
				client.setNom(nom.getText());
			}

			@Override
			public void changedUpdate(DocumentEvent changement) {
				// changement d'attributs - pas de changement de contenu
			}
		});
	}

	/**
	 * 
	 */
	public void connecter() {

		new Thread(() -> {

			try {
				client.connecter();
			} catch (MessageInvalide | IOException | CodeInvalide | ViolationProtocole exception) {
				GestionnaireException.traiter(exception);
			}

		}).start();
	}

	/**
	 * 
	 */
	public void deconnecter() {

		new Thread(() -> {

			try {
				client.deconnecter();
			} catch (IOException erreur) {
				GestionnaireException.traiter(erreur);
			}

		}).start();
	}

	/**
	 * @param statut
	 */
	public void setStatut(String statut) {
		this.statut.setText(statut);
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom.getText();
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {
		this.nom.setText(nom);
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code.getText();
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code.setText(code);
	}

	/**
	 * @return the port
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
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port.setText(Integer.toString(port));
	}

	/**
	 * @return the adresse
	 */
	public String getAdresse() {
		return adresse.getText();
	}

	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(String adresse) {
		this.adresse.setText(adresse);
	}

	/**
	 * 
	 */
	private void ajouterComposants() {

		labelsClient.add(labelNom);
		labelsClient.add(labelStatut);

		valeursClient.add(nom);
		valeursClient.add(statut);

		labelsServeur.add(labelAdresse);
		labelsServeur.add(labelPort);
		labelsServeur.add(labelCode);

		valeursServeur.add(adresse);
		valeursServeur.add(port);
		valeursServeur.add(code);

		panneauClient.add(labelsClient);
		panneauClient.add(valeursClient);
		panneauClient.add(Box.createHorizontalGlue());

		panneauServeur.add(labelsServeur);
		panneauServeur.add(valeursServeur);
		panneauServeur.add(Box.createHorizontalGlue());

		panneauBoutons.add(connecter);
		panneauBoutons.add(deconnecter);
		panneauBoutons.add(annuler);

		parametrage.add(panneauClient);
		parametrage.add(panneauServeur);
		parametrage.add(panneauBoutons);
		parametrage.add(createVerticalGlue());

		panneauPrincipal.setLeftComponent(parametrage);
		panneauPrincipal.setRightComponent(chat);

		add(panneauPrincipal);
	}

	/**
	 * 
	 */
	private void creerComposants() {

		// panneau en deux parties, à gauche le paramétrage, à droite le chat

		panneauPrincipal = new JSplitPane(HORIZONTAL_SPLIT);
		panneauPrincipal.setOneTouchExpandable(true);
		panneauPrincipal.setContinuousLayout(true);
		panneauPrincipal.setDividerSize(10);

		parametrage = new JPanel();
		parametrage.setLayout(new BoxLayout(parametrage, PAGE_AXIS));

		panneauClient = new JPanel();
		panneauClient.setBorder(createTitledBorder("Client"));
		panneauClient.setLayout(new BoxLayout(panneauClient, LINE_AXIS));

		labelsClient = new JPanel();
		labelsClient.setLayout(new GridLayout(2, 1, 5, 5));

		valeursClient = new JPanel();
		valeursClient.setLayout(new GridLayout(2, 1, 5, 5));

		panneauServeur = new JPanel();
		panneauServeur.setBorder(createTitledBorder("Serveur"));
		panneauServeur.setLayout(new BoxLayout(panneauServeur, LINE_AXIS));

		labelsServeur = new JPanel();
		labelsServeur.setLayout(new GridLayout(3, 1, 5, 5));

		valeursServeur = new JPanel();
		valeursServeur.setLayout(new GridLayout(3, 1, 5, 5));

		panneauBoutons = new JPanel();

		labelNom = new JLabel("Nom : ");
		labelStatut = new JLabel("Statut : ");

		nom = new JTextField();
		nom.setText(client.getNom());
		
		statut = new JLabel(client.getStatut().toString());
		statut.setForeground(Color.BLUE);

		labelAdresse = new JLabel("Adresse : ");
		labelPort = new JLabel("Port : ");
		labelCode = new JLabel("Code : ");

		adresse = new JTextField();
		port = new JTextField();
		code = new JTextField();

		connecter = new JButton("Connecter");
		deconnecter = new JButton("Déconnecter");
		annuler = new JButton("Annuler");

		chat = client.getDiscussion().getPresentation();
	}
}
