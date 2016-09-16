package de.verbund.sv.test;

import de.verbund.sv.SpielbrettManager;
import de.verbund.sv.SpielfeldManagerImplementation;
import de.verbund.sv.tools.Koordinaten;

/**
 * 
 * @author Adam
 *
 */
public class NetzUserTester {

	// TODO es muss das anfangsspielfeld kommuniziert werden
	// (1. eigenes kommunizieren, 2. gegnerisches empfangen)

	// instanz1 erzeugen, .verbinde aufrufen
	// spielfeldSchicken
	// instanz2 erzeugen,
	// spielfeld empfangen (setSpielfeld?)

	SpielfeldManagerImplementation eigenesSpielfeld;
	SpielfeldManagerImplementation gegnerischesSpielfeld;

	NetzUserTester() {
		// Initialisation
		System.out.println("Starte NetzTester mit zufällig befülltem Spielfeld");
		System.out.println();
		eigenesSpielfeld = new SpielfeldManagerImplementation(true);
		System.out.println("eigenesSpielfeld: ");
		System.out.println();
		for (int x = 0; x < eigenesSpielfeld.getGroesse(); x++) {
			for (int y = 0; y < eigenesSpielfeld.getGroesse(); y++) {
				Koordinaten koords = new Koordinaten(x, y);
				int status = eigenesSpielfeld.getStatus(koords);
				if (status == SpielbrettManager.SCHIFF) {
					System.out.print("X ");
				} else {
					System.out.print("O ");
				}
			}
			System.out.println();
		}
		System.out.println();

		// Gegnerfeld erzeugen
		gegnerischesSpielfeld = new SpielfeldManagerImplementation();
		System.out.println("Gegner erzeugt");

		// Verbindung
		System.out.println("eigenesSpielfeld verbindet...");
		try {
			eigenesSpielfeld.verbinden(true, "192.168.36.74", 4448);
			eigenesSpielfeld.schussAuf(new Koordinaten(1, 2));
			System.out.println("eigenesSpielfeld verbunden...");
		} catch (Exception e) {
			System.out.println("ERROR: Eigenes Spielfeld konnte Verbindung nicht aufbauen!");
		}

	}

	public static void main(String[] args) {
		new NetzUserTester();
	}
}
