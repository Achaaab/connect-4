package com.github.achaaab.reseau.contrat;

/**
 * @author Jonathan Guéhenneux
 */
public interface FabriqueMessage {

	/**
	 * @param messageSerialise
	 * @return
	 */
	Message deserialiser(String messageSerialise) throws Exception;
}
