/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security.authenticator;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 使用 MD5 算法校验密码
 */
public class MD5Authenticator
	implements Authenticator
{
	private static final Logger LOG = LoggerFactory.getLogger(MD5Authenticator.class);

	private MessageDigest messageDigest;

	public MD5Authenticator()
	{
		try{
			messageDigest = MessageDigest.getInstance("MD5");
		}catch(NoSuchAlgorithmException ex){
			LOG.warn("", ex);
		}
	}

	@Override
	public boolean authenticate(String password, String digest)
	{
		return StringUtils.equals(digest, getDigestString(password));
	}

	@Override
	public String getDigestString(String password)
	{
		byte pass[] = password.getBytes();
		byte data[] = messageDigest.digest(pass);
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
