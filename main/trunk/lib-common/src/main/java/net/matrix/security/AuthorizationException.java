/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security;

import java.security.GeneralSecurityException;

/**
 * 权限检查失败异常。
 */
public class AuthorizationException
	extends GeneralSecurityException {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 5334348169085634972L;

	public AuthorizationException() {
		super();
	}

	public AuthorizationException(final String msg) {
		super(msg);
	}

	public AuthorizationException(final Throwable e) {
		super(e);
	}

	public AuthorizationException(final String string, final Throwable e) {
		super(string, e);
	}
}
