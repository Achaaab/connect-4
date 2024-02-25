package com.github.achaaab.puissance4.application;

import com.github.achaaab.exceptions.RessourceManquante;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.awt.Image;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.github.achaaab.utilitaire.RessourceUtilitaire.getUrlRessource;

/**
 * @author Jonathan Guéhenneux
 */
public class ChargeurRessources {

	private static final String SEPARATEUR_DOSSIERS = "/";
	private static final String DOSSIER_ICONES = "/icones";
	private static final String DOSSIER_SMILEYS = "/smileys";

	private static final Map<String, Image> IMAGES = new HashMap<>();

	/**
	 * @param cheminImage
	 * @return
	 * @throws RessourceManquante
	 */
	private static Image chargerImage(String cheminImage) throws RessourceManquante {

		Image image;

		try {

			var urlIcone = getUrlRessource(cheminImage);
			image = ImageIO.read(urlIcone);

		} catch (IOException cause) {

			throw new RessourceManquante(cheminImage, cause);
		}

		return image;
	}

	/**
	 * @param nomFichier
	 * @return
	 * @throws RessourceManquante
	 */
	public static Icon getSmiley(String nomFichier) throws RessourceManquante {

		var imageSmiley = getImage(DOSSIER_SMILEYS, nomFichier);

		return new ImageIcon(imageSmiley);
	}

	/**
	 * @param nomFichier
	 * @return
	 * @throws RessourceManquante
	 */
	public static Image getIcone(String nomFichier) throws RessourceManquante {
		return getImage(DOSSIER_ICONES, nomFichier);
	}

	/**
	 * @param nomFichier
	 * @return
	 * @throws RessourceManquante
	 */
	public static Image getImage(String nomDossier, String nomFichier) throws RessourceManquante {

		var cheminImage = nomDossier + SEPARATEUR_DOSSIERS + nomFichier;

		Image image = IMAGES.get(cheminImage);

		if (image == null) {

			image = chargerImage(cheminImage);
			IMAGES.put(cheminImage, image);
		}

		return image;
	}
}
