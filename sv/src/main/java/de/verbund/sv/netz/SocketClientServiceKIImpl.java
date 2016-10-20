package de.verbund.sv.netz;

import java.util.ArrayList;
import java.util.List;

import de.verbund.sv.tools.KI;
import de.verbund.sv.tools.Koordinaten;

public class SocketClientServiceKIImpl implements SocketClientService, Runnable {

	KI ki;
	private List<Empfaenger> empfaenger = new ArrayList<>();

	@Override
	public boolean starten(String ip, int port) throws Exception {
		ki = new KI();
		return false;
	}

	@Override
	public void beenden() {
		// TODO nichts tun?
	}

	@Override
	public void sende(Befehl befehl) throws Exception {
		// TODO Auto-generated method stub
	}

	@Override
	public void addEmpfeanger(Empfaenger e) {
		empfaenger.add(e);
	}

	@Override
	public void run() {
		// TODO empfaenger durchlaufen und senden
		while (true) {
			Koordinaten koords = ki.getZufaelligerSchuss();
			Befehl b = new Befehl();
			// TODO Befehl anzupassen
			b.setBefehl("Schuss");
			b.getParameter().put("Koordinaten", koords);

			for (Empfaenger e : empfaenger) {
				e.empfange(b);
			}
		}

	}
}
