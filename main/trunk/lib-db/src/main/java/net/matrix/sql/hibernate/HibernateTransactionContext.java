/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import java.sql.SQLException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.matrix.sql.TransactionContext;

/**
 * Hibernate 事务上下文
 */
public class HibernateTransactionContext
	implements TransactionContext {
	private static final Logger LOG = LoggerFactory.getLogger(HibernateTransactionContext.class);

	private String sessionFactoryName;

	private Session session;

	private Transaction transaction;

	/**
	 * 使用默认的 SessionFactory 构建
	 */
	public HibernateTransactionContext() {
		sessionFactoryName = SessionFactoryManager.DEFAULT_NAME;
	}

	/**
	 * 使用指定的 SessionFactory 名称构建
	 * 
	 * @param sessionFactoryName
	 *            SessionFactory 名称
	 */
	public HibernateTransactionContext(String sessionFactoryName) {
		if (sessionFactoryName == null) {
			sessionFactoryName = SessionFactoryManager.DEFAULT_NAME;
		}
		this.sessionFactoryName = sessionFactoryName;
	}

	/**
	 * 获取对应的 Hibernate Session
	 * 
	 * @return Hibernate Session
	 * @throws SQLException
	 *             建立 Session 失败
	 */
	public Session getSession()
		throws SQLException {
		if (session == null) {
			try {
				session = SessionFactoryManager.getInstance(sessionFactoryName).createSession();
			} catch (HibernateException ex) {
				throw new SQLException(ex);
			}
		}
		return session;
	}

	@Override
	public void begin()
		throws SQLException {
		if (transaction == null) {
			try {
				transaction = getSession().beginTransaction();
			} catch (HibernateException ex) {
				throw new SQLException(ex);
			}
		}
	}

	@Override
	public void commit()
		throws SQLException {
		if (transaction != null) {
			try {
				transaction.commit();
				transaction = null;
			} catch (HibernateException ex) {
				throw new SQLException(ex);
			}
		}
	}

	@Override
	public void rollback()
		throws SQLException {
		if (transaction != null) {
			try {
				transaction.rollback();
			} catch (HibernateException ex) {
				throw new SQLException(ex);
			} finally {
				transaction = null;
			}
		}
	}

	@Override
	public void release() {
		if (transaction != null) {
			try {
				if (transaction.isActive()) {
					transaction.rollback();
				}
			} catch (HibernateException ex) {
				LOG.warn("Hibernate 事务回滚失败", ex);
			} finally {
				transaction = null;
			}
		}
		if (session != null) {
			try {
				session.close();
				LOG.debug("Hibernate 会话结束");
			} catch (HibernateException ex) {
				LOG.warn("Hibernate 会话结束失败", ex);
			} finally {
				session = null;
			}
		}
	}
}
