package com.github.achaaab.reseau.contrat;

/**
 * @author Jonathan Guéhenneux
 */
public enum StatutClient {

	DECONNECTE("déconnecté"),
	CONNEXION_EN_COURS("connexion en cours..."),
	CONNEXION_ETABLIE("connexion établie");

	private final String libelle;

	/**
	 * @param libelle
	 */
	StatutClient(String libelle) {
		this.libelle = libelle;
	}

	@Override
	public String toString() {
		return libelle;
	}
}
