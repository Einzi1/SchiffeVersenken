package de.verbund.sv.netz;

public interface SocketClientService {

    /**
     * Startet eine Netzwerkverbindung
     * 
     * @param ip
     * @param port
     * @return true, wenn erster, false wenn zweiter Spieler
     * @throws Exception
     */
    public boolean starten(String ip, int port) throws Exception;

    public void beenden();

    public void sende(Befehl befehl) throws Exception;

    public void addEmpfeanger(Empfaenger e);
}
