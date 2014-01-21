/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.web.html;

import org.apache.commons.lang3.StringUtils;

/**
 * HTML 相关工具。
 */
public final class HTMLs {
	/**
	 * 空格。
	 */
	public static final String SPACE = "&nbsp;";

	/**
	 * 阻止实例化。
	 */
	private HTMLs() {
	}

	public static String fitToLength(final String str, final int length) {
		if (str == null) {
			return StringUtils.repeat(SPACE, length);
		}
		int len = str.length();
		if (len >= length) {
			return str;
		}

		StringBuilder sb = new StringBuilder();
		sb.append(str);
		for (int i = 0; i < length - len; i++) {
			sb.append(SPACE);
		}
		return sb.toString();
	}
}
