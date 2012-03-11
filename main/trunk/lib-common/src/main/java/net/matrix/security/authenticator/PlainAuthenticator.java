/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security.authenticator;

import org.apache.commons.lang.StringUtils;

/**
 * 校验明文密码
 */
public class PlainAuthenticator
	implements Authenticator {
	@Override
	public boolean authenticate(String password, String digest) {
		return StringUtils.equals(digest, password);
	}

	@Override
	public String getDigestString(String password) {
		return password;
	}
}
