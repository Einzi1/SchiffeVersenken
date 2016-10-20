package de.verbund.sv.tools;

public class Schiff {

    private int laenge;
    private boolean waagrecht;

    public Schiff(int laenge, boolean waagrecht) {
	setLaenge(laenge);
	setWaagrecht(waagrecht);
    }

    public boolean isWaagrecht() {
	return waagrecht;
    }

    private void setWaagrecht(boolean waagrecht) {
	this.waagrecht = waagrecht;
    }

    public int getLaenge() {
	return laenge;
    }

    private void setLaenge(int laenge) {
	this.laenge = laenge;
    }

}
