package com.github.achaaab.puissance4.reseau.controle;

import com.github.achaaab.discussion.controle.Discussion;
import com.github.achaaab.puissance4.presentation.utilitaire.Couleur;
import com.github.achaaab.puissance4.reseau.presentation.PresentationJoueurReseau;

/**
 * @author Jonathan Guéhenneux
 */
public class ParametrageJoueurReseau {

	private final PresentationJoueurReseau presentation;
	private final boolean joueurServeur;

	private boolean connecte;
	private String nom;
	private Couleur couleur;
	private Discussion discussion;
	private ServeurPuissance4 serveur;

	/**
	 * @param nom
	 * @param titre
	 * @param joueurServeur
	 * @param couleur
	 */
	public ParametrageJoueurReseau(String nom, String titre, boolean joueurServeur, Couleur couleur) {

		this.nom = nom;
		this.couleur = couleur;
		this.joueurServeur = joueurServeur;

		presentation = new PresentationJoueurReseau(this, titre, joueurServeur);
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(String nom) {

		this.nom = nom;

		if (discussion != null) {
			discussion.setIntervenant(nom);
		}

		if (!joueurServeur) {
			presentation.setNom(nom);
		}
	}

	/**
	 * @return the couleur
	 */
	public Couleur getCouleur() {
		return couleur;
	}

	/**
	 * @param couleur
	 * @param modificationPresentation Indique si la modification de couleur vient de la présentation,
	 * si c'est le cas il faut actualiser la couleur sur le paramétrage du serveur.
	 * Sinon, la modification vient du serveur, il faut actualiser la presentation.
	 */
	public void setCouleur(Couleur couleur, boolean modificationPresentation) {

		this.couleur = couleur;

		if (modificationPresentation) {
			serveur.setCouleur(this, couleur);
		} else {
			presentation.setCouleur(couleur);
		}
	}

	/**
	 * @param connecte the connecte to set
	 */
	public void setConnecte(boolean connecte) {
		this.connecte = connecte;
	}

	/**
	 * @param discussion discussion
	 */
	public void setDiscussion(Discussion discussion) {
		this.discussion = discussion;
	}

	/**
	 * @param serveur the serveur to set
	 */
	public void setServeur(ServeurPuissance4 serveur) {
		this.serveur = serveur;
	}

	/**
	 * @return the presentation
	 */
	public PresentationJoueurReseau getPresentation() {
		return presentation;
	}

	/**
	 * @return
	 */
	public boolean isConnecte() {
		return connecte;
	}
}
