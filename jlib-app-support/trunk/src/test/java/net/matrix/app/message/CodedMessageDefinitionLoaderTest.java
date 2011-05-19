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
		CodedMessageDefinition part = CodedMessageDefinition.getDefinition("System.Error");
		Assert.assertEquals("System.Error", part.getCode());
		Assert.assertEquals("系统发生错误：{0}", part.getTemplate());
	}
}
