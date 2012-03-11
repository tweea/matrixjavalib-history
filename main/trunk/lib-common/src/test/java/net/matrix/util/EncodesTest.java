package net.matrix.util;

import java.io.UnsupportedEncodingException;

import org.junit.Assert;
import org.junit.Test;

public class EncodesTest {
	@Test
	public void base62Encode() {
		long num = 63;

		String result = Encodes.encodeBase62(num);
		Assert.assertEquals("11", result);
		Assert.assertEquals(num, Encodes.decodeBase62(result));
	}

	@Test
	public void urlEncode()
		throws UnsupportedEncodingException {
		String input = "http://locahost/?q=中文&t=1";
		String result = Encodes.urlEncode(input);
		System.out.println(result);

		Assert.assertEquals(input, Encodes.urlDecode(result));
	}
}
