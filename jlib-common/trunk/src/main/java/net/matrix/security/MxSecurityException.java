/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security;

import java.security.GeneralSecurityException;

/**
 * 安全错误
 * @author Tweea
 * @since 2006-1-10
 */
public class MxSecurityException
	extends GeneralSecurityException
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -2825191153347318279L;

	public MxSecurityException()
	{
		super();
	}

	public MxSecurityException(String msg)
	{
		super(msg);
	}

	public MxSecurityException(Throwable e)
	{
		super(e);
	}

	public MxSecurityException(String string, Throwable e)
	{
		super(string, e);
	}
}
