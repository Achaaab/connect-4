package com.github.achaaab.utilitaire.swing;

import com.github.achaaab.utilitaire.GestionnaireException;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;
import java.awt.AlphaComposite;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.util.Timer;

import static java.awt.RenderingHints.KEY_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_OFF;
import static java.awt.RenderingHints.VALUE_ANTIALIAS_ON;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static javax.swing.SwingUtilities.invokeAndWait;
import static javax.swing.SwingUtilities.invokeLater;

/**
 * Un dessin est un composant ou on peut dessiner directement par l'intermediaire d'un Graphics2D.
 *
 * @author Jonathan Guéhenneux
 */
public abstract class Dessin extends JComponent {

	private static final Timer TIMER_RAFRAICHISSEMENT = new Timer();

	protected boolean imageAJour;

	protected BufferedImage image;
	protected Graphics2D graphique;

	public int largeur;
	public int hauteur;

	private Rafraichissement rafraichissement;

	/**
	 *
	 */
	public Dessin() {
		imageAJour = false;
	}

	@Override
	public void paintComponent(Graphics graphics) {

		int largeurPanneau = getWidth();
		int hauteurPanneau = getHeight();

		if (image == null || largeurPanneau != largeur || hauteurPanneau != hauteur) {

			creerImage(largeurPanneau, hauteurPanneau);
			imageAJour = false;
		}

		if (!imageAJour) {

			// on efface l'image

			graphique.setComposite(AlphaComposite.Clear);
			graphique.fillRect(0, 0, largeur, hauteur);
			graphique.setComposite(AlphaComposite.SrcOver);

			dessiner();
			imageAJour = true;
		}

		if (isOpaque()) {

			graphics.setColor(getBackground());
			graphics.fillRect(0, 0, largeur, hauteur);
		}

		graphics.drawImage(image, 0, 0, null);
	}

	/**
	 * recalcule l'image puis redessine le composant,
	 * cette methode ne se termine que lorsque le composant est redessine
	 */
	public void recalculerImageSynchrone() {

		imageAJour = false;

		try {
			invokeAndWait(() -> paintImmediately(0, 0, largeur, hauteur));
		} catch (InterruptedException | InvocationTargetException erreur) {
			GestionnaireException.traiter(erreur);
		}
	}

	/**
	 * recalcule l'image puis redessine le composant, cette methode peut se termine avant que le composant ne soit
	 * redessine
	 */
	public void recalculerImageAsynchrone() {

		imageAJour = false;
		invokeLater(this::repaint);
	}

	/**
	 * @param largeur
	 * @param hauteur
	 */
	public void creerImage(int largeur, int hauteur) {

		this.largeur = largeur;
		this.hauteur = hauteur;

		image = new BufferedImage(largeur, hauteur, TYPE_INT_ARGB);
		graphique = image.createGraphics();
	}

	/**
	 *
	 */
	public abstract void dessiner();

	/**
	 *
	 * @param rps nombre de rafraichissements par seconde
	 */
	public void rafraichirAutomatiquement(int rps) {

		interrompreRafraichissementAutomatique();
		rafraichissement = new Rafraichissement(this);
		TIMER_RAFRAICHISSEMENT.schedule(rafraichissement, 0, 1_000 / rps);
	}

	/**
	 *
	 */
	public void interrompreRafraichissementAutomatique() {

		if (rafraichissement != null) {
			rafraichissement.cancel();
		}
	}

	/**
	 * @param anticrenelage
	 */
	public void setAnticrenelage(boolean anticrenelage) {

		graphique.setRenderingHint(KEY_ANTIALIASING,
				anticrenelage ? VALUE_ANTIALIAS_ON : VALUE_ANTIALIAS_OFF);
	}

	/**
	 *
	 * @return
	 */
	public BufferedImage getImage() {
		return image;
	}

	/**
	 *
	 * @return
	 */
	public Graphics2D getGraphique() {
		return graphique;
	}

	/**
	 * @return the imageAJour
	 */
	public boolean isImageAJour() {
		return imageAJour;
	}

	/**
	 * @param imageAJour imageAJour to set
	 */
	public void setImageAJour(boolean imageAJour) {
		this.imageAJour = imageAJour;
	}
}
