/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.transaction;

import net.matrix.lang.MxException;

public class TransactionException
	extends MxException
{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 6277899196784791125L;

	public TransactionException()
	{
	}

	public TransactionException(String s)
	{
		super(s);
	}

	public TransactionException(Throwable e)
	{
		super(e);
	}

	public TransactionException(String msg, Throwable e)
	{
		super(msg, e);
	}
}
