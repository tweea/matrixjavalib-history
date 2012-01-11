/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql;

import java.sql.SQLException;

/**
 * 事务处理
 * @since 2005.06.15
 */
public interface TransactionContext
{
	void begin()
		throws SQLException;

	void commit()
		throws SQLException;

	void rollback()
		throws SQLException;

	void release();
}
