/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

/**
 * HQL 构造器。
 * 
 * @since 2005.06.15
 */
public class HQLBuilder {
	private static final char[] PARAMETER_PREFIX = {
		':', 'p'
	};

	private StringBuilder sb;

	public HQLBuilder() {
		sb = new StringBuilder();
	}

	public HQLBuilder appendParameterName(int index) {
		sb.append(PARAMETER_PREFIX).append(index);
		return this;
	}

	public static String getParameterName(int index) {
		return PARAMETER_PREFIX[1] + Integer.toString(index);
	}
}
