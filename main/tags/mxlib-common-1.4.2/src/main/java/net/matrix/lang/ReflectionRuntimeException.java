/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.lang;

/**
 * 反射调用异常。
 */
public class ReflectionRuntimeException
	extends RuntimeException {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8363292424989396451L;

	public ReflectionRuntimeException() {
		super();
	}

	public ReflectionRuntimeException(final String msg) {
		super(msg);
	}

	public ReflectionRuntimeException(final Throwable e) {
		super(e);
	}

	public ReflectionRuntimeException(final String string, final Throwable e) {
		super(string, e);
	}
}
