/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCTransactionContext
	implements HibernateTransactionContext
{
	private static final Logger LOG = LoggerFactory.getLogger(JDBCTransactionContext.class);

	private String sessionFactoryName = SessionFactoryManager.DEFAULT_NAME;

	private Session session;

	private Transaction transaction;

	@Override
	public void setSessionFactoryName(String sessionFactoryName)
	{
		if(sessionFactoryName == null){
			sessionFactoryName = SessionFactoryManager.DEFAULT_NAME;
		}
		if(this.sessionFactoryName.equals(sessionFactoryName)){
			return;
		}
		if(session != null){
			throw new IllegalStateException("Hibernate 会话已建立");
		}
		this.sessionFactoryName = sessionFactoryName;
	}

	@Override
	public String getSessionFactoryName()
	{
		return sessionFactoryName;
	}

	@SuppressWarnings("deprecation")
	@Override
	public Connection getConnection()
		throws SQLException
	{
		try{
			// TODO 考虑用其他方法获得 JDBC 连接
			return getSession().connection();
		}catch(HibernateException ex){
			throw new SQLException(ex);
		}
	}

	@Override
	public Session getSession()
		throws SQLException
	{
		if(session == null){
			try{
				session = SessionFactoryManager.getInstance().createSession(sessionFactoryName);
			}catch(HibernateException ex){
				throw new SQLException(ex);
			}
		}
		return session;
	}

	@Override
	public void begin()
		throws SQLException
	{
		if(transaction == null){
			try{
				transaction = getSession().beginTransaction();
			}catch(HibernateException ex){
				throw new SQLException(ex);
			}
		}
	}

	@Override
	public void commit()
		throws SQLException
	{
		if(transaction != null){
			try{
				transaction.commit();
			}catch(HibernateException ex){
				throw new SQLException(ex);
			}
		}
	}

	@Override
	public void rollback()
		throws SQLException
	{
		if(transaction != null){
			try{
				transaction.rollback();
			}catch(HibernateException ex){
				throw new SQLException(ex);
			}
		}
	}

	@Override
	public void release()
	{
		try{
			if(transaction != null && transaction.isActive()){
				transaction.rollback();
			}
		}catch(HibernateException ex){
			LOG.warn("Hibernate 事务回滚失败", ex);
		}finally{
			transaction = null;
		}
		try{
			if(session != null){
				session.close();
			}
		}catch(HibernateException ex){
			LOG.warn("Hibernate 会话结束失败", ex);
		}finally{
			session = null;
		}
		LOG.debug("Hibernate 会话结束");
	}
}
