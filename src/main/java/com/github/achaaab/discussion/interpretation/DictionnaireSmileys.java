package com.github.achaaab.discussion.interpretation;

import com.github.achaaab.exceptions.RessourceManquante;
import com.github.achaaab.puissance4.application.ChargeurRessources;

import javax.swing.Icon;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.github.achaaab.utilitaire.RegexUtilitaire.OU;
import static com.github.achaaab.utilitaire.RegexUtilitaire.echapperMetacaractere;
import static com.github.achaaab.utilitaire.RegexUtilitaire.echapperMetacaracteres;
import static com.github.achaaab.utilitaire.RessourceUtilitaire.getFluxLecture;
import static java.util.regex.Pattern.compile;

/**
 * @author Jonathan Guéhenneux
 */
public class DictionnaireSmileys {

	private static final String CHEMIN_DICTIONNAIRE = "/smileys/dictionnaire.txt";

	private static final Pattern PATTERN_ENTREE_DICTIONNAIRE = compile(
			echapperMetacaractere('{') + "(.+)" + echapperMetacaractere('}') + "(.+)");

	private static final int GROUPE_TEXTE = 1;
	private static final int GROUPE_SMILEY = 2;

	private final Map<String, String> fichiersSmileys;
	private final Pattern patternSmileys;

	/**
	 *
	 */
	public DictionnaireSmileys() {
		this(CHEMIN_DICTIONNAIRE);
	}

	/**
	 * @param cheminDictionnaire
	 * @throws IOException
	 */
	public DictionnaireSmileys(String cheminDictionnaire) {

		fichiersSmileys = new HashMap<>();

		StringBuilder tamponPatternSmileys = new StringBuilder();

		var fluxLecture = getFluxLecture(cheminDictionnaire);
		var fluxLectureParLigne = new BufferedReader(new InputStreamReader(fluxLecture));

		fluxLectureParLigne.lines().forEach(entreeDictionnaire -> {

			var matcherEntreeDictionnaire = PATTERN_ENTREE_DICTIONNAIRE.matcher(entreeDictionnaire);

			if (matcherEntreeDictionnaire.matches()) {

				var texteSmiley = matcherEntreeDictionnaire.group(GROUPE_TEXTE);
				var fichierSmiley = matcherEntreeDictionnaire.group(GROUPE_SMILEY);

				fichiersSmileys.put(texteSmiley, fichierSmiley);

				if (!tamponPatternSmileys.isEmpty()) {
					tamponPatternSmileys.append(OU);
				}

				tamponPatternSmileys.append(echapperMetacaracteres(texteSmiley));
			}
		});

		patternSmileys = compile(tamponPatternSmileys.toString());
	}

	/**
	 * 
	 * @param texteSmiley
	 * @return
	 * @throws RessourceManquante
	 */
	public Icon getSmiley(String texteSmiley) throws RessourceManquante {

		var nomFichier = fichiersSmileys.get(texteSmiley);
		return ChargeurRessources.getSmiley(nomFichier);
	}

	/**
	 * @return
	 */
	public Pattern getPatternSmileys() {
		return patternSmileys;
	}
}
