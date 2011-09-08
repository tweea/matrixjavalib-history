/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.transaction;

/**
 * 事务的状态
 * @since 2007-7-5
 */
public enum TransactionStatus
{
	/**
	 * 不活跃
	 */
	INACTIVE,
	/**
	 * 开始
	 */
	BEGIN,
	/**
	 * 已提交
	 */
	COMMIT,
	/**
	 * 已滚回
	 */
	ROLLBACK
}
