/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.security;

import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * 支持 HMAC-SHA1 消息签名及 DES/AES 对称加密的工具类。
 * 支持 Hex 与 Base64 两种编码方式。
 */
public class Cryptos {
	private static final String AES = "AES";

	private static final String AES_CBC = "AES/CBC/PKCS5Padding";

	private static final String HMACSHA1 = "HmacSHA1";

	// RFC2401
	private static final int DEFAULT_HMACSHA1_KEYSIZE = 160;

	private static final int DEFAULT_AES_KEYSIZE = 128;

	private static final int DEFAULT_IVSIZE = 16;

	private static final SecureRandom RANDOM = new SecureRandom();

	private Cryptos() {
	}

	// -- HMAC-SHA1 funciton --//
	/**
	 * 使用 HMAC-SHA1 进行消息签名，返回字节数组,长度为 20 字节。
	 * 
	 * @param input
	 *            原始输入字符数组
	 * @param key
	 *            HMAC-SHA1 密钥
	 */
	public static byte[] hmacSha1(byte[] input, byte[] key)
		throws GeneralSecurityException {
		try {
			SecretKey secretKey = new SecretKeySpec(key, HMACSHA1);
			Mac mac = Mac.getInstance(HMACSHA1);
			mac.init(secretKey);
			return mac.doFinal(input);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 校验 HMAC-SHA1 签名是否正确。
	 * 
	 * @param expected
	 *            已存在的签名
	 * @param input
	 *            原始输入字符串
	 * @param key
	 *            密钥
	 */
	public static boolean isMacValid(byte[] expected, byte[] input, byte[] key)
		throws GeneralSecurityException {
		byte[] actual = hmacSha1(input, key);
		return Arrays.equals(expected, actual);
	}

	/**
	 * 生成 HMAC-SHA1 密钥，返回字节数组，长度为 160 位(20字节)。
	 * HMAC-SHA1 算法对密钥无特殊要求，RFC2401 建议最少长度为 160 位(20字节)。
	 */
	public static byte[] generateHmacSha1Key() {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(HMACSHA1);
			keyGenerator.init(DEFAULT_HMACSHA1_KEYSIZE);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	// -- AES funciton --//
	/**
	 * 使用 AES 加密原始字符串。
	 * 
	 * @param input
	 *            原始输入字符数组
	 * @param key
	 *            符合 AES 要求的密钥
	 */
	public static byte[] aesEncrypt(byte[] input, byte[] key)
		throws GeneralSecurityException {
		return aes(input, key, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 使用 AES 加密原始字符串。
	 * 
	 * @param input
	 *            原始输入字符数组
	 * @param key
	 *            符合 AES 要求的密钥
	 * @param iv
	 *            初始向量
	 */
	public static byte[] aesEncrypt(byte[] input, byte[] key, byte[] iv)
		throws GeneralSecurityException {
		return aes(input, key, iv, Cipher.ENCRYPT_MODE);
	}

	/**
	 * 使用 AES 解密字符串，返回原始字符串。
	 * 
	 * @param input
	 *            Hex 编码的加密字符串
	 * @param key
	 *            符合 AES 要求的密钥
	 */
	public static String aesDecrypt(byte[] input, byte[] key)
		throws GeneralSecurityException {
		byte[] decryptResult = aes(input, key, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * 使用 AES 解密字符串，返回原始字符串。
	 * 
	 * @param input
	 *            Hex 编码的加密字符串
	 * @param key
	 *            符合 AES 要求的密钥
	 * @param iv
	 *            初始向量
	 */
	public static String aesDecrypt(byte[] input, byte[] key, byte[] iv)
		throws GeneralSecurityException {
		byte[] decryptResult = aes(input, key, iv, Cipher.DECRYPT_MODE);
		return new String(decryptResult);
	}

	/**
	 * 使用 AES 加密或解密无编码的原始字节数组，返回无编码的字节数组结果。
	 * 
	 * @param input
	 *            原始字节数组
	 * @param key
	 *            符合 AES 要求的密钥
	 * @param mode
	 *            Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] aes(byte[] input, byte[] key, int mode)
		throws GeneralSecurityException {
		try {
			SecretKey secretKey = new SecretKeySpec(key, AES);
			Cipher cipher = Cipher.getInstance(AES);
			cipher.init(mode, secretKey);
			return cipher.doFinal(input);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 使用 AES 加密或解密无编码的原始字节数组，返回无编码的字节数组结果。
	 * 
	 * @param input
	 *            原始字节数组
	 * @param key
	 *            符合 AES 要求的密钥
	 * @param iv
	 *            初始向量
	 * @param mode
	 *            Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 */
	private static byte[] aes(byte[] input, byte[] key, byte[] iv, int mode)
		throws GeneralSecurityException {
		try {
			SecretKey secretKey = new SecretKeySpec(key, AES);
			IvParameterSpec ivSpec = new IvParameterSpec(iv);
			Cipher cipher = Cipher.getInstance(AES_CBC);
			cipher.init(mode, secretKey, ivSpec);
			return cipher.doFinal(input);
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		} catch (NoSuchPaddingException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 生成 AES 密钥，返回字节数组，默认长度为 128 位(16 字节)。
	 */
	public static byte[] generateAesKey() {
		return generateAesKey(DEFAULT_AES_KEYSIZE);
	}

	/**
	 * 生成 AES 密钥，可选长度为 128,192,256 位。
	 */
	public static byte[] generateAesKey(int keysize) {
		try {
			KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
			keyGenerator.init(keysize);
			SecretKey secretKey = keyGenerator.generateKey();
			return secretKey.getEncoded();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 生成随机向量，默认大小为 cipher.getBlockSize()，16字节。
	 */
	public static byte[] generateIV() {
		byte[] bytes = new byte[DEFAULT_IVSIZE];
		RANDOM.nextBytes(bytes);
		return bytes;
	}
}
