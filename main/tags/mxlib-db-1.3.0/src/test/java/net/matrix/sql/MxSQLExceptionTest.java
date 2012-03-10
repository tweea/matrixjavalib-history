/*
 * $Id$
 * Copyright(C) 2011 北航冠新
 * All right reserved.
 */
package net.matrix.sql;

import org.junit.Assert;
import org.junit.Test;

public class MxSQLExceptionTest
{
	@Test
	public void testMxSQLExceptionThrowable()
	{
		Throwable cause = new Exception();
		Throwable e = new MxSQLException(cause);
		Assert.assertEquals(cause, e.getCause());
	}
}
