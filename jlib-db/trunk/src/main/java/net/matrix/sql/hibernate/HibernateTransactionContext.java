/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import java.sql.SQLException;

import org.hibernate.Session;

import net.matrix.sql.TransactionContext;

public interface HibernateTransactionContext
	extends TransactionContext
{
	void setConfigName(String configName);

	String getConfigName();

	Session getSession()
		throws SQLException;
}
