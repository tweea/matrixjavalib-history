package net.matrix.util;

import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

public class GregorianCalendarUtilTest
{
	@Test
	public void testBusinessDateGregorianCalendar()
	{
		GregorianCalendar gc = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
		GregorianCalendar bd = GregorianCalendarUtil.create(gc);
		Assert.assertEquals(gc, bd);
	}

	@Test
	public void testBusinessDateDate()
	{
		Date date = new Date();
		GregorianCalendar bd = GregorianCalendarUtil.create(date);
		Assert.assertEquals(date, bd.getTime());
	}
}
