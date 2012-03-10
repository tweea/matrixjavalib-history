/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.concurrent.atomic.AtomicInteger;

public class Counter
{
	private AtomicInteger count;

	public Counter()
	{
		this.count = new AtomicInteger();
	}

	public int increment()
	{
		return count.incrementAndGet();
	}

	public int decrement()
	{
		return count.decrementAndGet();
	}

	public void clean()
	{
		count.set(0);
	}

	public boolean isZero()
	{
		return count.intValue() == 0;
	}

	public int getCount()
	{
		return count.intValue();
	}
}
