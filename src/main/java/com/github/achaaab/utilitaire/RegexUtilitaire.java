package com.github.achaaab.utilitaire;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author guehenneux
 * 
 */
public class RegexUtilitaire {

	public static final char OU = '|';

	public static final char CARACTERE_ECHAPPEMENT = '\\';

	/**
	 * liste des metacaracteres utilises dans les expressions regulieres
	 */
	private static final char[] METACARACTERES = { '.', '*', '?', '+', OU, '(',
			')', '{', '}', '[', ']', CARACTERE_ECHAPPEMENT };

	private static final Pattern PATTERN_METACARACTERES;

	static {

		StringBuilder tampon = new StringBuilder();

		for (char metacaractere : METACARACTERES) {

			if (tampon.length() > 0) {
				tampon.append(OU);
			}

			tampon.append(echapperMetacaractere(metacaractere));
		}

		PATTERN_METACARACTERES = Pattern.compile(tampon.toString());

	}

	/**
	 * 
	 * @param metacaractere
	 * @return
	 */
	public static final String echapperMetacaractere(char metacaractere) {
		return Character.toString(CARACTERE_ECHAPPEMENT) + metacaractere;
	}

	/**
	 * 
	 * @param chaine
	 * @return
	 */
	public static String echapperMetacaracteres(String chaine) {

		Matcher matcherMetacaracteres = PATTERN_METACARACTERES.matcher(chaine);

		StringBuffer tampon = new StringBuffer();

		char metacaractere;
		String metacaractereEchappe;

		String sousChaineIgnoree;
		int indexDebutLecture = 0;
		int indexDebutGroupe;

		/*
		 * ici, je n'ai pas utilise la methode appendReplacement qui remplacait
		 * les "\\" par des "\", je ne sais pas pourquoi
		 */

		while (matcherMetacaracteres.find()) {

			/*
			 * index du metacaractere
			 */

			indexDebutGroupe = matcherMetacaracteres.start();

			/*
			 * sous-chaine sans metacaracteres
			 */

			sousChaineIgnoree = chaine.substring(indexDebutLecture,
					indexDebutGroupe);

			/*
			 * on conserve telles quelles les sous-chaines sans metacaracteres
			 */

			tampon.append(sousChaineIgnoree);

			/*
			 * on recupere le metacaractere
			 */

			metacaractere = matcherMetacaracteres.group().charAt(0);

			/*
			 * on le prefixe avec le caractere d'echappement
			 */

			metacaractereEchappe = echapperMetacaractere(metacaractere);

			/*
			 * on rajoute le metacaractere echappe
			 */

			tampon.append(metacaractereEchappe);

			/*
			 * on positionne l'index de debut de lecture apres le metacaractere
			 * rencontre, pour l'iteration suivante
			 */

			indexDebutLecture = matcherMetacaracteres.end();

		}

		/*
		 * on rajoute la derniere sous-chaine sans metacaracteres
		 */

		sousChaineIgnoree = chaine.substring(indexDebutLecture);
		tampon.append(sousChaineIgnoree);

		return tampon.toString();

	}

}