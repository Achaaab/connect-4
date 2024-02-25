package com.github.achaaab.apparence;

import com.github.achaaab.utilitaire.GestionnaireException;

import javax.swing.plaf.metal.MetalTheme;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.github.achaaab.apparence.LookAndFeelUtilitaire.setLookAndFeelParClasse;
import static javax.swing.plaf.metal.MetalLookAndFeel.setCurrentTheme;

/**
 * @author Jonathan Guéhenneux
 */
public class EcouteurChangementLnf implements ActionListener {

	private final String classeLnf;
	private final MetalTheme theme;

	/**
	 * @param classeLnf
	 * @param theme optionnel (renseigné seulement pour le Look And feel Metal)
	 */
	public EcouteurChangementLnf(String classeLnf, MetalTheme theme) {

		this.classeLnf = classeLnf;
		this.theme = theme;
	}

	@Override
	public void actionPerformed(ActionEvent evenement) {

		try {

			if (theme != null) {
				setCurrentTheme(theme);
			}

			setLookAndFeelParClasse(classeLnf);

		} catch (Exception exception) {

			GestionnaireException.traiter(exception);
		}
	}
}
