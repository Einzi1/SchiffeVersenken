package de.verbund.sv.netz.test;

public class Tester {
    //
    // private SocketClientService client1 = new SocketClientServiceImpl();
    // private SocketClientService client2 = new SocketClientServiceImpl();
    //
    // public Tester() throws IOException {
    // starteErstenClient();
    // starteZweitenClient();
    // schickeNachrichtVonClientEins();
    // schickeNachrichtVonClientZwei();
    // // beendeBeideClients();
    //
    // }
    //
    // private boolean nachrichtFuer2Empfangen = false;
    // private boolean nachrichtFuer1Empfangen = false;
    //
    // private void starteErstenClient() {
    // try {
    // boolean erster = client1.starten("localhost", 4448);
    // if (!erster) {
    // System.out.println("Fehler: Sollte Erster sein!");
    // }
    // // Empfänger hinzufügen
    // client1.addEmpfeanger(new Empfaenger() {
    // @Override
    // public void empfange(Befehl befehl) {
    // empfangeNachrichtenFuerClient1(befehl);
    // }
    // });
    // } catch (Exception e) {
    // System.out.println("Fehler: Sollte keine Ex werfen!");
    // }
    // }
    //
    // private void starteZweitenClient() {
    // try {
    // boolean erster = client2.starten("localhost", 4448);
    // if (erster) {
    // System.out.println("Fehler: Sollte Zweiter sein!");
    // }
    // // Empfänger hinzufügen
    // client2.addEmpfeanger(new Empfaenger() {
    // @Override
    // public void empfange(Befehl befehl) {
    // empfangeNachrichtenFuerClient2(befehl);
    // }
    // });
    // } catch (Exception e) {
    // System.out.println("Fehler: Sollte keine Ex werfen!");
    // }
    // }
    //
    // private void schickeNachrichtVonClientEins() {
    // Befehl b = new Befehl();
    // b.setBefehl("Für Zwei");
    // b.getParameter().put("para1", 1253);
    // try {
    // client1.sende(b);
    // } catch (Exception e) {
    // System.out.println("Fehler: Sollte keine Ex werfen!");
    // }
    //
    // }
    //
    // protected void empfangeNachrichtenFuerClient2(Befehl befehl) {
    // if ("Für Zwei".equals(befehl.getBefehl()) &&
    // befehl.getParameter().get("para1") != null
    // && befehl.getParameter().get("para1") instanceof Integer) {
    // nachrichtFuer2Empfangen = true;
    // }
    // }
    //
    // private void schickeNachrichtVonClientZwei() {
    // Befehl b = new Befehl();
    // b.setBefehl("Für Eins");
    // b.getParameter().put("para1", new Date());
    // try {
    // client2.sende(b);
    // } catch (Exception e) {
    // System.out.println("Fehler: Sollte keine Ex werfen!");
    // }
    // }
    //
    // protected void empfangeNachrichtenFuerClient1(Befehl befehl) {
    // if ("Für Eins".equals(befehl.getBefehl()) &&
    // befehl.getParameter().get("para1") != null
    // && befehl.getParameter().get("para1") instanceof Date) {
    // nachrichtFuer1Empfangen = true;
    // }
    //
    // }
    //
    // private void beendeBeideClients() {
    // if (!nachrichtFuer1Empfangen) {
    // System.out.println("Fehler: Nachricht für Client 1 nicht angekommen!");
    // } else {
    // System.out.println("Nachricht f. Client 1 angekommen!");
    // }
    // if (!nachrichtFuer2Empfangen) {
    // System.out.println("Fehler: Nachricht für Client 2 nicht angekommen!");
    // } else {
    // System.out.println("Nachricht f. Client 2 angekommen!");
    // }
    // client1.beenden();
    // client2.beenden();
    // }
    //
    // public static void main(String[] args) throws IOException {
    // new Tester();
    // }
}