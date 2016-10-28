package de.verbund.sv;

import java.io.Serializable;
import java.util.Random;

import de.verbund.sv.netz.Befehl;
import de.verbund.sv.netz.Empfaenger;
import de.verbund.sv.netz.SocketClientService;
import de.verbund.sv.netz.SocketClientServiceImpl;
import de.verbund.sv.netz.SocketClientServiceKIImpl;
import de.verbund.sv.tools.Koordinaten;
import de.verbund.sv.tools.Schiff;

/**
 * 
 * @author Adam & Caro
 *
 */
public class SpielfeldManagerImplementation implements SpielbrettManager, Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int[][] spielfeld;

    private int groesse;

    private SocketClientService socketClient;
    /**
     * bestimmt, ob dieses Spielfeld ein eigenes ist. Wichtig für die
     * Kommunikation mit dem Client
     */
    private boolean eigenesSpielfeld;

    /**
     * erzeugt leeres Spielfeld mit {@code groesse} = 10
     */
    public SpielfeldManagerImplementation() {
	this(10, false);
    }

    /**
     * erzeugt leeres Spielfeld mit der angegebenen Größe
     * 
     * @param groesse
     *            des Spielfeldes (groesse * groesse)
     */
    public SpielfeldManagerImplementation(int groesse) {
	this(groesse, false);
    }

    /**
     * erzeugt ein zufällig befülltes oder leeres Spielfeld mit Größe = 10
     * 
     * @param spielfeldRandomBefuellen
     *            wenn {@code true}, wird Spielfeld zufällig befüllt
     */
    public SpielfeldManagerImplementation(boolean spielfeldRandomBefuellen) {
	this(10, spielfeldRandomBefuellen);
    }

    /**
     * erzeugt ein zufällig befülltes oder leeres Spielfeld mit der angegebenen
     * Größe
     * 
     * @param groesse
     *            des Spielfeldes (groesse * groesse)
     * @param spielfeldRandomBefuellen
     *            wenn {@code true}, wird Spielfeld zufällig befüllt
     */
    public SpielfeldManagerImplementation(int groesse, boolean spielfeldRandomBefuellen) {
	// DUMMY
	if (groesse == 0) {
	    setGroesse(10);
	    this.spielfeld = new int[getGroesse()][getGroesse()];
	    spielfeldFuellenDUMMY(2);

	} else if (groesse == -1) {
	    setGroesse(10);
	    this.spielfeld = new int[getGroesse()][getGroesse()];
	    spielfeldFuellenDUMMY(1);
	} else {
	    setGroesse(groesse);
	    this.spielfeld = new int[getGroesse()][getGroesse()];
	}

	if (spielfeldRandomBefuellen) {
	    fuelleSpielfeldRandom();
	    // kiInitialisieren();
	    // } else {
	    // gegnerInitialisieren();
	    // }
	}
    }

    private void kiInitialisieren() {
	socketClient = new SocketClientServiceKIImpl();
    }

    private void gegnerInitialisieren(String ip, int port) {
	socketClient = SocketClientServiceImpl.getInstance();

	try {
	    socketClient.starten(ip, port);
	    // erster = socketClient.starten("localhost", 4448);
	    // erster = socketClient.starten("192.168.36.73", 4448);
	    // TODO tz: Empfänger nur hinzufügen, wenn Starten geklappt hat!
	    // Ansosnten: ordentlicher Exit!
	    socketClient.addEmpfeanger(new Empfaenger() {
		@Override
		public void empfange(Befehl befehl) {
		    befehlEmpfangen(befehl);
		}
	    });
	    Befehl befehl = new Befehl();
	    befehl.setBefehl("initSpielfeld");
	    befehl.getParameter().put("adressat", "gegner");
	    befehl.getParameter().put("spielfeld", spielfeld);
	    socketClient.sende(befehl);
	} catch (Exception e) {
	    System.out.println("IT  FOCKIN FAILED! Server nicht gefunden!");
	    e.printStackTrace();
	}
    }

    public void verbinden(boolean spielfeldIstEigenes, String ip, int port) throws Exception {
	verbinden(spielfeldIstEigenes, ip, port, false);
    }

    public void verbinden(boolean spielfeldIstEigenes, String ip, int port, boolean ki) throws Exception {
	this.eigenesSpielfeld = spielfeldIstEigenes;
	if (ki) {
	    kiInitialisieren();
	} else {
	    gegnerInitialisieren(ip, port);
	}
    }

    @Override
    public int schussAuf(Koordinaten koordinaten) throws SchussSchonAbgegebenException {
	// Schuss auswerten
	if (getStatus(koordinaten) == SCHIFF_SCHUSS || getStatus(koordinaten) == WASSER_SCHUSS) {
	    throw new SchussSchonAbgegebenException();
	}

	spielfeld[koordinaten.getX()][koordinaten.getY()]++;

	// Schuss kommunizieren
	Befehl befehl = new Befehl();
	befehl.setBefehl("schuss");
	befehl.getParameter().put("Koordinaten", koordinaten);
	befehl.getParameter().put("adressat", "eigenes");
	try {
	    socketClient.sende(befehl);
	} catch (Exception e) {
	    System.out.println("[KOMMUNIKATIONSFEHLER]: Konnte Befehl " + befehl.getBefehl() + " auf " + koordinaten
		    + " nicht senden! ");
	    e.printStackTrace();
	}

	return (getStatus(koordinaten));
    }

    @Override
    public boolean setzeSchiff(Koordinaten startkoordinaten, Schiff schiff) {

	if (schiffZuGrossFuerSpielfeld(startkoordinaten, schiff)) {
	    return false;
	}

	if (liegtSchiffAufWegVon(startkoordinaten, schiff)) {
	    return false;
	}

	if (beruehrung(startkoordinaten, schiff)) {
	    return false;
	}

	if (schiff.isWaagrecht()) {
	    for (int x = startkoordinaten.getX(); x < (startkoordinaten.getX() + schiff.getLaenge()); x++) {
		spielfeld[x][startkoordinaten.getY()] = SpielbrettManager.SCHIFF;
	    }
	} else {
	    for (int y = startkoordinaten.getY(); y < (startkoordinaten.getY() + schiff.getLaenge()); y++) {
		spielfeld[startkoordinaten.getX()][y] = SpielbrettManager.SCHIFF;
	    }
	}
	return true;
    }

    public boolean schiffStartEndKoordinaten(Koordinaten startkoordinaten, Koordinaten endkoordinaten) {
	// TODO Validierungen modularisieren (z.B. zuerst senkrecht/waagrecht,
	// danach ob Länge stimmt)
	if (startkoordinaten.getX() == endkoordinaten.getX()) {

	    if (endkoordinaten.getY() < startkoordinaten.getY()) {
		Koordinaten neueStartkoordinaten = new Koordinaten(endkoordinaten.getX(), endkoordinaten.getY());
		endkoordinaten = startkoordinaten;
		startkoordinaten = neueStartkoordinaten;
	    }

	    int laengeSchiff = (endkoordinaten.getY() - startkoordinaten.getY()) + 1;

	    if (laengeSchiff > 1 && laengeSchiff < 6) {

		return setzeSchiff(startkoordinaten, new Schiff(laengeSchiff, false));

	    } else {
		// Schiff zu klein oder zu groß
		return false;
	    }

	} else if (startkoordinaten.getY() == endkoordinaten.getY()) {

	    if (endkoordinaten.getX() < startkoordinaten.getX()) {
		Koordinaten neueStartkoordinaten = new Koordinaten(endkoordinaten.getX(), endkoordinaten.getY());
		endkoordinaten = startkoordinaten;
		startkoordinaten = neueStartkoordinaten;
	    }

	    int laengeSchiff = (endkoordinaten.getX() - startkoordinaten.getX()) + 1;

	    if (laengeSchiff > 1 && laengeSchiff < 6) {

		return setzeSchiff(startkoordinaten, new Schiff(laengeSchiff, true));

	    } else {
		// Schiff zu klein oder zu groß
		return false;
	    }

	} else {
	    // Schiff liegt nicht in einer Reihe
	    return false;
	}

    }

    private boolean beruehrung(Koordinaten startkoordinaten, Schiff schiff) {

	if (schiff.isWaagrecht()) {
	    for (int i = 0, x = startkoordinaten.getX(); i < schiff.getLaenge(); i++, x++) {
		if (pruefeUmgebung(x, startkoordinaten.getY())) {
		    return true;
		}
	    }
	} else {
	    for (int i = 0, y = startkoordinaten.getY(); i < schiff.getLaenge(); i++, y++) {
		if (pruefeUmgebung(startkoordinaten.getX(), y)) {
		    return true;
		}
	    }
	}
	return false;
    }

    /**
     * 
     * @param i
     * @return true wenn ein Schiff in der Umgebung (oben, unten, links, rechts
     *         von x|y) vorhanden
     */

    private boolean pruefeUmgebung(int x, int y) {

	// Prüfung oben
	if (y != 0 && spielfeld[x][y - 1] > 1) {
	    return true;
	}
	// Prüfung unten
	if (y != (groesse - 1) && spielfeld[x][y + 1] > 1) {
	    return true;
	}
	// Prüfung links
	if (x != 0 && spielfeld[x - 1][y] > 1) {
	    return true;
	}
	// Prüfung rechts
	if (x != (groesse - 1) && spielfeld[x + 1][y] > 1) {
	    return true;
	}
	return false;
    }

    private boolean schiffZuGrossFuerSpielfeld(Koordinaten startkoordinaten, Schiff schiff) {

	if (schiff.isWaagrecht()) {
	    if (startkoordinaten.getX() + schiff.getLaenge() > groesse) {
		return true;
	    }
	} else if ((startkoordinaten.getY() + schiff.getLaenge()) > groesse) {
	    return true;
	}
	return false;
    }

    private boolean liegtSchiffAufWegVon(Koordinaten startkoordinaten, Schiff schiff) {

	if (schiff.isWaagrecht()) {
	    for (int x = startkoordinaten.getX(); x < (startkoordinaten.getX() + schiff.getLaenge()); x++) {
		if (spielfeld[x][startkoordinaten.getY()] == SpielbrettManager.SCHIFF) {
		    return true;
		}
	    }
	} else {
	    for (int y = startkoordinaten.getY(); y < (startkoordinaten.getY() + schiff.getLaenge()); y++) {
		if (spielfeld[startkoordinaten.getX()][y] == SpielbrettManager.SCHIFF) {
		    return true;
		}
	    }
	}
	return false;
    }

    private void spielfeldFuellenDUMMY(int version) {
	switch (version) {
	case 2:
	    setzeSchiff(new Koordinaten(0, 0), new Schiff(5, false));
	    setzeSchiff(new Koordinaten(7, 2), new Schiff(4, false));
	    setzeSchiff(new Koordinaten(2, 7), new Schiff(4, true));
	    setzeSchiff(new Koordinaten(2, 4), new Schiff(3, true));
	    setzeSchiff(new Koordinaten(4, 0), new Schiff(3, false));
	    setzeSchiff(new Koordinaten(7, 8), new Schiff(3, true));
	    setzeSchiff(new Koordinaten(2, 1), new Schiff(2, false));
	    setzeSchiff(new Koordinaten(1, 9), new Schiff(2, true));
	    setzeSchiff(new Koordinaten(7, 0), new Schiff(2, true));
	    setzeSchiff(new Koordinaten(9, 3), new Schiff(2, false));
	    break;
	default:
	case 1:
	    setzeSchiff(new Koordinaten(0, 0), new Schiff(5, true));
	    setzeSchiff(new Koordinaten(2, 7), new Schiff(4, true));
	    setzeSchiff(new Koordinaten(7, 2), new Schiff(4, false));
	    setzeSchiff(new Koordinaten(4, 2), new Schiff(3, false));
	    setzeSchiff(new Koordinaten(0, 4), new Schiff(3, true));
	    setzeSchiff(new Koordinaten(8, 7), new Schiff(3, false));
	    setzeSchiff(new Koordinaten(1, 2), new Schiff(2, true));
	    setzeSchiff(new Koordinaten(9, 1), new Schiff(2, false));
	    setzeSchiff(new Koordinaten(0, 7), new Schiff(2, false));
	    setzeSchiff(new Koordinaten(3, 9), new Schiff(2, true));
	    break;
	}

    }

    @Override
    public int getStatus(Koordinaten koordinaten) {
	return spielfeld[koordinaten.getX()][koordinaten.getY()];
    }

    @Override
    public int getGroesse() {
	return groesse;
    }

    // @Override
    // public Koordinaten werdeAngeschosen() {
    // return null;
    // }

    private void setGroesse(int groesse) {
	this.groesse = groesse;
    }

    @Override
    public boolean hatVerloren() {
	int anzahlSchiffe = 0;
	for (int x = 0; x < groesse; x++) {
	    for (int y = 0; y < groesse; y++) {
		if (spielfeld[x][y] == SCHIFF) {
		    anzahlSchiffe++;
		}
	    }
	}
	if (anzahlSchiffe == 0) {
	    return true;
	} else {
	    return false;
	}
    }

    @Override
    public void fuelleSpielfeldRandom() {
	setzeZufaelligeSchiffe(1, 5);
	setzeZufaelligeSchiffe(2, 4);
	setzeZufaelligeSchiffe(3, 3);
	setzeZufaelligeSchiffe(4, 2);
    }

    private void setzeZufaelligeSchiffe(int anzahl, int laenge) {
	Random random = new Random();
	int zufallX;
	int zufallY;
	boolean waagrecht;

	for (int schiffartAnz = 0; schiffartAnz < anzahl;) {
	    zufallX = (int) (Math.random() * 10);
	    zufallY = (int) (Math.random() * 10);
	    waagrecht = random.nextBoolean();

	    if (setzeSchiff(new Koordinaten(zufallX, zufallY), new Schiff(laenge, waagrecht))) {
		schiffartAnz++;
	    }
	}

    }

    private void befehlEmpfangen(Befehl befehl) {
	if ("gegner".equals(befehl.getParameter().get("adressat")) && !eigenesSpielfeld) {
	    if ("initSpielfeld".equals(befehl.getBefehl()))
		spielfeld = (int[][]) befehl.getParameter().get("spielfeld");
	} else if ("eigenes".equals(befehl.getParameter().get("adressat")) && eigenesSpielfeld) {
	    if ("schuss".equals(befehl.getBefehl())) {
		Koordinaten koords = (Koordinaten) befehl.getParameter().get("koordinaten");
		try {
		    schussAuf(koords);
		} catch (SchussSchonAbgegebenException e) {
		    e.printStackTrace();
		}
	    }
	}
    }

}