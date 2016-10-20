package de.verbund.sv;

import java.util.HashMap;
import java.util.Map;

public class SchiffUeberpruefung {

	public static void main(String[] args) {
		// Initialisierung
		Map<Integer, Integer> schiffe = new HashMap<Integer, Integer>();
		schiffe.put(5, 1);
		schiffe.put(4, 2);
		// ..
		
		// wenn einer einen 4er setzt
		int anzahl = schiffe.get(4);
		if ( anzahl > 0) {
			anzahl --;
			schiffe.put(4, anzahl);
		} else {
			// mecker! Kein Schiff mehr
		}
		
		
	}

}
