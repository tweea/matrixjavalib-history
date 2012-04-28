/*
 * $Id$
 * Copyright(C) 2010 matrix
 * All right reserved.
 */
package net.matrix.app;

import org.junit.Assert;
import org.junit.Test;

import net.matrix.app.message.CodedMessage;
import net.matrix.app.message.CodedMessageLevels;

public class SystemExceptionTest {
	@Test
	public void testSystemException() {
		SystemException exception = new SystemException();
		Assert.assertEquals("System.Error", exception.getRootMessage().getCode());
		Assert.assertEquals(CodedMessageLevels.ERROR, exception.getRootMessage().getLevel());
		Assert.assertEquals(1, exception.getMessageList().size());
	}

	@Test
	public void testSystemException2() {
		SystemException exception = new SystemException(new Exception());
		Assert.assertEquals("System.Error", exception.getRootMessage().getCode());
		Assert.assertEquals(CodedMessageLevels.ERROR, exception.getRootMessage().getLevel());
		Assert.assertEquals(1, exception.getMessageList().size());
		exception = new SystemException(exception);
		Assert.assertEquals("System.Error", exception.getRootMessage().getCode());
		Assert.assertEquals(CodedMessageLevels.ERROR, exception.getRootMessage().getLevel());
		Assert.assertEquals(2, exception.getMessageList().size());
	}

	@Test
	public void testSystemException3() {
		SystemException exception = new SystemException(new CodedMessage("System.Error", CodedMessageLevels.INFORMATION));
		Assert.assertEquals("System.Error", exception.getRootMessage().getCode());
		Assert.assertEquals(CodedMessageLevels.INFORMATION, exception.getRootMessage().getLevel());
		Assert.assertEquals(1, exception.getMessageList().size());
	}

	@Test
	public void testSystemException4() {
		SystemException exception = new SystemException(new Exception(), new CodedMessage("System.Error", CodedMessageLevels.INFORMATION));
		Assert.assertEquals("System.Error", exception.getRootMessage().getCode());
		Assert.assertEquals(CodedMessageLevels.INFORMATION, exception.getRootMessage().getLevel());
		Assert.assertEquals(2, exception.getMessageList().size());
		exception = new SystemException(exception, new CodedMessage("System.Error", CodedMessageLevels.INFORMATION));
		Assert.assertEquals("System.Error", exception.getRootMessage().getCode());
		Assert.assertEquals(CodedMessageLevels.INFORMATION, exception.getRootMessage().getLevel());
		Assert.assertEquals(3, exception.getMessageList().size());
	}
}
