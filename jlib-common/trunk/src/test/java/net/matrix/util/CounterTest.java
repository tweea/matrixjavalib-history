/*
 * $Id$
 * Copyright(C) 2011 北航冠新
 * All right reserved.
 */
package net.matrix.util;

import org.junit.Assert;
import org.junit.Test;

public class CounterTest
{
	@Test
	public void testCounter()
	{
		Counter counter = new Counter();
		Assert.assertEquals(0, counter.getCount());
	}

	@Test
	public void testIncrement()
	{
		Counter counter = new Counter();
		Assert.assertEquals(0, counter.getCount());
		Assert.assertEquals(1, counter.increment());
		Assert.assertEquals(1, counter.getCount());
	}

	@Test
	public void testDecrement()
	{
		Counter counter = new Counter();
		Assert.assertEquals(0, counter.getCount());
		counter.increment();
		Assert.assertEquals(0, counter.decrement());
		Assert.assertEquals(0, counter.getCount());
	}

	@Test
	public void testClean()
	{
		Counter counter = new Counter();
		Assert.assertEquals(0, counter.getCount());
		counter.increment();
		counter.increment();
		counter.increment();
		counter.clean();
		Assert.assertEquals(0, counter.getCount());
	}

	@Test
	public void testIsZero()
	{
		Counter counter = new Counter();
		Assert.assertTrue(counter.isZero());
		counter.increment();
		Assert.assertFalse(counter.isZero());
		counter.clean();
		Assert.assertTrue(counter.isZero());
	}

	@Test
	public void testGetCount()
	{
		Counter counter = new Counter();
		Assert.assertEquals(0, counter.getCount());
		counter.increment();
		counter.increment();
		counter.increment();
		Assert.assertEquals(3, counter.getCount());
	}
}
