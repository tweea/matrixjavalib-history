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
	public void testCodedMessage() {
		CodedMessage message = new CodedMessage("Message.Test1", CodedMessageLevel.INFORMATION);
		Assert.assertEquals("Message.Test1", message.getCode());
		Assert.assertEquals(CodedMessageLevel.INFORMATION, message.getLevel());
		Assert.assertEquals(0, message.getArguments().size());
		Assert.assertEquals(0, message.getUnformattedArguments().size());
		Assert.assertEquals(0, message.getMessages().size());
	}

	@Test
	public void testAddArgument() {
		CodedMessage message = new CodedMessage("Message.Test1", CodedMessageLevel.INFORMATION);
		Assert.assertEquals("Message.Test1", message.getCode());
		Assert.assertEquals(CodedMessageLevel.INFORMATION, message.getLevel());
		Assert.assertEquals(0, message.getArguments().size());
		message.addArgument("abc");
		Assert.assertEquals(1, message.getArguments().size());
		Assert.assertEquals("abc", message.getArguments().get(0));
	}

	@Test
	public void testAddUnformattedArgument() {
		CodedMessage message = new CodedMessage("Message.Test1", CodedMessageLevel.INFORMATION);
		Assert.assertEquals("Message.Test1", message.getCode());
		Assert.assertEquals(CodedMessageLevel.INFORMATION, message.getLevel());
		Assert.assertEquals(0, message.getArguments().size());
		message.addUnformattedArgument("abc");
		Assert.assertEquals(1, message.getUnformattedArguments().size());
		Assert.assertEquals("abc", message.getUnformattedArguments().get(0));
	}

	@Test
	public void testHasLevel() {
		CodedMessage message = new CodedMessage("Message.Test1", CodedMessageLevel.INFORMATION);
		Assert.assertTrue(message.hasLevel(CodedMessageLevel.INFORMATION));
		Assert.assertFalse(message.hasLevel(CodedMessageLevel.ERROR));
	}

	@Test
	public void testFormat() {
		CodedMessage message = new CodedMessage("Message.Test2", CodedMessageLevel.INFORMATION, "Test", "test2");
		String formatString = message.format();
		Assert.assertEquals("测试消息 B：Testtest2", formatString);
	}

	@Test
	public void testFormatFallback() {
		CodedMessage message = new CodedMessage("Message.Fallback", CodedMessageLevel.INFORMATION, "Test", "test2");
		String formatString = message.format();
		Assert.assertEquals("Message.Fallback, Test, test2", formatString);
	}

	@Test
	public void testFormatUnformatted() {
		CodedMessage message = new CodedMessage("Message.Test2", CodedMessageLevel.INFORMATION, "Test", "test2");
		message.addUnformattedArgument("12345");
		String formatString = message.format();
		Assert.assertEquals("测试消息 B：Testtest2，12345", formatString);
	}

	@Test
	public void testFormatAll() {
		CodedMessage message = new CodedMessage("Message.Test1", CodedMessageLevel.INFORMATION, "a");
		message.getMessages().add(new CodedMessage("Message.Test2", CodedMessageLevel.INFORMATION, "b", "c"));
		String formatString = message.formatAll();
		Assert.assertEquals("测试消息：a\n\t测试消息 B：bc", formatString);
	}
}
