/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.sql.hibernate;

import org.junit.Assert;
import org.junit.Test;

/**
 * Hibernate 测试
 * 
 * @author Tweea
 * @version 2005-11-30
 */
public class HQLBuilderTest {
	@Test
	public void getParameterName()
		throws Exception {
		Assert.assertEquals("p0", HQLBuilder.getParameterName(0));
		Assert.assertEquals("p10", HQLBuilder.getParameterName(10));
	}
}
