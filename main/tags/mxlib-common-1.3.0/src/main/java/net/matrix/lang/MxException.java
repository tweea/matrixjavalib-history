/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.lang;

public class MxException
	extends Exception
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -621303836152776763L;

	public MxException()
	{
	}

	public MxException(String msg)
	{
		super(msg);
	}

	public MxException(Throwable e)
	{
		super(e);
	}

	public MxException(String string, Throwable e)
	{
		super(string, e);
	}
}
