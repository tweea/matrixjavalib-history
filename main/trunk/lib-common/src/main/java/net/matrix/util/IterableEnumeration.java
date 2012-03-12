/*
 * $Id$
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.util.Enumeration;
import java.util.Iterator;

import org.apache.commons.collections.iterators.EnumerationIterator;

/**
 * 把 Enumeration 接口转化为一个 Iterable 接口。
 * 
 * @param <E>
 *            内容类型
 */
public class IterableEnumeration<E>
	implements Iterable<E> {
	/**
	 * 目标 Iterator。
	 */
	private Iterator<E> it;

	public IterableEnumeration(final Enumeration<E> enumeration) {
		this.it = new EnumerationIterator(enumeration);
	}

	@Override
	public Iterator<E> iterator() {
		return it;
	}
}
