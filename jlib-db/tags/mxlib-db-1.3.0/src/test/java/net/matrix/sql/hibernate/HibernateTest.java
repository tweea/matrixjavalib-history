/*
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import org.junit.Assert;
import org.junit.Test;

import net.matrix.sql.TransactionContext;

/**
 * Hibernate 测试
 * @author Tweea
 * @version 2005-11-30
 */
public class HibernateTest
{
	@Test
	public void testContextManager()
		throws Exception
	{
		HibernateContextManager mm = HibernateContextManager.getInstance();
		TransactionContext tc0 = mm.getTransactionContext();
		TransactionContext tc1 = mm.getTransactionContext();
		TransactionContext tc2 = mm.getTransactionContext();
		TransactionContext tc01 = mm.getTransactionContext();
		TransactionContext tc11 = mm.getTransactionContext();
		TransactionContext tc21 = mm.getTransactionContext();
		Assert.assertSame(tc0, tc01);
		Assert.assertSame(tc1, tc11);
		Assert.assertSame(tc2, tc21);
	}

	@Test
	public void testCreateDrop()
		throws Exception
	{
		HibernateContextManager mm = HibernateContextManager.getInstance();
		Assert.assertNotNull(mm.createTransactionContext());
		Assert.assertNotNull(mm.getTransactionContext());
		mm.dropTransactionContext();
		mm.dropTransactionContext();
		Assert.assertNotNull(mm.getTransactionContext());
	}

	@Test
	public void testJDBCTransactionContext()
		throws Exception
	{
		HibernateContextManager mm = HibernateContextManager.getInstance();
		TransactionContext tc = mm.getTransactionContext();
		tc.begin();
		tc.getConnection();
		tc.commit();
		tc.release();
	}

	@Test
	public void testSetConfigName()
		throws Exception
	{
		HibernateContextManager mm = HibernateContextManager.getInstance();
		HibernateTransactionContext tc = mm.getTransactionContext();
		tc.setConfigName("");
		tc.begin();
		tc.getConnection();
		tc.commit();
		tc.release();
	}
}
