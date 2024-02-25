package com.github.achaaab.utilitaire;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Jonathan Guéhenneux
 */
public class Chronometre {

	private static final int NANOSECONDES_PAR_SECONDE = 1_000_000_000;
	public static final Chronometre DEFAULT_INSTANCE = new Chronometre();

	private final Map<String, Long> temps;

	/**
	 * 
	 */
	public Chronometre() {
		temps = new HashMap<>();
	}

	/**
	 * 
	 * @param cle
	 */
	public void start(String cle) {

		var time = System.nanoTime();
		temps.put(cle, time);
	}

	/**
	 * 
	 * @param cle
	 * @return
	 */
	public float tick(String cle) {

		var t1 = temps.get(cle);
		var t2 = System.nanoTime();
		var t = Float.valueOf(t2 - t1);

		return t / NANOSECONDES_PAR_SECONDE;
	}

	/**
	 * @param cle
	 * @return
	 */
	public float stop(String cle) {

		var secondes = tick(cle);
		temps.remove(cle);
		return secondes;
	}
}
