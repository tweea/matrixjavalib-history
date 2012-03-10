/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.lang;

/**
 * 反射调用错误
 */
public class ReflectionRuntimeException
	extends RuntimeException
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -8363292424989396451L;

	public ReflectionRuntimeException()
	{
		super();
	}

	public ReflectionRuntimeException(String msg)
	{
		super(msg);
	}

	public ReflectionRuntimeException(Throwable e)
	{
		super(e);
	}

	public ReflectionRuntimeException(String string, Throwable e)
	{
		super(string, e);
	}
}
