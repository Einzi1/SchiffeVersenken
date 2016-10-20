/*																												*/
/*										       Leon Braungardt													*/
/*							Multiplayer Client für das Spiel Battleship Destroyer								*/
/*													2016														*/
/*______________________________________________________________________________________________________________*/

package de.verbund.sv.netz;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class SocketClientServiceImpl implements SocketClientService, Runnable {

	private static SocketClientServiceImpl instance;

	private List<Empfaenger> empfaenger = new ArrayList<>();
	private Thread t;
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	boolean spielgestartet = false;

	private boolean spielerzahl = false;

	private SocketClientServiceImpl() {
	}

	@Override
	public boolean starten(String ip, int port) throws Exception {
		spielgestartet = true;
		socket = new Socket(ip, port);
		in = new ObjectInputStream(socket.getInputStream());
		out = new ObjectOutputStream(socket.getOutputStream());
		t = new Thread(this);
		t.start();
		// TODO Spielstart, wenn beide Spieler ihre Boote gesetzt haben
		/*
		 * while(!bootegesetzt){ in. } if(bootegesetzt == true){ //bootegesetzt
		 * muss durch gui irgendwie gemacht werden spielgestartet = true; Befehl
		 * start = new Befehl(); start.setBefehl("bootegesetzt");
		 * out.writeObject(start); }
		 */

		return spielerzahl;
	}

	@Override
	public void beenden() {
		spielgestartet = false;
		Befehl b = new Befehl();
		b.setBefehl("abmelden");
		try {
			out.writeObject(b);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		try {
			in.close();
			out.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void sende(Befehl befehl) throws Exception {
		out.writeObject(befehl);
	}

	@Override
	public void addEmpfeanger(Empfaenger e) {
		empfaenger.add(e);

	}

	@Override
	public void run() {

		Befehl b = null;

		while (spielgestartet) {
			try {
				Object o = in.readObject();
				if (o instanceof Befehl) {
					b = (Befehl) o;
					System.out.println("Befehl angekommen " + b.getBefehl());
					if ("Spieler1".equals(b.getBefehl())) {
						spielerzahl = true;
					} 
					for (Empfaenger e : empfaenger) {
						e.empfange(b);
					}
				} else {
					System.out.println("Müll angekommen: " + o);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static SocketClientServiceImpl getInstance() {
		if (instance == null) {
			instance = new SocketClientServiceImpl();
		}
		return instance;
	}
}
