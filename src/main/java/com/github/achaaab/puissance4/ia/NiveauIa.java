package com.github.achaaab.puissance4.ia;

/**
 * @author Jonathan Guéhenneux
 */
public enum NiveauIa {

	DEBUTANT("Débutant", 1, false),
	INITIE("Initié", 2, false),
	CONFIRME("Confirmé", 3, false),
	SHODAN("Expert - shodan", 5, false),
	NIDAN("Expert - nidan", 7, false),
	SANDAN("Expert - sandan", 9, false),
	YONDAN("Expert - yondan", 11, false),
	GODAN("Expert - godan", 13, false),
	ROKUDAN("Expert - rokudan", 15, false),
	SHICHIDAN("Expert - shichidan", 16, false),
	HACHIDAN("Expert - hachidan", 17, false),
	KYUDAN("Expert - kyudan", 18, false),
	JUDAN("Expert - judan", 19, false),
	DEMENTIEL("Démentiel", 20, true),
	TEST("Test", 25, false);

	private final String libelle;
	private final int profondeurBase;
	private final boolean ameliorationProgressive;

	/**
	 * @param libelle
	 * @param profondeurBase
	 * @param ameliorationProgressive
	 */
	NiveauIa(String libelle, int profondeurBase, boolean ameliorationProgressive) {

		this.libelle = libelle;
		this.profondeurBase = profondeurBase;
		this.ameliorationProgressive = ameliorationProgressive;
	}

	/**
	 * 
	 * @param nombreJetonsJoues
	 * @return
	 */
	public int getProfondeurMaximum(int nombreJetonsJoues) {

		int profondeurMaximum = profondeurBase;

		if (ameliorationProgressive) {
			profondeurMaximum += nombreJetonsJoues / 4;
		}

		return profondeurMaximum;
	}

	@Override
	public String toString() {
		return libelle;
	}
}
