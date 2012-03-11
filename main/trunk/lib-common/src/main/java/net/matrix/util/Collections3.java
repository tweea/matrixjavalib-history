/*
 * $Id$
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.util;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;

import net.matrix.lang.ReflectionRuntimeException;

/**
 * Collections 工具集。
 * 在 JDK 的 Colllections 和 Guava 的 Collections2 后，命名为 Collections3。
 */
public final class Collections3 {
	/**
	 * 阻止实例化。
	 */
	private Collections3() {
	}

	public static <T> List<T> subtract(final Collection<T> a, final Collection<T> b) {
		List<T> list = new ArrayList<T>(a);
		for (T item : b) {
			list.remove(item);
		}
		return list;
	}

	/**
	 * 提取集合中的对象的属性(通过 Getter 函数)，组合成 Map。
	 * 
	 * @param collection
	 *            来源集合.
	 * @param keyPropertyName
	 *            要提取为Map中的Key值的属性名.
	 * @param valuePropertyName
	 *            要提取为Map中的Value值的属性名.
	 */
	public static Map extractToMap(final Collection collection, final String keyPropertyName, final String valuePropertyName) {
		Map map = new HashMap();

		try {
			for (Object obj : collection) {
				map.put(PropertyUtils.getProperty(obj, keyPropertyName), PropertyUtils.getProperty(obj, valuePropertyName));
			}
		} catch (InvocationTargetException e) {
			throw new ReflectionRuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new ReflectionRuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new ReflectionRuntimeException(e);
		}

		return map;
	}

	/**
	 * 提取集合中的对象的属性(通过 Getter 函数)，组合成 List。
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertyName
	 *            要提取的属性名.
	 */
	public static List extractToList(final Collection collection, final String propertyName) {
		List list = new ArrayList();

		try {
			for (Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (InvocationTargetException e) {
			throw new ReflectionRuntimeException(e);
		} catch (IllegalAccessException e) {
			throw new ReflectionRuntimeException(e);
		} catch (NoSuchMethodException e) {
			throw new ReflectionRuntimeException(e);
		}

		return list;
	}

	/**
	 * 提取集合中的对象的属性(通过 Getter 函数)，组合成由分割符分隔的字符串。
	 * 
	 * @param collection
	 *            来源集合.
	 * @param propertyName
	 *            要提取的属性名.
	 * @param separator
	 *            分隔符.
	 */
	public static String extractToString(final Collection collection, final String propertyName, final String separator) {
		List list = extractToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}
}
