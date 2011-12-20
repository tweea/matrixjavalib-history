/*
 * $Id$
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 支持SHA-1/MD5消息摘要的工具类.
 */
public class Digests
{
	public static final String SHA1 = "SHA-1";

	public static final String MD5 = "MD5";

	private Digests()
	{
	}

	public static byte[] sha1(byte[] input)
	{
		try{
			return digest(input, SHA1);
		}catch(NoSuchAlgorithmException e){
			throw new RuntimeException("Impossible exception", e);
		}
	}

	public static byte[] md5(byte[] input)
	{
		try{
			return digest(input, MD5);
		}catch(NoSuchAlgorithmException e){
			throw new RuntimeException("Impossible exception", e);
		}
	}

	/**
	 * 对字符串进行散列, 支持md5与sha1算法.
	 * @throws NoSuchAlgorithmException 编码错误
	 */
	public static byte[] digest(byte[] input, String algorithm)
		throws NoSuchAlgorithmException
	{
		MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
		return messageDigest.digest(input);
	}
}
