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
 * 在 JDK 的 Collections 和 Guava 的 Collections2 后，命名为 Collections3。
 */
public final class Collections3 {
	/**
	 * 阻止实例化。
	 */
	private Collections3() {
	}

	/**
	 * 返回 a - b 的集合。
	 * 
	 * @param <T>
	 *            集合元素类型
	 * @param a
	 *            总集合
	 * @param b
	 *            被减集合
	 * @return 差集合
	 */
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
	 * @return 属性集合
	 */
	public static Map extractToMap(final Collection collection, final String keyPropertyName, final String valuePropertyName) {
		Map map = new HashMap(collection.size());

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
	 * @return 属性列表
	 */
	public static List extractToList(final Collection collection, final String propertyName) {
		List list = new ArrayList(collection.size());

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
	 * @return 组合字符串
	 */
	public static String extractToString(final Collection collection, final String propertyName, final String separator) {
		List list = extractToList(collection, propertyName);
		return StringUtils.join(list, separator);
	}

	/**
	 * 转换 Collection 为 String，每个元素的前面加入 prefix，后面加入 postfix，如<div>mymessage</div>。
	 * 
	 * @param collection
	 *            来源集合.
	 * @param prefix
	 *            前缀
	 * @param postfix
	 *            后缀
	 * @return 组合字符串
	 */
	public static String convertToString(final Collection collection, final String prefix, final String postfix) {
		StringBuilder builder = new StringBuilder();
		for (Object o : collection) {
			builder.append(prefix).append(o).append(postfix);
		}
		return builder.toString();
	}
}
