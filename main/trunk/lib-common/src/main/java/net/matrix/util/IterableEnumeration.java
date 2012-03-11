package net.matrix.util;

import java.util.Enumeration;
import java.util.Iterator;

import org.apache.commons.collections.iterators.EnumerationIterator;

/**
 * 把 Enumeration 接口转化为一个 Iterable 接口
 */
public class IterableEnumeration<E>
	implements Iterable<E> {
	private Iterator<E> it;

	public IterableEnumeration(Enumeration<E> enumeration) {
		this.it = new EnumerationIterator(enumeration);
	}

	@Override
	public Iterator<E> iterator() {
		return it;
	}
}
