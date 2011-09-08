/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.transaction;

import java.util.Stack;

/**
 * 事务处理管理器
 * @since 2006.07.04
 */
public class TransactionManager
{
	private static final ThreadLocal<TransactionManager> THREAD_MANAGERS = new ThreadLocal<TransactionManager>();

	// 当前事务，现在只能有一个事务
	private Stack<Transaction> transactions;

	private TransactionManager()
	{
		transactions = new Stack<Transaction>();
		Transaction transaction = new TransactionSupport();
		transactions.push(transaction);
	}

	/**
	 * 获取唯一实例
	 * @return 唯一实例
	 */
	public static TransactionManager getInstance()
	{
		TransactionManager manager = THREAD_MANAGERS.get();
		if(manager == null){
			manager = new TransactionManager();
			THREAD_MANAGERS.set(manager);
		}
		return manager;
	}

	/**
	 * 设置顶级事务
	 * @param transaction 事务
	 */
	public void setTransaction(Transaction transaction)
	{
		if(!transactions.empty()){
			Transaction transactionToDrop = transactions.pop();
			transactionToDrop.rollback();
			transactionToDrop.release();
		}
		transactions.push(transaction);
	}

	/**
	 * 获取顶级事务
	 */
	public Transaction getTransaction()
	{
		return transactions.peek();
	}

	/**
	 * 创建事务
	 */
	public Transaction createTransaction()
	{
		Transaction transaction = new TransactionSupport();
		transactions.push(transaction);
		return transaction;
	}

	/**
	 * 丢弃事务
	 */
	public void dropTransaction()
	{
		if(transactions.size() > 1){
			Transaction transactionToDrop = transactions.pop();
			transactionToDrop.rollback();
			transactionToDrop.release();
		}
	}
}
