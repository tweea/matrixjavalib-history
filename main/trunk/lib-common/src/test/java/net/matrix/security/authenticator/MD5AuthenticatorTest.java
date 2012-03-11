/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security.authenticator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * @since 2007-1-29
 */
public class MD5AuthenticatorTest {
	/**
	 * Test method for
	 * {@link net.matrix.security.authenticator.MD5Authenticator#getDigestString(java.lang.String)}.
	 */
	@Test
	public void testGetDigestString() {
		Authenticator a = new MD5Authenticator();
		assertEquals("D41D8CD98F00B204E9800998ECF8427E", a.getDigestString(""));
		assertEquals("0CC175B9C0F1B6A831C399E269772661", a.getDigestString("a"));
		assertEquals("900150983CD24FB0D6963F7D28E17F72", a.getDigestString("abc"));
		assertEquals("F96B697D7CB7938D525A2F31AAF161D0", a.getDigestString("message digest"));
		assertEquals("C3FCD3D76192E4007DFB496CCA67E13B", a.getDigestString("abcdefghijklmnopqrstuvwxyz"));
	}
}
