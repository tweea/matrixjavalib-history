/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security;

import java.security.GeneralSecurityException;

/**
 * 验证错误
 */
public class AuthenticateException
	extends GeneralSecurityException
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8363292424989396451L;

	public AuthenticateException()
	{
		super();
	}

	public AuthenticateException(String msg)
	{
		super(msg);
	}

	public AuthenticateException(Throwable e)
	{
		super(e);
	}

	public AuthenticateException(String string, Throwable e)
	{
		super(string, e);
	}
}
