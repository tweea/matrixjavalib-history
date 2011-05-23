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
	public void testFormatInputHTML()
	{
		String xx = "/abc/张三";
		Assert.assertEquals(xx, HTMLUtil.formatInputHTML(xx, 50));
	}
}
