/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.transaction;

/**
 * 事务处理
 * @since 2006.07.04
 */
public interface Transaction
{
	/**
	 * 获取事务当前状态
	 */
	TransactionStatus getStatus();

	/**
	 * 事务当前是否开始
	 */
	boolean isActive();

	/**
	 * 开始一个事务
	 * @throws TransactionException 事务开始失败
	 */
	void begin()
		throws TransactionException;

	/**
	 * 提交一个事务
	 * @throws TransactionException 事务提交失败
	 */
	void commit()
		throws TransactionException;

	/**
	 * 滚回事务
	 */
	void rollback();

	/**
	 * 完成事务，若未提交则滚回
	 */
	void release();

	/**
	 * 增加一个撤销操作
	 * @param undo 撤销操作
	 */
	void addUndoOperation(UndoOperation undo);
}
