/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import net.matrix.sql.MxSQLException;

public class JDBCTransactionContext
	extends HibernateTransactionContext
{
	private static final Log LOG = LogFactory.getLog(JDBCTransactionContext.class);

	private String configName = SessionFactoryManager.DEFAULT_NAME;

	private Session session;

	private Transaction transaction;

	@Override
	public void setConfigName(String configName)
	{
		if(configName == null){
			configName = SessionFactoryManager.DEFAULT_NAME;
		}
		if(this.configName.equals(configName)){
			return;
		}
		if(session != null){
			throw new IllegalStateException("Hibernate 会话已建立");
		}
		this.configName = configName;
	}

	@Override
	public String getConfigName()
	{
		return configName;
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
			throw new MxSQLException(ex);
		}
	}

	@Override
	public Session getSession()
		throws SQLException
	{
		if(session == null){
			try{
				session = SessionFactoryManager.getInstance().createSession(configName);
			}catch(HibernateException ex){
				throw new MxSQLException(ex);
			}
		}
		return session;
	}

	@Override
	protected void doBegin()
		throws SQLException
	{
		if(transaction == null){
			try{
				transaction = getSession().beginTransaction();
			}catch(HibernateException ex){
				throw new MxSQLException(ex);
			}
		}
	}

	@Override
	protected void doCommit()
		throws SQLException
	{
		if(transaction != null){
			try{
				transaction.commit();
			}catch(HibernateException ex){
				throw new MxSQLException(ex);
			}
		}
	}

	@Override
	protected void doRollback()
		throws SQLException
	{
		if(transaction != null){
			try{
				transaction.rollback();
			}catch(HibernateException ex){
				throw new MxSQLException(ex);
			}
		}
	}

	@Override
	protected void doRelease()
	{
		try{
			if(transaction != null && transaction.isActive()){
				doRollback();
			}
			if(session != null){
				getSession().close();
			}
			transaction = null;
			session = null;
		}catch(SQLException ex){
			LOG.warn("Hibernate 会话结束失败", ex);
		}catch(HibernateException ex){
			LOG.warn("Hibernate 会话结束失败", ex);
		}
		LOG.debug("Hibernate 会话结束");
	}
}
