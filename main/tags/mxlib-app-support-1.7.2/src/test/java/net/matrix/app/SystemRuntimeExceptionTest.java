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

public class SystemRuntimeExceptionTest {
	@Test
	public void testSystemRuntimeException() {
		SystemRuntimeException exception = new SystemRuntimeException();
		Assert.assertEquals("System.Error", exception.getCodedMessage().getCode());
		Assert.assertEquals(CodedMessageLevel.ERROR, exception.getCodedMessage().getLevel());
		Assert.assertEquals(0, exception.getCodedMessage().getMessages().size());
	}

	@Test
	public void testSystemRuntimeException2() {
		SystemRuntimeException exception = new SystemRuntimeException(new Exception());
		Assert.assertEquals("System.Error", exception.getCodedMessage().getCode());
		Assert.assertEquals(CodedMessageLevel.ERROR, exception.getCodedMessage().getLevel());
		Assert.assertEquals(0, exception.getCodedMessage().getMessages().size());
		exception = new SystemRuntimeException(exception);
		Assert.assertEquals("System.Error", exception.getCodedMessage().getCode());
		Assert.assertEquals(CodedMessageLevel.ERROR, exception.getCodedMessage().getLevel());
		Assert.assertEquals(1, exception.getCodedMessage().getMessages().size());
	}

	@Test
	public void testSystemRuntimeException3() {
		SystemRuntimeException exception = new SystemRuntimeException(CodedMessages.information("System.Error"));
		Assert.assertEquals("System.Error", exception.getCodedMessage().getCode());
		Assert.assertEquals(CodedMessageLevel.INFORMATION, exception.getCodedMessage().getLevel());
		Assert.assertEquals(0, exception.getCodedMessage().getMessages().size());
	}

	@Test
	public void testSystemRuntimeException4() {
		SystemRuntimeException exception = new SystemRuntimeException(new Exception(), CodedMessages.information("System.Error"));
		Assert.assertEquals("System.Error", exception.getCodedMessage().getCode());
		Assert.assertEquals(CodedMessageLevel.INFORMATION, exception.getCodedMessage().getLevel());
		Assert.assertEquals(0, exception.getCodedMessage().getMessages().size());
		exception = new SystemRuntimeException(exception, CodedMessages.information("System.Error"));
		Assert.assertEquals("System.Error", exception.getCodedMessage().getCode());
		Assert.assertEquals(CodedMessageLevel.INFORMATION, exception.getCodedMessage().getLevel());
		Assert.assertEquals(1, exception.getCodedMessage().getMessages().size());
	}
}
