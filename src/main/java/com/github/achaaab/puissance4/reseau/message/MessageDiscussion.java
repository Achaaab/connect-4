package com.github.achaaab.puissance4.reseau.message;

import com.github.achaaab.reseau.contrat.Message;

/**
 * @author Jonathan Guéhenneux
 */
public record MessageDiscussion(String intervenant, String texte) implements Message {

	public static final String TYPE = "DIS";

	/**
	 * @param intervenant
	 * @param texte
	 */
	public MessageDiscussion(String intervenant, String texte) {

		this.intervenant = intervenant.replaceAll(REGEX_SEPARATEUR, " ");
		this.texte = texte.replaceAll(REGEX_SEPARATEUR, " ");
	}

	@Override
	public String serialiser() {
		return TYPE + SEPARATEUR + intervenant + SEPARATEUR + texte;
	}
}
