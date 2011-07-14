/*
 * $Id$
 * Copyright(C) 2011 北航冠新
 * All right reserved.
 */
package net.matrix.app;

import org.junit.Assert;
import org.junit.Test;

public class DefaultSystemContextTest
{
	@Test
	public void testRegisterObject()
	{
		DefaultSystemContext context = new DefaultSystemContext();
		Object obj = new Object();
		context.registerObject(Object.class, obj);
		Assert.assertEquals(obj, context.lookupObject(Object.class));
	}
}
