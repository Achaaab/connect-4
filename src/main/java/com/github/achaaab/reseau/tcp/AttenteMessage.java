package com.github.achaaab.reseau.tcp;

import com.github.achaaab.puissance4.reseau.exceptions.MessageInvalide;
import com.github.achaaab.reseau.contrat.Connexion;
import com.github.achaaab.reseau.contrat.Entite;
import com.github.achaaab.utilitaire.GestionnaireException;
import com.github.achaaab.utilitaire.ProcessusInterruptible;

import java.io.IOException;

/**
 * @author Jonathan Guéhenneux
 */
public class AttenteMessage extends ProcessusInterruptible {

	private final Connexion connexion;
	private final Entite entite;

	/**
	 * @param connexion
	 * @param entite
	 */
	public AttenteMessage(Connexion connexion, Entite entite) {

		this.connexion = connexion;
		this.entite = entite;
	}

	@Override
	public void boucle() {

		try {

			var message = connexion.lireMessage();
			entite.recevoirMessage(connexion, message);

		} catch (MessageInvalide messageInvalide) {

			GestionnaireException.traiter(messageInvalide);

		} catch (IOException ioException) {

			GestionnaireException.traiter(ioException);
			interrompre();
		}
	}
}
