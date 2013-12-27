/*
 * $Id$
 * Copyright(C) 2010 matrix
 * All right reserved.
 */
package net.matrix.app;

import org.junit.Assert;
import org.junit.Test;

import net.matrix.app.message.CodedMessageLevel;
import net.matrix.app.message.CodedMessages;

public class SystemExceptionTest {
	@Test
	public void testSystemException() {
		SystemException exception = new SystemException();
		Assert.assertEquals("System.Error", exception.getCodedMessage().getCode());
		Assert.assertEquals(CodedMessageLevel.ERROR, exception.getCodedMessage().getLevel());
		Assert.assertEquals(0, exception.getCodedMessage().getMessages().size());
	}

	@Test
	public void testSystemException2() {
		SystemException exception = new SystemException(new Exception());
		Assert.assertEquals("System.Error", exception.getCodedMessage().getCode());
		Assert.assertEquals(CodedMessageLevel.ERROR, exception.getCodedMessage().getLevel());
		Assert.assertEquals(0, exception.getCodedMessage().getMessages().size());
		exception = new SystemException(exception);
		Assert.assertEquals("System.Error", exception.getCodedMessage().getCode());
		Assert.assertEquals(CodedMessageLevel.ERROR, exception.getCodedMessage().getLevel());
		Assert.assertEquals(1, exception.getCodedMessage().getMessages().size());
	}

	@Test
	public void testSystemException3() {
		SystemException exception = new SystemException(CodedMessages.information("System.Error"));
		Assert.assertEquals("System.Error", exception.getCodedMessage().getCode());
		Assert.assertEquals(CodedMessageLevel.INFORMATION, exception.getCodedMessage().getLevel());
		Assert.assertEquals(0, exception.getCodedMessage().getMessages().size());
	}

	@Test
	public void testSystemException4() {
		SystemException exception = new SystemException(new Exception(), CodedMessages.information("System.Error"));
		Assert.assertEquals("System.Error", exception.getCodedMessage().getCode());
		Assert.assertEquals(CodedMessageLevel.INFORMATION, exception.getCodedMessage().getLevel());
		Assert.assertEquals(0, exception.getCodedMessage().getMessages().size());
		exception = new SystemException(exception, CodedMessages.information("System.Error"));
		Assert.assertEquals("System.Error", exception.getCodedMessage().getCode());
		Assert.assertEquals(CodedMessageLevel.INFORMATION, exception.getCodedMessage().getLevel());
		Assert.assertEquals(1, exception.getCodedMessage().getMessages().size());
	}
}
