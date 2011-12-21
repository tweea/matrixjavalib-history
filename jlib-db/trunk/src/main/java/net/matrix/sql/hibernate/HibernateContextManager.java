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

import org.hibernate.util.ReflectHelper;

import net.matrix.lang.Resettable;

public class HibernateContextManager
	implements Resettable
{
	private static Map<String, HibernateContextManager> managers = new HashMap<String, HibernateContextManager>();

	private Class<? extends HibernateTransactionContext> transactionContextClass = JDBCTransactionContext.class;

	private ThreadLocal<Stack<HibernateTransactionContext>> threadContext;

	private HibernateContextManager()
	{
		threadContext = new ThreadLocal<Stack<HibernateTransactionContext>>();
	}

	public static synchronized HibernateContextManager getInstance()
	{
		return getInstance("");
	}

	public static synchronized HibernateContextManager getInstance(String name)
	{
		HibernateContextManager manager = managers.get(name);
		if(manager == null){
			manager = new HibernateContextManager();
			managers.put(name, manager);
		}
		return manager;
	}

	public void setTransactionContextClass(String name)
	{
		Class klass;
		try{
			klass = ReflectHelper.classForName(name);
		}catch(ClassNotFoundException cnfe){
			throw new IllegalArgumentException("找不到类: " + name, cnfe);
		}
		transactionContextClass = klass;
	}

	/*
	 * 删除所有事务
	 * @see net.matrix.lang.Resettable#reset()
	 */
	@Override
	public void reset()
	{
		threadContext = new ThreadLocal<Stack<HibernateTransactionContext>>();
	}

	public HibernateTransactionContext getTransactionContext()
		throws SQLException
	{
		Stack<HibernateTransactionContext> contextStack = threadContext.get();
		if(contextStack == null){
			contextStack = new Stack<HibernateTransactionContext>();
			threadContext.set(contextStack);
		}
		if(contextStack.empty()){
			HibernateTransactionContext context = null;
			try{
				context = transactionContextClass.newInstance();
			}catch(Exception e){
				throw new SQLException("Could not instantiate class", e);
			}
			contextStack.push(context);
		}
		return contextStack.peek();
	}

	public HibernateTransactionContext createTransactionContext()
		throws SQLException
	{
		Stack<HibernateTransactionContext> contextStack = threadContext.get();
		if(contextStack == null){
			return getTransactionContext();
		}
		HibernateTransactionContext context = null;
		try{
			context = transactionContextClass.newInstance();
		}catch(Exception e){
			throw new SQLException("Could not instantiate class", e);
		}
		contextStack.push(context);
		return context;
	}

	public void dropTransactionContext()
		throws SQLException
	{
		Stack<HibernateTransactionContext> contextStack = threadContext.get();
		if(contextStack == null){
			return;
		}
		if(contextStack.size() > 1){
			HibernateTransactionContext transactionToDrop = contextStack.pop();
			transactionToDrop.rollback();
			transactionToDrop.release();
		}
	}
}
