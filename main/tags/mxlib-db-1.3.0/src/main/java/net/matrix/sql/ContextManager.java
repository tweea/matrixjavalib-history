/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql;

import java.sql.SQLException;

/**
 * 数据库连接管理
 * @author Tweea
 * @since 2005.11.10
 */
public interface ContextManager
{
	public TransactionContext getTransactionContext()
		throws SQLException;

	public TransactionContext createTransactionContext()
		throws SQLException;

	public void dropTransactionContext();
}
