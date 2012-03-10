/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security;

import java.security.GeneralSecurityException;

/**
 * 授权错误
 */
public class AuthorizationException
	extends GeneralSecurityException
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 5334348169085634972L;

	public AuthorizationException()
	{
		super();
	}

	public AuthorizationException(String msg)
	{
		super(msg);
	}

	public AuthorizationException(Throwable e)
	{
		super(e);
	}

	public AuthorizationException(String string, Throwable e)
	{
		super(string, e);
	}
}
