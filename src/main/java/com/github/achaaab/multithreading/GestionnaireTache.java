package com.github.achaaab.multithreading;

/**
 * @author Jonathan Guéhenneux
 */
public abstract class GestionnaireTache<P extends TachePartielle, T extends Tache<P>> {

	protected T tache;

	/**
	 * @param tache tâche à effectuer
	 */
	public GestionnaireTache(T tache) {
		this.tache = tache;
	}

	/**
	 * Lance l'exécution de la tâche.
	 * 
	 * @throws InterruptedException
	 */
	public abstract void executer() throws InterruptedException;

	/**
	 * @return tâche à effectuer
	 */
	public T getTache() {
		return tache;
	}
}
