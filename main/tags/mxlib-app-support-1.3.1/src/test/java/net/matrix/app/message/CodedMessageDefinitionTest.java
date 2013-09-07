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

import net.matrix.text.Locales;

public class CodedMessageDefinitionTest {
	@BeforeClass
	public static void setUp() {
		CodedMessageDefinition.define(new CodedMessageDefinition("Test1", Locale.ROOT, "message1"));
		CodedMessageDefinition.define(new CodedMessageDefinition("Test1", Locale.CHINA, "message2"));
		CodedMessageDefinition.define(new CodedMessageDefinition("Test2", Locale.ROOT, "messageB"));
	}

	@Test
	public void testGetDefinition() {
		CodedMessageDefinition part = CodedMessageDefinition.getDefinition("notfound");
		Assert.assertNull(part);

		Locales.current(Locale.CHINA);
		part = CodedMessageDefinition.getDefinition("Test1");
		Assert.assertEquals("Test1", part.getCode());
		Assert.assertEquals("message2", part.getTemplate());
		part = CodedMessageDefinition.getDefinition("Test2");
		Assert.assertEquals("Test2", part.getCode());
		Assert.assertEquals("messageB", part.getTemplate());

		Locales.current(Locale.ENGLISH);
		part = CodedMessageDefinition.getDefinition("Test1");
		Assert.assertEquals("Test1", part.getCode());
		Assert.assertEquals("message1", part.getTemplate());
		part = CodedMessageDefinition.getDefinition("Test2");
		Assert.assertEquals("Test2", part.getCode());
		Assert.assertEquals("messageB", part.getTemplate());
	}
}
