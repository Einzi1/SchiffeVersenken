package de.verbund.sv.guitools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

public class RessourceSingleton {

	private static RessourceSingleton instance;
	private BufferedImage imgCursor;
	private Icon imgWasser;
	private Icon imgWasserGetroffen;
	private Icon imgSchiff;
	private Icon imgSchiffGetroffen;

	private RessourceSingleton() {
		ladeBilder();
	}

	private void ladeBilder() {
		try {
			imgCursor = ImageIO.read(new File("Bilder/einzelschiff_32px_neu.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		imgWasser = new ImageIcon("Bilder/Meer_64px.png");
		imgWasserGetroffen = new ImageIcon("Bilder/explosion_wasser_32px.png");
		imgSchiff = new ImageIcon("Bilder/einzelschiff_wasser_32px.png");
		imgSchiffGetroffen = new ImageIcon("Bilder/explosion_schiff_64px.png");
	}

	public static RessourceSingleton getInstance() {
		if (instance == null) {
			instance = new RessourceSingleton();
		}
		return instance;
	}

	public BufferedImage getImgCursor() {
		return imgCursor;
	}

	public Icon getImgWasser() {
		return imgWasser;
	}

	public Icon getImgWasserGetroffen() {
		return imgWasserGetroffen;
	}

	public Icon getImgSchiff() {
		return imgSchiff;
	}

	public Icon getImgSchiffGetroffen() {
		return imgSchiffGetroffen;
	}
}