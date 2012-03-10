/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql;

import java.sql.Connection;
import java.sql.SQLException;

import net.matrix.transaction.AbstractTransaction;

/**
 * 事务处理
 * @author Tweea
 * @since 2005.06.15
 */
public abstract class TransactionContext
	extends AbstractTransaction
{
	public abstract Connection getConnection()
		throws SQLException;
}
