/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import net.matrix.lang.Resettable;

/**
 * Hibernate 事务上下文管理器
 */
public class HibernateTransactionContextManager
	implements Resettable
{
	private static Map<String, HibernateTransactionContextManager> managers = new HashMap<String, HibernateTransactionContextManager>();

	private ThreadLocal<HibernateTransactionContext> threadContext;

	private HibernateTransactionContextManager()
	{
		threadContext = new ThreadLocal<HibernateTransactionContext>();
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
		threadContext.set(null);
	}

	/**
	 * 获取当前顶层事务上下文，没有则建立
	 * @return 当前顶层事务上下文
	 */
	public HibernateTransactionContext getTransactionContext()
	{
		HibernateTransactionContext context = threadContext.get();
		if(context == null){
			context = new HibernateTransactionContext();
			threadContext.set(context);
		}
		return context;
	}

	/**
	 * 丢弃顶层事务上下文
	 * @throws SQLException 回滚发生错误
	 */
	public void dropTransactionContext()
		throws SQLException
	{
		HibernateTransactionContext context = threadContext.get();
		if(context == null){
			return;
		}
		threadContext.set(null);
		try{
			context.rollback();
		}finally{
			context.release();
		}
	}
}
