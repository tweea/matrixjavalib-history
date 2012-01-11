/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import java.sql.SQLException;

/**
 * Hibernate 事务上下文管理器
 */
public class HibernateTransactionContextManager
{
	private String name;

	private ThreadLocal<HibernateTransactionContext> threadContext;

	public HibernateTransactionContextManager(String name)
	{
		this.name = name;
		this.threadContext = new ThreadLocal<HibernateTransactionContext>();
	}

	/**
	 * 获取当前顶层事务上下文，没有则建立
	 * @return 当前顶层事务上下文
	 */
	public HibernateTransactionContext getTransactionContext()
	{
		HibernateTransactionContext context = threadContext.get();
		if(context == null){
			context = new HibernateTransactionContext(name);
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
