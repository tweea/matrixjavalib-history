/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @since 2007 三月 1
 */
public class HibernateHelperTest
{
	@BeforeClass
	public static void setUpBeforeClass()
		throws Exception
	{
		SessionFactoryManager.getInstance();
	}

	@AfterClass
	public static void tearDownAfterClass()
		throws Exception
	{
	}

	@Test
	public void testSet()
		throws Exception
	{
		HibernateHelper.querySQLAsMap(null, "SELECT CURDATE()");
	}
}
