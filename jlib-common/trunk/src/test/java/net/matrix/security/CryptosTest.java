package net.matrix.security;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;

import org.junit.Assert;
import org.junit.Test;

public class CryptosTest
{
	@Test
	public void mac()
		throws GeneralSecurityException, UnsupportedEncodingException
	{
		String input = "foo message";

		// key可为任意字符串
		// byte[] key = "a foo key".getBytes();
		byte[] key = Cryptos.generateMacSha1Key();
		Assert.assertEquals(20, key.length);

		byte[] macResult = Cryptos.hmacSha1(input.getBytes("UTF-8"), key);
		Assert.assertEquals(20, macResult.length);
	}

	@Test
	public void des()
		throws GeneralSecurityException, UnsupportedEncodingException
	{
		String input = "foo message";

		byte[] key = Cryptos.generateDesKey();
		Assert.assertEquals(8, key.length);

		byte[] encryptResult = Cryptos.des(input.getBytes("UTF-8"), key, Cipher.ENCRYPT_MODE);
		byte[] descryptResult = Cryptos.des(encryptResult, key, Cipher.DECRYPT_MODE);
		Assert.assertEquals(input, new String(descryptResult, "UTF-8"));
	}

	@Test
	public void aes()
		throws GeneralSecurityException, UnsupportedEncodingException
	{
		String input = "foo message";

		byte[] key = Cryptos.generateAesKey();
		Assert.assertEquals(16, key.length);

		byte[] encryptResult = Cryptos.aes(input.getBytes("UTF-8"), key, Cipher.ENCRYPT_MODE);
		byte[] descryptResult = Cryptos.aes(encryptResult, key, Cipher.DECRYPT_MODE);
		Assert.assertEquals(input, new String(descryptResult, "UTF-8"));
	}
}
