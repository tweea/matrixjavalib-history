/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import java.sql.SQLException;

import org.hibernate.Session;

import net.matrix.sql.TransactionContext;

/**
 * Hibernate 事务上下文
 */
public interface HibernateTransactionContext
	extends TransactionContext
{
	/**
	 * 设置使用的 SessionFactory 名称
	 * @param sessionFactoryName SessionFactory 名称
	 */
	void setSessionFactoryName(String sessionFactoryName);

	/**
	 * 当前使用的 SessionFactory 名称
	 * @return SessionFactory 名称
	 */
	String getSessionFactoryName();

	/**
	 * 获取对应的 Hibernate Session
	 * @return Hibernate Session
	 * @throws SQLException 建立 Session 失败
	 */
	Session getSession()
		throws SQLException;
}
