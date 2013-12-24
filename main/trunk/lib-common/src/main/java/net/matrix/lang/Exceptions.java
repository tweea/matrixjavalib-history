/*
 * $Id$
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

// TODO 整理并应用
/**
 * 关于异常的工具类.
 */
public class Exceptions {
	/**
	 * 将CheckedException转换为UncheckedException.
	 */
	public static RuntimeException unchecked(Exception e) {
		if (e instanceof RuntimeException) {
			return (RuntimeException) e;
		} else {
			return new RuntimeException(e);
		}
	}

	/**
	 * 获取组合本异常信息与底层异常信息的异常描述, 适用于本异常为统一包装异常类，底层异常才是根本原因的情况。
	 */
	public static String getErrorMessageWithNestedException(Exception e) {
		Throwable nestedException = e.getCause();
		return new StringBuilder().append(e.getMessage()).append(" nested exception is ").append(nestedException.getClass().getName()).append(":")
			.append(nestedException.getMessage()).toString();
	}

	/**
	 * 判断异常是否由某些底层的异常引起.
	 */
	public static boolean isCausedBy(Exception ex, Class<? extends Exception>... causeExceptionClasses) {
		Throwable cause = ex;
		while (cause != null) {
			for (Class<? extends Exception> causeClass : causeExceptionClasses) {
				if (causeClass.isInstance(cause)) {
					return true;
				}
			}
			cause = cause.getCause();
		}
		return false;
	}
}
