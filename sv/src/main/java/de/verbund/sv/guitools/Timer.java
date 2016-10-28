package de.verbund.sv.guitools;

public class Timer {

	private long start;
	private long ende;

	public void start() {
		start = System.currentTimeMillis();
	}

	public long stop() {
		ende = System.currentTimeMillis();
		long zeit = ende - start;
		return zeit;
	}
}