/*																												*/
/*										       Leon Braungardt													*/
/*									Server für das Spiel Battleship Destroyer									*/
/*													2016														*/
/*______________________________________________________________________________________________________________*/

package de.verbund.sv.netz;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    public Server() {

	Thread t = new Thread(this);
	t.start();

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	try {
	    String konsole = br.readLine();
	} catch (IOException e) { // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public static void main(String[] args) {
	new Server();

    }

    public void run() {

	Befehl gesendet1 = new Befehl();
	Befehl gesendet2 = new Befehl();
	ObjectInputStream in1 = null;
	ObjectOutputStream out1 = null;
	ObjectInputStream in2 = null;
	ObjectOutputStream out2 = null;
	ServerSocket server = null;
	Socket spieler1 = new Socket();
	Socket spieler2 = new Socket();
	boolean sp1 = false;
	boolean sp2 = false;

	try {
	    server = new ServerSocket(4448);
	    System.out.println("Warte auf Client 1 ...");
	    spieler1 = server.accept();
	    System.out.println("Spieler1 ist beigetreten... " + spieler1.getInetAddress().getHostAddress());
	    out1 = new ObjectOutputStream(spieler1.getOutputStream());
	    //Befehl b = new Befehl();
	    // b.setBefehl("Spieler1");
	    //out1.writeObject(b);
	    in1 = new ObjectInputStream(spieler1.getInputStream());

	} catch (IOException e) {
	    e.printStackTrace();
	}

	try {
	    System.out.println("Warte auf Client 2 ...");
	    spieler2 = server.accept();
	    System.out.println("Spieler2 ist beigetreten... " + spieler2.getInetAddress().getHostAddress());
	    out2 = new ObjectOutputStream(spieler2.getOutputStream());
	    in2 = new ObjectInputStream(spieler2.getInputStream());
	    //Befehl b = new Befehl();
	    //b.setBefehl("Spieler2");
	    //out2.writeObject(b);

	} catch (IOException e) {
	    e.printStackTrace();
	}

	do {
	    try {

		// warte auf Input, leite gesendete Informationen an jeweiligen
		// Client weiter,
		// schaue, ob beide Spieler ihre Boote gesetzt haben
		// gesendet1 = (Befehl) in1.readObject();
		// if (gesendet1.getBefehl().equals("bootegesetzt")) {
		// sp1 = true;
		// } else {
		// out2.writeObject(gesendet1);
		// System.out.println("1 an 2");
		// }
		// gesendet2 = (Befehl) in2.readObject();
		// if (gesendet2.getBefehl().equals("bootegesetzt")) {
		// sp2 = true;
		// } else {
		// out1.writeObject(gesendet2);
		// System.out.println("2 an 1");
		// }
		// if (sp1 && sp2) {
		//
		// // TODO: Beide Clients benachrichtigen, dass Spiel jetzt
		// // startet und Spieler1 anfängt
		//
		// }
		gesendet1 = (Befehl) in1.readObject();
		System.out.println("in1 empfang: " + gesendet1.getBefehl());
		out2.writeObject(gesendet1);
		gesendet2 = (Befehl) in2.readObject();
		System.out.println("in2 empfang: " + gesendet2.getBefehl());
		out1.writeObject(gesendet2);

	    } catch (IOException e) {
		System.out.println("Spieler 1 connected: " + spieler1.isConnected());
		System.out.println("Spieler 2 connected: " + spieler2.isConnected());
		e.printStackTrace();
		gesendet1.setBefehl("abmelden");
		gesendet2.setBefehl("abmelden");
	    } catch (ClassNotFoundException e) {
		//
		e.printStackTrace();
	    }
	} while ((!gesendet1.getBefehl().equals("abmelden") && !gesendet2.getBefehl().equals("abmelden")));

	try {
	    in1.close();
	    out1.close();
	    in2.close();
	    out2.close();
	    spieler1.close();
	    spieler2.close();
	} catch (IOException e) {
	    System.out.println("Fehler beim beenden");
	    e.printStackTrace();
	}

    }

}
