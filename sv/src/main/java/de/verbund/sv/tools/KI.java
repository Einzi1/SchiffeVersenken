package de.verbund.sv.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Adam & Caro
 *
 */
public class KI {

	List<Koordinaten> abgegebeneSchuesse = new ArrayList<>();

	public Koordinaten getZufaelligerSchuss() {

		Koordinaten kiKoordinaten;

		do {
			int SchussZufallX = (int) (Math.random() * 10);
			int SchussZufallY = (int) (Math.random() * 10);
			kiKoordinaten = new Koordinaten(SchussZufallX, SchussZufallY);

		} while (abgegebeneSchuesse.contains(kiKoordinaten));

		abgegebeneSchuesse.add(kiKoordinaten);

		return kiKoordinaten;
	}
}
