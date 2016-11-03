package de.verbund.sv.guitools;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;

import de.verbund.sv.SpielfeldManagerImplementation;

public class SVButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private BufferedImage imgCursor;
	private Icon imgWasser = RessourceSingleton.getInstance().getImgWasser();
	private Icon imgWasserGetroffen = RessourceSingleton.getInstance().getImgWasserGetroffen();
	private Icon imgSchiff = RessourceSingleton.getInstance().getImgSchiff();
	private Icon imgSchiffGetroffen = RessourceSingleton.getInstance().getImgSchiffGetroffen();

	Map<Number, Icon> imageMap = new HashMap<Number, Icon>();

	public SVButton(int status) {
		super();

		setContentAreaFilled(false);
		// setFocusPainted(false);
		setBorder(new LineBorder(new Color(0, 180, 255)));
		setPreferredSize(new Dimension(30, 30));
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(initCursor(),
				new Point(initCursor().getHeight() / 2, initCursor().getWidth() / 2), "Fadenkreuz");
		setCursor(cursor);

		// Map<Number, Icon> imageMap = new HashMap<Number, Icon>();

		// imgWasser = new ImageIcon("Bilder/Meer_64px.png");
		// imgWasser = RessourceSingleton.getInstance().getImgWasser();
		// imgWasserGetroffen = new
		// ImageIcon("Bilder/explosion_wasser_32px.png");
		// imgWasserGetroffen =
		// RessourceSingleton.getInstance().getImgWasserGetroffen();
		// imgSchiff = new ImageIcon("Bilder/einzelschiff_wasser_32px.png");
		// imgSchiff = RessourceSingleton.getInstance().getImgSchiff();
		// imgSchiffGetroffen = new
		// ImageIcon("Bilder/explosion_schiff_64px.png");
		// imgSchiffGetroffen =
		// RessourceSingleton.getInstance().getImgSchiffGetroffen();

		imageMap.put(SpielfeldManagerImplementation.WASSER, imgWasser);
		imageMap.put(SpielfeldManagerImplementation.WASSER_SCHUSS, imgWasserGetroffen);
		imageMap.put(SpielfeldManagerImplementation.SCHIFF, imgSchiff);
		imageMap.put(SpielfeldManagerImplementation.SCHIFF_SCHUSS, imgSchiffGetroffen);

		setIcon(imageMap.get(status));
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.fillRect(0, 0, getWidth(), getHeight());
		g2.dispose();
		super.paintComponent(g);
	}

	private BufferedImage initCursor() {
		try {
			imgCursor = ImageIO.read(new File("Bilder/maus_cursor_fadenkreuz.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return imgCursor;
	}
}