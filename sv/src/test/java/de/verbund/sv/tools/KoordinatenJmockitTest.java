package de.verbund.sv.tools;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import mockit.Deencapsulation;
import mockit.Tested;

public class KoordinatenJmockitTest {

	@Tested
	public Koordinaten tested;

	@Before
	public void beforeMethode() {
		tested = new Koordinaten();
	}

	@Test
	public void setX() {

		tested.setX(1);

		int result = Deencapsulation.getField(tested, "x");

		assertThat(result, is(1));
	}

	@Test
	public void setY() {

		tested.setY(1);

		int result = Deencapsulation.getField(tested, "y");

		assertThat(result, is(1));
	}

	@Test
	public void getX() {

		Deencapsulation.setField(tested, "x", 1);

		int result = tested.getX();

		assertThat(result, is(1));
	}

	@Test
	public void getY() {

		Deencapsulation.setField(tested, "y", 1);

		int result = tested.getY();

		assertThat(result, is(1));
	}

	@Test
	public void toStringXY() {

		tested = new Koordinaten(1, 2);

		String result = tested.toString();

		assertThat(result, is("1|2"));
	}

	@Test
	public void hashCodeKoordinaten() {

		tested = new Koordinaten(1, 2);

		int result = tested.hashCode();

		assertThat(result, is(994));
	}

	@Test
	public void equalsGleichesObjekt() {

		boolean result = tested.equals(tested);

		assertThat(result, is(true));
	}

	@Test
	public void equalsUebergabeNull() {

		boolean result = tested.equals(null);

		assertThat(result, is(false));
	}

	@Test
	public void equalsUnterschiedlicheKlasse() {

		boolean result = tested.equals(this);

		assertThat(result, is(false));
	}

	@Test
	public void equalsXUnterschiedlich() {

		tested = new Koordinaten(2, 1);

		boolean result = tested.equals(new Koordinaten(1, 1));

		assertThat(result, is(false));
	}

	@Test
	public void equalsYUnterschiedlich() {

		tested = new Koordinaten(2, 1);

		boolean result = tested.equals(new Koordinaten(2, 2));

		assertThat(result, is(false));
	}

	@Test
	public void equalsTrue() {

		tested = new Koordinaten(1, 2);

		boolean result = tested.equals(new Koordinaten(1, 2));

		assertThat(result, is(true));
	}
}