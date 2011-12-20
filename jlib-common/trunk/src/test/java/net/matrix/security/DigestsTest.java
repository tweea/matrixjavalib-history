package net.matrix.security;

import java.security.NoSuchAlgorithmException;

import org.junit.Test;

public class DigestsTest
{
	@Test
	public void digestString()
		throws NoSuchAlgorithmException
	{
		String input = "foo message";
		Digests.digest(input, Digests.SHA1);
		Digests.digest(input, Digests.MD5);
	}
}
