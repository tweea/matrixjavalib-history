/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.message;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class CodedMessageDefinitionTest {
	@BeforeClass
	public static void setUp() {
		CodedMessageDefinition.define(new CodedMessageDefinition("Test1", "测试消息：{0}"));
		CodedMessageDefinition.define(new CodedMessageDefinition("Test2", "测试消息 B：{0}{1}"));
	}

	@Test
	public void createUnkownDefinition() {
		CodedMessageDefinition part = CodedMessageDefinition.getDefinition("Test1");
		Assert.assertEquals("Test1", part.getCode());
		Assert.assertEquals("测试消息：{0}", part.getTemplate());
	}
}
