package com.github.achaaab.multithreading;

/**
 * @author Jonathan Guéhenneux
 */
public interface Tache<P extends TachePartielle> {

	/**
	 * @return {@code true} si la tâche est terminée, {@code false} sinon
	 */
	boolean isComplete();

	/**
	 * @return partie suivante de la tâche
	 */
	P getTachePartielle();

	/**
	 * @return nombre de parties séparables de la tâche
	 */
	int getNombreTachePartielles();
}
