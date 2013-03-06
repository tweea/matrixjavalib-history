/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.text;

import java.util.Locale;
import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Test;

public class ResourcesTest {
	@Test
	public void testGetBundle() {
		Locales.current(Locale.CHINA);
		ResourceBundle bundle = Resources.getBundle("global");
		Assert.assertEquals("男性", bundle.getString("male"));
	}

	@Test
	public void testGetBundle_locale() {
		ResourceBundle bundle = Resources.getBundle("global", Locale.US);
		Assert.assertEquals("爷们", bundle.getString("male"));
	}

	@Test
	public void testGetBundle_fallback() {
		ResourceBundle bundle = Resources.getBundle("fallback");
		Assert.assertEquals("male", bundle.getString("male"));
	}

	@Test
	public void testGetProperty() {
		ResourceBundle bundle = Resources.getBundle("global", Locale.US);
		Assert.assertEquals("爷们", Resources.getProperty(bundle, "male"));
		Assert.assertEquals("yes", Resources.getProperty(bundle, "yes"));
		Assert.assertEquals("OK", Resources.getProperty(bundle, "OK"));
	}

	@Test
	public void testFormatMessage() {
		ResourceBundle bundle = Resources.getBundle("global", Locale.CHINA);
		Assert.assertEquals("一双绣花鞋", Resources.formatMessage(bundle, "message", "绣花鞋"));
	}

	@Test
	public void testFormatMessage_fallback() {
		ResourceBundle bundle = Resources.getBundle("global", Locale.CHINA);
		Assert.assertEquals("1, 2, 3", Resources.formatMessage(bundle, "1", "2", 3));
	}

	@Test
	public void testFallbackFormatMessage() {
		Assert.assertEquals("1, 2, 3", Resources.formatFallbackMessage("1", "2", 3));
	}
}
