/*
 * $Id$
 * 版权所有 2013 Matrix。
 * 保留所有权利。
 */
package net.matrix.lang;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.junit.Test;

//TODO 整理并应用
public class ExceptionsTest {
	@Test
	public void unchecked() {
		Exception exception = new Exception("my exception");
		RuntimeException runtimeException = Exceptions.unchecked(exception);
		assertEquals(exception, runtimeException.getCause());

		RuntimeException runtimeException2 = Exceptions.unchecked(runtimeException);
		assertEquals(runtimeException, runtimeException2);
	}

	@Test
	public void isCausedBy() {
		IOException ioexception = new IOException("my exception");
		IllegalStateException illegalStateException = new IllegalStateException(ioexception);
		RuntimeException runtimeException = new RuntimeException(illegalStateException);

		assertTrue(Exceptions.isCausedBy(runtimeException, IOException.class));
		assertTrue(Exceptions.isCausedBy(runtimeException, IllegalStateException.class, IOException.class));
		assertTrue(Exceptions.isCausedBy(runtimeException, Exception.class));
		assertFalse(Exceptions.isCausedBy(runtimeException, IllegalAccessException.class));
	}
}
