/*
 * $Id$
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.lang;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 反射工具类。
 * 提供调用 getter/setter 方法，访问私有变量，调用私有方法，获取泛型类型 Class，被 AOP 过的真实类等工具函数。
 */
public final class Reflections {
	/**
	 * cgLib 库修饰对象名的标识。
	 */
	public static final String CGLIB_CLASS_SEPARATOR = "$$";

	/**
	 * 日志记录器。
	 */
	private static final Logger LOG = LoggerFactory.getLogger(Reflections.class);

	/**
	 * 阻止实例化。
	 */
	private Reflections() {
	}

	/**
	 * 调用 Getter 方法。
	 * 
	 * @param target
	 *            目标对象
	 * @param name
	 *            属性名
	 * @return 属性值
	 */
	public static Object invokeGetter(final Object target, final String name) {
		String getterMethodName = "get" + StringUtils.capitalize(name);
		return invokeMethod(target, getterMethodName, new Class[] {}, new Object[] {});
	}

	/**
	 * 调用 Setter 方法。使用 value 的 Class 来查找 Setter 方法。
	 * 
	 * @param target
	 *            目标对象
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 */
	public static void invokeSetter(final Object target, final String name, final Object value) {
		invokeSetter(target, name, value, null);
	}

	/**
	 * 调用 Setter 方法。
	 * 
	 * @param target
	 *            目标对象
	 * @param name
	 *            属性名
	 * @param value
	 *            属性值
	 * @param type
	 *            用于查找 Setter 方法，为空时使用 value 的 Class 替代。
	 */
	public static void invokeSetter(final Object target, final String name, final Object value, final Class<?> type) {
		Class<?> valueType = type != null ? type : value.getClass();
		String setterMethodName = "set" + StringUtils.capitalize(name);
		invokeMethod(target, setterMethodName, new Class[] {
			valueType
		}, new Object[] {
			value
		});
	}

	/**
	 * 直接读取对象属性值，无视 private/protected 修饰符，不经过 getter 函数。
	 * 
	 * @param target
	 *            目标对象
	 * @param name
	 *            成员名
	 * @return 成员值
	 */
	public static Object getFieldValue(final Object target, final String name) {
		Field field = FieldUtils.getDeclaredField(target.getClass(), name, true);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + name + "] on target [" + target + "]");
		}

		Object result = null;
		try {
			result = field.get(target);
		} catch (IllegalAccessException e) {
			LOG.error("不可能抛出的异常{}", e.getMessage());
		}
		return result;
	}

	/**
	 * 直接设置对象属性值，无视 private/protected 修饰符，不经过 setter 函数。
	 * 
	 * @param target
	 *            目标对象
	 * @param name
	 *            成员名
	 * @param value
	 *            成员值
	 */
	public static void setFieldValue(final Object target, final String name, final Object value) {
		Field field = FieldUtils.getDeclaredField(target.getClass(), name, true);

		if (field == null) {
			throw new IllegalArgumentException("Could not find field [" + name + "] on target [" + target + "]");
		}

		try {
			field.set(target, value);
		} catch (IllegalAccessException e) {
			LOG.error("不可能抛出的异常:{}", e.getMessage());
		}
	}

	/**
	 * 直接调用对象方法，无视 private/protected 修饰符。
	 * 用于一次性调用的情况。
	 * 
	 * @param target
	 *            目标对象
	 * @param name
	 *            方法名
	 * @param parameterTypes
	 *            参数类型
	 * @param parameterValues
	 *            参数值
	 * @return 返回值
	 */
	public static Object invokeMethod(final Object target, final String name, final Class<?>[] parameterTypes, final Object[] parameterValues) {
		Method method = null;
		try {
			method = target.getClass().getDeclaredMethod(name, parameterTypes);
		} catch (NoSuchMethodException e) {
			throw new IllegalArgumentException("Could not find method [" + name + "] on target [" + target + "]");
		}
		if (method == null) {
			throw new IllegalArgumentException("Could not find method [" + name + "] on target [" + target + "]");
		}

		try {
			method.setAccessible(true);
			return method.invoke(target, parameterValues);
		} catch (IllegalAccessException e) {
			throw new ReflectionRuntimeException(e);
		} catch (InvocationTargetException e) {
			throw new ReflectionRuntimeException(e);
		}
	}

	/**
	 * 对于被 cglib AOP 过的对象，取得真实的 Class 类型。
	 * 
	 * @param clazz
	 *            cglib 类
	 * @return 原始类
	 */
	public static Class<?> getUserClass(final Class<?> clazz) {
		if (clazz != null && clazz.getName().contains(CGLIB_CLASS_SEPARATOR)) {
			Class<?> superClass = clazz.getSuperclass();
			if (superClass != null && !Object.class.equals(superClass)) {
				return superClass;
			}
		}
		return clazz;
	}

	/**
	 * 通过反射获得 Class 定义中声明的父类的泛型参数的类型。
	 * 如无法找到，返回 Object.class。
	 * eg.
	 * public UserDao extends HibernateDao<User>
	 * 
	 * @param clazz
	 *            The class to introspect
	 * @return the first generic declaration, or Object.class if cannot be determined
	 */
	public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
		return getSuperClassGenricType(clazz, 0);
	}

	/**
	 * 通过反射，获得 Class 定义中声明的父类的泛型参数的类型。
	 * 如无法找到，返回 Object.class。
	 * 如：
	 * public UserDao extends HibernateDao<User, Long>
	 * 
	 * @param clazz
	 *            clazz The class to introspect
	 * @param index
	 *            the Index of the generic ddeclaration,start from 0.
	 * @return the index generic declaration, or Object.class if cannot be determined
	 */
	public static Class getSuperClassGenricType(final Class clazz, final int index) {
		Type genType = clazz.getGenericSuperclass();

		if (!(genType instanceof ParameterizedType)) {
			LOG.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
			return Object.class;
		}

		Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

		if (index >= params.length || index < 0) {
			LOG.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
			return Object.class;
		}
		if (!(params[index] instanceof Class)) {
			LOG.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
			return Object.class;
		}

		return (Class) params[index];
	}
}
