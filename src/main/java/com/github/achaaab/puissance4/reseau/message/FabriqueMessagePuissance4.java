package com.github.achaaab.puissance4.reseau.message;

import com.github.achaaab.puissance4.reseau.exceptions.MessageInvalide;
import com.github.achaaab.reseau.contrat.FabriqueMessage;
import com.github.achaaab.reseau.contrat.Message;

import static java.lang.Integer.parseInt;
import static com.github.achaaab.reseau.contrat.Message.REGEX_SEPARATEUR;

/**
 * @author Jonathan Guéhenneux
 */
public class FabriqueMessagePuissance4 implements FabriqueMessage {

	private static FabriqueMessagePuissance4 instance;

	/**
	 * @return
	 */
	public static synchronized FabriqueMessagePuissance4 getInstance() {

		if (instance == null) {
			instance = new FabriqueMessagePuissance4();
		}

		return instance;
	}

	/**
	 * 
	 */
	private FabriqueMessagePuissance4() {

	}

	@Override
	public Message deserialiser(String messageSerialise) throws MessageInvalide {

		var donneesMessage = messageSerialise.split(REGEX_SEPARATEUR, -1);

		if (donneesMessage.length == 0) {
			throw new MessageInvalide(messageSerialise);
		}

		var typeMessage = donneesMessage[0];

		Message message;

		switch (typeMessage) {

			case MessageIdentification.TYPE -> {

				var nom = donneesMessage[1];
				var code = donneesMessage[2];

				message = creerMessageIdentification(nom, code);
			}

			case MessageCoup.TYPE -> {

				var colonneString = donneesMessage[1];

				int colonne;

				try {
					colonne = parseInt(colonneString);
				} catch (NumberFormatException cause) {
					throw new MessageInvalide(messageSerialise, cause);
				}

				message = creerMessageCoup(colonne);
			}
			case MessageCodeErrone.TYPE -> message = creerMessageCodeErrone();
			case MessageCodeValide.TYPE -> message = creerMessageCodeValide();
			case MessageDiscussion.TYPE -> {

				var intervenant = donneesMessage[1];
				var texte = donneesMessage[2];

				message = creerMessageDiscussion(intervenant, texte);
			}
			default -> throw new MessageInvalide(messageSerialise);
		}

		return message;
	}

	/**
	 * 
	 * @param intervenant
	 * @param texte
	 * @return
	 */
	public MessageDiscussion creerMessageDiscussion(String intervenant, String texte) {
		return new MessageDiscussion(intervenant, texte);
	}

	/**
	 * 
	 * @return
	 */
	public MessageCodeValide creerMessageCodeValide() {
		return new MessageCodeValide();
	}

	/**
	 * 
	 * @return
	 */
	public MessageCodeErrone creerMessageCodeErrone() {
		return new MessageCodeErrone();
	}

	/**
	 * 
	 * @param nom
	 * @param code
	 * @return
	 */
	public MessageIdentification creerMessageIdentification(String nom, String code) {
		return new MessageIdentification(nom, code);
	}

	/**
	 * 
	 * @param colonne
	 * @return
	 */
	public MessageCoup creerMessageCoup(int colonne) {
		return new MessageCoup(colonne);
	}
}
