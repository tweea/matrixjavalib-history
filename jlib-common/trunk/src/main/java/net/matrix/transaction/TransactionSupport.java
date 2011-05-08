/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.transaction;

/**
 * 事务处理辅助实现
 * @since 2006.07.04
 */
public class TransactionSupport
	extends AbstractTransaction
{
	TransactionSupport()
	{
	}

	@Override
	protected void doBegin()
	{
	}

	@Override
	protected void doCommit()
	{
	}

	@Override
	protected void doRollback()
	{
	}

	@Override
	protected void doRelease()
	{
	}
}
