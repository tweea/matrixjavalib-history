/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.web.html;

/**
 * HTML 相关工具。
 */
public final class HTMLs {
	/**
	 * 阻止实例化。
	 */
	private HTMLs() {
	}

	public static String fitToLength(final String str, final int length) {
		StringBuilder sb = new StringBuilder();
		if (str == null) {
			for (int i = 0; i < length; i++) {
				sb.append("&nbsp;");
			}
			return sb.toString();
		}
		int len = str.length();
		if (len >= length) {
			return str;
		} else {
			sb.append(str);
			for (int i = 0; i < length - len; i++) {
				sb.append("&nbsp;");
			}
			return sb.toString();
		}
	}
}
