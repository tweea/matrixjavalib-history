/*
 * $Id$
 * Copyright(C) 2011 北航冠新
 * All right reserved.
 */
package net.matrix.text;

import java.text.DateFormat;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

public class DateFormatHelperTest
{
	@Test
	public void testGetFormat()
	{
		DateFormat format = DateFormatHelper.getFormat(DateFormatHelper.ISO_DATE_FORMAT);
		Assert.assertEquals("2011-01-01", format.format(new GregorianCalendar(2011, 0, 1, 1, 1, 1).getTime()));
	}

	@Test
	public void testFormatDateString()
	{
		Assert.assertEquals("2011-01-01", DateFormatHelper.format(new GregorianCalendar(2011, 0, 1, 1, 1, 1).getTime(), DateFormatHelper.ISO_DATE_FORMAT));
	}

	@Test
	public void testFormatCalendarString()
	{
		Assert.assertEquals("2011-01-01", DateFormatHelper.format(new GregorianCalendar(2011, 0, 1, 1, 1, 1), DateFormatHelper.ISO_DATE_FORMAT));
	}

	@Test
	public void testFormatTime()
	{
		Assert.assertEquals("2011-01-01",
			DateFormatHelper.formatTime(new GregorianCalendar(2011, 0, 1, 1, 1, 1).getTime().getTime(), DateFormatHelper.ISO_DATE_FORMAT));
	}
}
