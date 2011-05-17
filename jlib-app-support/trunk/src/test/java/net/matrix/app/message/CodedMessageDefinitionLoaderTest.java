/*
 * $Id$
 * Copyright(C) 2009 北航冠新
 * All right reserved.
 */
package net.matrix.app.message;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 */
public class CodedMessageDefinitionLoaderTest
{
	@BeforeClass
	public static void setUp()
	{
		CodedMessageDefinitionLoader.loadDefinitions();
	}

	@Test
	public void getDefinition()
	{
		CodedMessageDefinition part = CodedMessageDefinition.getDefinition("Test");
		Assert.assertEquals("Test", part.getCode());
		Assert.assertEquals("测试消息：{0}", part.getTemplate());
	}
}
