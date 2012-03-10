/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 解析逗号分割的字符串
 */
public class CommaSeparatedStringList
	implements List<String>
{
	private List<String> internalList;

	/**
	 * 空字符串
	 */
	public CommaSeparatedStringList()
	{
		internalList = new ArrayList<String>();
	}

	/**
	 * 解析字符串中的项目
	 * @param value 逗号分割的字符串
	 */
	public CommaSeparatedStringList(String value)
	{
		String[] array = value.split(",", -1);
		internalList = Arrays.asList(array);
	}

	/**
	 * 使用多个项目形成字符串
	 * @param values 项目
	 */
	public CommaSeparatedStringList(String[] values)
	{
		internalList = Arrays.asList(values);
	}

	/**
	 * 使用多个项目形成字符串
	 * @param list 项目
	 */
	public CommaSeparatedStringList(List<String> list)
	{
		internalList = new ArrayList<String>(list);
	}

	@Override
	public void add(int index, String element)
	{
		internalList.add(index, element);
	}

	@Override
	public boolean add(String o)
	{
		return internalList.add(o);
	}

	@Override
	public boolean addAll(Collection<? extends String> c)
	{
		return internalList.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends String> c)
	{
		return internalList.addAll(index, c);
	}

	@Override
	public void clear()
	{
		internalList.clear();
	}

	@Override
	public boolean contains(Object obj)
	{
		return internalList.contains(obj);
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		return internalList.containsAll(c);
	}

	@Override
	public boolean equals(Object obj)
	{
		return internalList.equals(obj);
	}

	@Override
	public String get(int i)
	{
		return internalList.get(i);
	}

	@Override
	public int hashCode()
	{
		return internalList.hashCode();
	}

	@Override
	public int indexOf(Object obj)
	{
		return internalList.indexOf(obj);
	}

	@Override
	public boolean isEmpty()
	{
		return internalList.isEmpty();
	}

	@Override
	public Iterator<String> iterator()
	{
		return internalList.iterator();
	}

	@Override
	public int lastIndexOf(Object obj)
	{
		return internalList.lastIndexOf(obj);
	}

	@Override
	public ListIterator<String> listIterator()
	{
		return internalList.listIterator();
	}

	@Override
	public ListIterator<String> listIterator(int i)
	{
		return internalList.listIterator(i);
	}

	@Override
	public String remove(int i)
	{
		return internalList.remove(i);
	}

	@Override
	public boolean remove(Object obj)
	{
		return internalList.remove(obj);
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		return internalList.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		return internalList.retainAll(c);
	}

	@Override
	public String set(int index, String element)
	{
		return internalList.set(index, element);
	}

	@Override
	public int size()
	{
		return internalList.size();
	}

	@Override
	public List<String> subList(int i, int j)
	{
		return internalList.subList(i, j);
	}

	@Override
	public Object[] toArray()
	{
		return internalList.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		return internalList.toArray(a);
	}

	@Override
	public String toString()
	{
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i < internalList.size(); i++){
			if(i == internalList.size() - 1){
				buffer.append(internalList.get(i));
			}else{
				buffer.append(internalList.get(i)).append(",");
			}
		}
		return buffer.toString();
	}
}
