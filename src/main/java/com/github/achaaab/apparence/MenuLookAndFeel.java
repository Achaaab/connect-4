package com.github.achaaab.apparence;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.plaf.metal.DefaultMetalTheme;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.MetalTheme;
import javax.swing.plaf.metal.OceanTheme;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static javax.swing.UIManager.getInstalledLookAndFeels;
import static javax.swing.UIManager.getLookAndFeel;
import static javax.swing.plaf.metal.MetalLookAndFeel.getCurrentTheme;

/**
 * @author Jonathan Guéhenneux
 */
public class MenuLookAndFeel extends JMenu {

	private static final Map<String, String> CLASSES_LNF;

	/**
	 * nom du look and feel : Metal
	 */
	private static final String NOM_LNF_METAL = "Metal";

	/**
	 * classe du look and feel : Metal
	 */
	private static final String CLASSE_LNF_METAL = MetalLookAndFeel.class.getName();

	/**
	 * liste des thèmes existants pour le look and feel metal
	 */
	private static final List<MetalTheme> THEMES_METAL;

	static {

		/*
		 * on recupère les look and feel installés
		 */

		CLASSES_LNF = new HashMap<>();

		var informationsLnf = getInstalledLookAndFeels();

		String nomLnf;
		String classeLnf;

		for (var informationLnf : informationsLnf) {

			nomLnf = informationLnf.getName();
			classeLnf = informationLnf.getClassName();
			CLASSES_LNF.put(nomLnf, classeLnf);
		}

		// on liste les thèmes disponibles pour le look and feel metal

		THEMES_METAL = new ArrayList<>();
		THEMES_METAL.add(new DefaultMetalTheme());
		THEMES_METAL.add(new OceanTheme());
	}

	/**
	 * 
	 */
	public MenuLookAndFeel() {

		super("Apparence");

		creerComposants();
	}

	/**
	 * 
	 */
	private void creerComposants() {

		var lnfCourant = getLookAndFeel();
		var nomLnfCourant = lnfCourant.getName();
		var nomThemeMetalCourant = getCurrentTheme().getName();
		var lnfCourantMetal = nomLnfCourant.equals(NOM_LNF_METAL);
		var groupeLnf = new ButtonGroup();
		var nomsLnf = CLASSES_LNF.keySet();

		JRadioButtonMenuItem lnf;
		String classeLnf;

		for (var nomLnf : nomsLnf) {

			classeLnf = CLASSES_LNF.get(nomLnf);

			// on ajoute les thèmes "Metal" dans un sous-menu

			if (nomLnf.equals(NOM_LNF_METAL)) {

				var menuMetal = new JMenu(NOM_LNF_METAL);

				String nomThemeMetal;

				for (var themeMetal : THEMES_METAL) {

					nomThemeMetal = themeMetal.getName();

					lnf = new BoutonLookAndFeel(NOM_LNF_METAL, CLASSE_LNF_METAL, themeMetal);
					lnf.setSelected(lnfCourantMetal && nomThemeMetal.equals(nomThemeMetalCourant));

					groupeLnf.add(lnf);
					menuMetal.add(lnf);
				}

				add(menuMetal);

			} else {

				lnf = new BoutonLookAndFeel(nomLnf, classeLnf, null);
				lnf.setSelected(nomLnf.equals(nomLnfCourant));

				groupeLnf.add(lnf);
				add(lnf);
			}
		}
	}
}
