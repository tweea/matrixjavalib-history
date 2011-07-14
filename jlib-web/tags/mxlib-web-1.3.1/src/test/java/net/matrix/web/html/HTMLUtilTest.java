/*
 * $Id$
 * Copyright(C) 2011 北航冠新
 * All right reserved.
 */
package net.matrix.web.html;

import org.junit.Assert;
import org.junit.Test;

public class HTMLUtilTest
{
	@Test
	public void fitToLength()
	{
		String xx = "abc";
		String yy = "abc&nbsp;&nbsp;";
		Assert.assertEquals(yy, HTMLUtil.fitToLength(xx, 5));
	}
}
