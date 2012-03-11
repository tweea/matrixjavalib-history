/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql;

import java.sql.SQLException;

/**
 * 事务处理
 * 
 * @since 2005.06.15
 */
public interface TransactionContext {
	/**
	 * 启动事务
	 * 
	 * @throws SQLException
	 *             启动失败
	 */
	void begin()
		throws SQLException;

	/**
	 * 提交事务
	 * 
	 * @throws SQLException
	 *             提交失败
	 */
	void commit()
		throws SQLException;

	/**
	 * 撤销事务
	 * 
	 * @throws SQLException
	 *             撤销失败
	 */
	void rollback()
		throws SQLException;

	/**
	 * 释放事务资源
	 */
	void release();
}
