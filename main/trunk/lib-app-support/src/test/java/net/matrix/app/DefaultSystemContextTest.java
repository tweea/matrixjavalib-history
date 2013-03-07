/*
 * $Id$
 * Copyright(C) 2011 matrix
 * All right reserved.
 */
package net.matrix.app;

import org.junit.Assert;
import org.junit.Test;

public class DefaultSystemContextTest {
	@Test
	public void testRegisterObject() {
		DefaultSystemContext context = new DefaultSystemContext();
		Object obj = new Object();
		context.registerObject(Object.class, obj);
		Assert.assertSame(obj, context.lookupObject(Object.class));
	}
}
