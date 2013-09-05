/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import java.io.Serializable;

/**
 * HQL 构造器。
 * 
 * @since 2005.06.15
 */
public class HQLBuilder
	implements Serializable, Appendable {
	private static final long serialVersionUID = 1525758570709007599L;

	private static final char[] PARAMETER_PREFIX = {
		':', 'p'
	};

	private StringBuilder sb;

	public HQLBuilder() {
		sb = new StringBuilder();
	}

	@Override
	public HQLBuilder append(char c) {
		sb.append(c);
		return this;
	}

	@Override
	public HQLBuilder append(CharSequence csq) {
		sb.append(csq);
		return this;
	}

	@Override
	public HQLBuilder append(CharSequence csq, int start, int end) {
		sb.append(csq, start, end);
		return this;
	}

	public HQLBuilder appendParameterName(int index) {
		sb.append(PARAMETER_PREFIX).append(index);
		return this;
	}

	public static String getParameterName(int index) {
		return PARAMETER_PREFIX[1] + Integer.toString(index);
	}

	public void clear() {
		sb = new StringBuilder();
	}

	@Override
	public String toString() {
		return sb.toString();
	}
}
