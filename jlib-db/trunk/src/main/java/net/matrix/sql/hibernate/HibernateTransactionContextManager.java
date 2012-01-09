/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import net.matrix.lang.Resettable;

/**
 * Hibernate 事务上下文管理器
 */
public class HibernateTransactionContextManager
	implements Resettable
{
	private static Map<String, HibernateTransactionContextManager> managers = new HashMap<String, HibernateTransactionContextManager>();

	private ThreadLocal<Stack<HibernateTransactionContext>> threadContext;

	private HibernateTransactionContextManager()
	{
		threadContext = new ThreadLocal<Stack<HibernateTransactionContext>>();
	}

	/**
	 * 获取默认实例
	 * @return 默认实例
	 */
	public static synchronized HibernateTransactionContextManager getInstance()
	{
		return getInstance("");
	}

	/**
	 * 获取实例
	 * @param name 实例名称
	 * @return 实例
	 */
	public static synchronized HibernateTransactionContextManager getInstance(String name)
	{
		HibernateTransactionContextManager manager = managers.get(name);
		if(manager == null){
			manager = new HibernateTransactionContextManager();
			managers.put(name, manager);
		}
		return manager;
	}

	/**
	 * 删除所有事务
	 * @see net.matrix.lang.Resettable#reset()
	 */
	@Override
	public void reset()
	{
		threadContext = new ThreadLocal<Stack<HibernateTransactionContext>>();
	}

	/**
	 * 获取当前顶层事务上下文，没有则建立
	 * @return 当前顶层事务上下文
	 */
	public HibernateTransactionContext getTransactionContext()
	{
		Stack<HibernateTransactionContext> contextStack = threadContext.get();
		if(contextStack == null){
			contextStack = new Stack<HibernateTransactionContext>();
			threadContext.set(contextStack);
		}
		if(contextStack.empty()){
			contextStack.push(new JDBCTransactionContext());
		}
		return contextStack.peek();
	}

	/**
	 * 建立新的顶层事务上下文
	 * @return 顶层事务上下文
	 */
	public HibernateTransactionContext createTransactionContext()
	{
		Stack<HibernateTransactionContext> contextStack = threadContext.get();
		if(contextStack == null){
			return getTransactionContext();
		}
		HibernateTransactionContext context = new JDBCTransactionContext();
		contextStack.push(context);
		return context;
	}

	/**
	 * 丢弃顶层事务上下文
	 * @throws SQLException 回滚发生错误
	 */
	public void dropTransactionContext()
		throws SQLException
	{
		Stack<HibernateTransactionContext> contextStack = threadContext.get();
		if(contextStack == null){
			return;
		}
		if(contextStack.size() > 1){
			HibernateTransactionContext transactionToDrop = contextStack.pop();
			try{
				transactionToDrop.rollback();
			}finally{
				transactionToDrop.release();
			}
		}
	}
}
