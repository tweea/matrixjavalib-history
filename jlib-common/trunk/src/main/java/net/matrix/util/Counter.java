/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 简单的计数器
 */
public class Counter
{
	private AtomicInteger count;

	/**
	 * 初始化一个计数器，初始值为 0。
	 */
	public Counter()
	{
		this.count = new AtomicInteger();
	}

	/**
	 * 计数加一
	 * @return 计数后数值
	 */
	public int increment()
	{
		return count.incrementAndGet();
	}

	/**
	 * 计数减一
	 * @return 计数后数值
	 */
	public int decrement()
	{
		return count.decrementAndGet();
	}

	/**
	 * 计数重置为 0
	 */
	public void clean()
	{
		count.set(0);
	}

	/**
	 * 当前计数是否为 0
	 * @return 计数为 0 返回 true
	 */
	public boolean isZero()
	{
		return count.intValue() == 0;
	}

	/**
	 * 获取当前计数
	 * @return 当前计数
	 */
	public int getCount()
	{
		return count.intValue();
	}
}
