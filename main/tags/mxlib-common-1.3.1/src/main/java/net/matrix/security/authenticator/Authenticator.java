/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security.authenticator;

public interface Authenticator
{
	public boolean authenticate(String password, String digest);

	public String getDigestString(String password);
}
