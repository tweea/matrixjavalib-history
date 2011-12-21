/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security.authenticator;

/**
 * 校验器
 */
public interface Authenticator
{
	/**
	 * 校验密码是否正确
	 * @param password 密码
	 * @param digest 校验参考值
	 * @return 正确返回 true
	 */
	boolean authenticate(String password, String digest);

	/**
	 * 计算密码校验参考值
	 * @param password 密码
	 * @return 校验参考值
	 */
	String getDigestString(String password);
}
