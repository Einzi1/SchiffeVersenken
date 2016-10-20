package de.verbund.sv;

import de.verbund.sv.tools.Koordinaten;
import de.verbund.sv.tools.Schiff;

public interface SpielbrettManager {
	public final static int WASSER = 0;
	public final static int WASSER_SCHUSS = 1;
	public final static int SCHIFF = 2;
	public final static int SCHIFF_SCHUSS = 3; // = Schiff ist angeschossen

	/**
	 * Schießt eine Zelle an und liefert ihren Status als {@code int} zurück
	 * 
	 * @param koordinaten
	 *            der Zelle, die angeschossen wird
	 * @return WASSER = 0; WASSER_SCHUSS = 1; SCHIFF = 2; SCHIFF_SCHUSS = 3;
	 * @throws SchussSchonAbgegebenException,
	 *             wenn die Zelle schon angeschossen wurde (doppelter Schuss
	 *             nicht erlaubt)
	 */
	public int schussAuf(Koordinaten koordinaten) throws SchussSchonAbgegebenException;

	/**
	 * 
	 * @param startkoordianten
	 *            ein Koordinaten-Objekt (int x, int y) mit den
	 *            Anfangskoordinaten
	 * @param schiff
	 *            ein Schiff-Objekt (int laenge, boolean waagrechteAusrichtung)
	 * @return {@code true}, wenn Schiff erfolgreich gesetzt wurde,
	 *         {@code false}, wenn das Schiff nicht setzbar ist
	 */
	public boolean setzeSchiff(Koordinaten startkoordianten, Schiff schiff);

	/**
	 * Liefert einen {@code int} zurück, der den Zustand der Zelle beschreibt
	 * 
	 * @param koordinaten
	 *            der Zelle, die angeschossen wird
	 * @return WASSER = 0; WASSER_SCHUSS = 1; SCHIFF = 2; SCHIFF_SCHUSS = 3;
	 */
	public int getStatus(Koordinaten koordinaten);

	// public Koordinaten werdeAngeschosen();

	/**
	 * 
	 * @return einen {@code int}, wie groß das Spielfeld ist (x*x)
	 */
	public int getGroesse();

	/**
	 * 
	 * @return {@code true}, wenn das Spielbrett keine Schiffe enthält,
	 *         {@code false} wenn min. 1 Zelle ein Schiff ist
	 */
	public boolean hatVerloren();

	/**
	 * füllt das Spielfeld zufällig mit einem Schiff der Länge 5, zwei Schiffen
	 * der Länge 4, drei Schiffen der Länge 3 und vier Schiffen der Länge 2
	 */
	public void fuelleSpielfeldRandom();

	public boolean schiffStartEndKoordinaten(Koordinaten startkoordinaten, Koordinaten endkoordinaten);

	public void verbinden(boolean spielfeldIstEigenes, String ip, int port) throws Exception;

	public void verbinden(boolean spielfeldIstEigenes, String ip, int port, boolean ki) throws Exception;

}
