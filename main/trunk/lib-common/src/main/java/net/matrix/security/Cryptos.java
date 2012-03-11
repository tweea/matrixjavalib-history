/*
 * $Id$
 * Copyright(C) 2011 Matrix
 * All right reserved.
 */
package net.matrix.security;

import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 支持HMAC-SHA1消息签名 及 DES/AES对称加密的工具类.
 * 支持Hex与Base64两种编码方式.
 */
public class Cryptos {
	private static final String DES = "DES";

	private static final String AES = "AES";

	private static final String HMACSHA1 = "HmacSHA1";

	private static final int DEFAULT_HMACSHA1_KEYSIZE = 160;// RFC2401

	private static final int DEFAULT_AES_KEYSIZE = 128;

	private Cryptos() {
	}

	// -- HMAC-SHA1 funciton --//
	/**
	 * 使用HMAC-SHA1进行消息签名, 返回字节数组,长度为20字节.
	 * 
	 * @param key
	 *            HMAC-SHA1密钥
	 * @param input
	 *            原始输入
	 * @throws GeneralSecurityException
	 *             签名失败
	 */
	public static byte[] hmacSha1(byte[] key, byte[] input)
		throws GeneralSecurityException {
		SecretKey secretKey = new SecretKeySpec(key, HMACSHA1);
		Mac mac = Mac.getInstance(HMACSHA1);
		mac.init(secretKey);
		return mac.doFinal(input);
	}

	/**
	 * 生成HMAC-SHA1密钥,返回字节数组,长度为160位(20字节).
	 * HMAC-SHA1算法对密钥无特殊要求, RFC2401建议最少长度为160位(20字节).
	 */
	public static byte[] generateMacSha1Key()
		throws GeneralSecurityException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(HMACSHA1);
		keyGenerator.init(DEFAULT_HMACSHA1_KEYSIZE);
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}

	// -- DES function --//
	/**
	 * 使用DES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 
	 * @param inputBytes
	 *            原始字节数组
	 * @param keyBytes
	 *            符合DES要求的密钥
	 * @param mode
	 *            Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	public static byte[] des(byte[] inputBytes, byte[] keyBytes, int mode)
		throws GeneralSecurityException {
		DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(mode, secretKey);
		return cipher.doFinal(inputBytes);
	}

	/**
	 * 生成符合DES要求的密钥, 长度为64位(8字节).
	 */
	public static byte[] generateDesKey()
		throws GeneralSecurityException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(DES);
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}

	// -- AES funciton --//
	/**
	 * 使用AES加密或解密无编码的原始字节数组, 返回无编码的字节数组结果.
	 * 
	 * @param inputBytes
	 *            原始字节数组
	 * @param keyBytes
	 *            符合AES要求的密钥
	 * @param mode
	 *            Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	public static byte[] aes(byte[] inputBytes, byte[] keyBytes, int mode)
		throws GeneralSecurityException {
		SecretKey secretKey = new SecretKeySpec(keyBytes, AES);
		Cipher cipher = Cipher.getInstance(AES);
		cipher.init(mode, secretKey);
		return cipher.doFinal(inputBytes);
	}

	/**
	 * 生成AES密钥,返回字节数组,长度为128位(16字节).
	 */
	public static byte[] generateAesKey()
		throws GeneralSecurityException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
		keyGenerator.init(DEFAULT_AES_KEYSIZE);
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}
}
