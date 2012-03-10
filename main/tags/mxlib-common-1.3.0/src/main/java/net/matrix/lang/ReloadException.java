/*
 * $Id$
 * Copyright(C) 2011 北航冠新
 * All right reserved.
 */
package net.matrix.lang;

/**
 * 重新加载失败
 */
public class ReloadException
	extends MxException
{
	private static final long serialVersionUID = 7185142142086056686L;

	public ReloadException()
	{
		super();
	}

	public ReloadException(String string, Throwable e)
	{
		super(string, e);
	}

	public ReloadException(String msg)
	{
		super(msg);
	}

	public ReloadException(Throwable e)
	{
		super(e);
	}
}
