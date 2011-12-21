/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security.authenticator;

import org.apache.commons.lang.StringUtils;

import net.matrix.security.Digests;

/**
 * 使用 MD5 算法校验密码
 */
public class MD5Authenticator
	implements Authenticator
{
	@Override
	public boolean authenticate(String password, String digest)
	{
		return StringUtils.equals(digest, getDigestString(password));
	}

	@Override
	public String getDigestString(String password)
	{
		byte[] pass = password.getBytes();
		byte[] data = Digests.md5(pass);
		return toHexString(data);
	}

	private String toHexString(byte[] data)
	{
		StringBuffer sb = new StringBuffer();
		for(byte b : data){
			int i = b;
			if(i < 0){
				i += 256;
			}
			String s = Integer.toHexString(i).toUpperCase();
			if(s.length() == 1){
				sb.append('0');
			}
			sb.append(s);
		}
		return sb.toString();
	}
}
