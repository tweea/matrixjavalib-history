/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.message;

import java.util.Locale;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CodedMessageTest {
	@BeforeClass
	public static void setUp() {
		CodedMessageDefinition.define(new CodedMessageDefinition("Message.Test1", Locale.ROOT, "测试消息：{0}"));
		CodedMessageDefinition.define(new CodedMessageDefinition("Message.Test2", Locale.ROOT, "测试消息 B：{0}{1}"));
	}

	@Test
	public void logMessage() {
		CodedMessage message = new CodedMessage("Message.Test1", CodedMessageLevels.INFORMATION);
		Assert.assertEquals("Message.Test1", message.getCode());
		Assert.assertEquals(CodedMessageLevels.INFORMATION, message.getLevel());
		Assert.assertEquals(0, message.getArguments().size());
	}

	@Test
	public void addArgument() {
		CodedMessage message = new CodedMessage("Message.Test1", CodedMessageLevels.INFORMATION);
		Assert.assertEquals("Message.Test1", message.getCode());
		Assert.assertEquals(CodedMessageLevels.INFORMATION, message.getLevel());
		Assert.assertEquals(0, message.getArguments().size());
		message.addArgument("abc");
		Assert.assertEquals(1, message.getArguments().size());
		Assert.assertEquals("abc", message.getArgument(0));
	}

	@Test
	public void format() {
		CodedMessage message = new CodedMessage("Message.Test2", CodedMessageLevels.INFORMATION);
		message.addArgument("Test");
		message.addArgument("test2");
		String formatString = message.format();
		Assert.assertEquals("测试消息 B：Testtest2", formatString);
	}

	@Test
	public void formatFallback() {
		CodedMessage message = new CodedMessage("Message.Fallback", CodedMessageLevels.INFORMATION);
		message.addArgument("Test");
		message.addArgument("test2");
		String formatString = message.format();
		Assert.assertEquals("Message.Fallback, Test, test2", formatString);
	}
}
