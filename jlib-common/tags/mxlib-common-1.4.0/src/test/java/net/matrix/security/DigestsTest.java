package net.matrix.security;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class DigestsTest
{
	@Test
	public void digestString()
		throws NoSuchAlgorithmException, UnsupportedEncodingException
	{
		String input = "foo message";
		Digests.digest(input.getBytes("UTF-8"), Digests.SHA1);
		Digests.digest(input.getBytes("UTF-8"), Digests.MD5);
	}
}
