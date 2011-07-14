/*
 * $Id$
 * Copyright(C) 2009 北航冠新
 * All right reserved.
 */
package net.matrix.app.message;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CodedMessageTest
{
	@BeforeClass
	public static void setUp()
	{
		CodedMessageDefinition.define(new CodedMessageDefinition("Test1", "测试消息：{0}"));
		CodedMessageDefinition.define(new CodedMessageDefinition("Test2", "测试消息 B：{0}{1}"));
	}

	@Test
	public void logMessage()
	{
		CodedMessage message = new CodedMessage("Test1", CodedMessageLevels.INFORMATION);
		Assert.assertEquals("Test1", message.getCode());
		Assert.assertEquals(CodedMessageLevels.INFORMATION, message.getLevel());
		Assert.assertEquals(0, message.getArguments().size());
	}

	@Test
	public void addArgument()
	{
		CodedMessage message = new CodedMessage("Test1", CodedMessageLevels.INFORMATION);
		Assert.assertEquals("Test1", message.getCode());
		Assert.assertEquals(CodedMessageLevels.INFORMATION, message.getLevel());
		Assert.assertEquals(0, message.getArguments().size());
		message.addArgument("abc");
		Assert.assertEquals(1, message.getArguments().size());
		Assert.assertEquals("abc", message.getArgument(0));
	}

	@Test
	public void format()
	{
		CodedMessage message = new CodedMessage("Test2", CodedMessageLevels.INFORMATION);
		message.addArgument("Test");
		message.addArgument("test2");
		String formatString = message.format();
		Assert.assertEquals("测试消息 B：Testtest2", formatString);
	}
}
