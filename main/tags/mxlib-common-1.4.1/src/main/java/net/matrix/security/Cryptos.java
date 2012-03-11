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
 * 支持 HMAC-SHA1 消息签名及 DES/AES 对称加密的工具类。
 * 支持 Hex 与 Base64 两种编码方式。
 */
public final class Cryptos {
	private static final String DES = "DES";

	private static final String AES = "AES";

	private static final String HMACSHA1 = "HmacSHA1";

	/**
	 * RFC2401 建议 HMAC-SHA1 密钥最小长度。
	 */
	private static final int DEFAULT_HMACSHA1_KEYSIZE = 160;

	/**
	 * AES 密钥长度。
	 */
	private static final int DEFAULT_AES_KEYSIZE = 128;

	/**
	 * 阻止实例化。
	 */
	private Cryptos() {
	}

	// -- HMAC-SHA1 funciton --//
	/**
	 * 使用 HMAC-SHA1 进行消息签名，返回字节数组，长度为 20 字节。
	 * 
	 * @param key
	 *            HMAC-SHA1 密钥
	 * @param input
	 *            原始输入
	 * @return 签名
	 * @throws GeneralSecurityException
	 *             签名失败
	 */
	public static byte[] hmacSha1(final byte[] key, final byte[] input)
		throws GeneralSecurityException {
		SecretKey secretKey = new SecretKeySpec(key, HMACSHA1);
		Mac mac = Mac.getInstance(HMACSHA1);
		mac.init(secretKey);
		return mac.doFinal(input);
	}

	/**
	 * 生成 HMAC-SHA1 密钥，返回字节数组，长度为 160 位(20字节)。
	 * HMAC-SHA1 算法对密钥无特殊要求，RFC2401 建议最少长度为 160 位(20 字节)。
	 * 
	 * @return HMAC-SHA1 密钥
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
	 * 使用 DES 加密或解密无编码的原始字节数组，返回无编码的字节数组结果。
	 * 
	 * @param inputBytes
	 *            原始字节数组
	 * @param keyBytes
	 *            符合DES要求的密钥
	 * @param mode
	 *            Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 * @return 密文或明文
	 */
	public static byte[] des(final byte[] inputBytes, final byte[] keyBytes, final int mode)
		throws GeneralSecurityException {
		DESKeySpec desKeySpec = new DESKeySpec(keyBytes);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES);
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);

		Cipher cipher = Cipher.getInstance(DES);
		cipher.init(mode, secretKey);
		return cipher.doFinal(inputBytes);
	}

	/**
	 * 生成符合 DES 要求的密钥，长度为 64 位(8 字节)。
	 * 
	 * @return DES 密钥
	 */
	public static byte[] generateDesKey()
		throws GeneralSecurityException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(DES);
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}

	// -- AES funciton --//
	/**
	 * 使用 AES 加密或解密无编码的原始字节数组，返回无编码的字节数组结果。
	 * 
	 * @param inputBytes
	 *            原始字节数组
	 * @param keyBytes
	 *            符合 AES 要求的密钥
	 * @param mode
	 *            Cipher.ENCRYPT_MODE 或 Cipher.DECRYPT_MODE
	 * @return 密文或明文
	 */
	public static byte[] aes(final byte[] inputBytes, final byte[] keyBytes, final int mode)
		throws GeneralSecurityException {
		SecretKey secretKey = new SecretKeySpec(keyBytes, AES);
		Cipher cipher = Cipher.getInstance(AES);
		cipher.init(mode, secretKey);
		return cipher.doFinal(inputBytes);
	}

	/**
	 * 生成 AES 密钥，返回字节数组，长度为 128 位(16 字节)。
	 * 
	 * @return AES 密钥
	 */
	public static byte[] generateAesKey()
		throws GeneralSecurityException {
		KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
		keyGenerator.init(DEFAULT_AES_KEYSIZE);
		SecretKey secretKey = keyGenerator.generateKey();
		return secretKey.getEncoded();
	}
}
