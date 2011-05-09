package net.matrix.data;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

import org.junit.Assert;
import org.junit.Test;

public class BusinessDateTest
{
	@Test
	public void testBusinessDateGregorianCalendar()
	{
		GregorianCalendar gc = new GregorianCalendar(2011, 11, 1, 13, 15, 35);
		BusinessDate bd = new BusinessDate(gc);
		Assert.assertEquals(gc, bd);
	}

	@Test
	public void testBusinessDateDate()
	{
		Date date = new Date();
		BusinessDate bd = new BusinessDate(date);
		Assert.assertEquals(date, bd.getTime());
	}

	@Test
	public void testParseString()
		throws ParseException
	{
		BusinessDate bd1 = BusinessDate.parse("2011-12-01T13:15:35");
		BusinessDate bd2 = new BusinessDate(2011, 11, 1, 13, 15, 35);
		Assert.assertEquals(bd2, bd1);
	}

	@Test
	public void testParseStringString()
		throws ParseException
	{
		BusinessDate bd1 = BusinessDate.parse("2011-12-01", "yyyy-MM-dd");
		BusinessDate bd2 = new BusinessDate(2011, 11, 1);
		Assert.assertEquals(bd2, bd1);
	}

	@Test
	public void testToString()
	{
		BusinessDate bd = new BusinessDate(2011, 11, 1, 13, 15, 35);
		Assert.assertEquals("2011-12-01T13:15:35", bd.toString());
	}

	@Test
	public void testToStringString()
	{
		BusinessDate bd = new BusinessDate(2011, 11, 1, 13, 15, 35);
		Assert.assertEquals("13:15:35", bd.toString("HH:mm:ss"));
	}

	@Test
	public void testToDisplayStringStringStringString()
	{
		BusinessDate bd = new BusinessDate(2011, 11, 1, 13, 15, 35);
		Assert.assertEquals("2011a12b01c", bd.toDisplayString("a", "b", "c"));
	}

	@Test
	public void testToDisplayString()
	{
		BusinessDate bd = new BusinessDate(2011, 11, 1, 13, 15, 35);
		Assert.assertEquals("2011-12-01", bd.toDisplayString());
	}

	@Test
	public void testToChineseString()
	{
		BusinessDate bd = new BusinessDate(2011, 11, 1, 13, 15, 35);
		Assert.assertEquals("2011年12月01日", bd.toChineseString());
	}
}
