package com.github.achaaab.puissance4.presentation.utilitaire;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import static java.awt.Toolkit.getDefaultToolkit;
import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.lang.Math.round;

/**
 * Swing utility methods
 *
 * @author Jonathan Guéhenneux
 * @since 0.0.0
 */
public class SwingUtility {

	private static final Toolkit TOOLKIT = getDefaultToolkit();
	private static final BufferedImage NO_CURSOR_IMAGE = new BufferedImage(1, 1, TYPE_INT_ARGB);
	private static final Cursor NO_CURSOR = TOOLKIT.createCustomCursor(NO_CURSOR_IMAGE, new Point(0, 0), "no_cursor");
	private static final float BASE_RESOLUTION = 72.0f;
	private static final int RESOLUTION = TOOLKIT.getScreenResolution();

	/**
	 * Hides the mouse cursor on the given component.
	 *
	 * @param component component on which to hide the mouse cursor
	 * @since 0.0.0
	 */
	public static void hideCursor(Component component) {
		component.setCursor(NO_CURSOR);
	}

	/**
	 * @param size normalized size for 72 DPI resolution
	 * @return scaled and rounded size
	 * @since 0.0.0
	 */
	public static int scale(float size) {
		return round(scaleFloat(size));
	}

	/**
	 * @param size normalized size for 72 DPI resolution
	 * @return scaled size
	 * @since 0.0.0
	 */
	public static float scaleFloat(float size) {
		return size * RESOLUTION / BASE_RESOLUTION;
	}
}
