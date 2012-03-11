package net.matrix.util;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

public class CalendarsTest {
	@Test
	public void create() {
		GregorianCalendar gc = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
		GregorianCalendar bd = Calendars.create(gc);
		Assert.assertEquals(gc, bd);
	}

	@Test
	public void create2() {
		Date date = new Date();
		GregorianCalendar bd = Calendars.create(date);
		Assert.assertEquals(date, bd.getTime());
	}

	@Test
	public void isValidDate() {
		Assert.assertTrue(Calendars.isValidDate(2011, 10, 1));
		Assert.assertFalse(Calendars.isValidDate(2011, 13, 1));
	}
}
