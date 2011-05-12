/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import java.sql.SQLException;

import org.hibernate.Session;

import net.matrix.sql.TransactionContext;

public abstract class HibernateTransactionContext
	extends TransactionContext
{
	public abstract void setConfigName(String configName);

	public abstract String getConfigName();

	public abstract Session getSession()
		throws SQLException;
}
