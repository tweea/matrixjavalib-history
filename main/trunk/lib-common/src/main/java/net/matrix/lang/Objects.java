/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.lang;

public class Objects {
	private Objects() {
	}

	public static <T> T isNull(T value, T replacement) {
		return value == null ? replacement : value;
	}

	public static <T> T nullIf(T value, T value2) {
		if (value == null || value2 == null) {
			return null;
		}
		return value.equals(value2) ? null : value;
	}
}
