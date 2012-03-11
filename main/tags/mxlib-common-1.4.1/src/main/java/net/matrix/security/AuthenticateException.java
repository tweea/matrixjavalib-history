/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security;

import java.security.GeneralSecurityException;

/**
 * 身份认证失败异常。
 */
public class AuthenticateException
	extends GeneralSecurityException {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8363292424989396451L;

	public AuthenticateException() {
		super();
	}

	public AuthenticateException(final String msg) {
		super(msg);
	}

	public AuthenticateException(final Throwable e) {
		super(e);
	}

	public AuthenticateException(final String string, final Throwable e) {
		super(string, e);
	}
}
