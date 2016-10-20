package de.verbund.sv.tools;

import java.io.Serializable;

public class Koordinaten implements Serializable {

	/**
	 * 
	 */
	private int x;
	private int y;

	public Koordinaten(int x, int y) {
		setX(x);
		setY(y);
	}


    public void setX(int x) {
	this.x = x;
    }

    public void setY(int y) {
	this.y = y;
    }

    public int getX() {
	return x;
    }

    public int getY() {
	return y;
    }

	@Override
	public String toString() {
		String output = x + "|" + y;
		return output;
	}

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + x;
	result = prime * result + y;
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Koordinaten other = (Koordinaten) obj;
	if (x != other.x)
	    return false;
	if (y != other.y)
	    return false;
	return true;
    }
}
