/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.text;

import java.text.NumberFormat;

/**
 * 数字格式化工具方法。
 */
public final class NumberFormatHelper {
	/**
	 * 缓存。
	 */
	private static final ThreadLocal<NumberFormat> LOCAL_FORMAT = new ThreadLocal<NumberFormat>();

	/**
	 * 阻止实例化。
	 */
	private NumberFormatHelper() {
	}

	/**
	 * 获取格式化工具实例。
	 * 
	 * @return 格式化工具实例
	 */
	public static NumberFormat getDateFormat() {
		NumberFormat df = LOCAL_FORMAT.get();
		if (df == null) {
			df = NumberFormat.getInstance();
			LOCAL_FORMAT.set(df);
		}
		return df;
	}
}
