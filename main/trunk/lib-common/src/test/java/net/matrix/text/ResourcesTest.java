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
		ResourceBundle bundle = Resources.getBundle("global");
		Assert.assertEquals("男性", bundle.getString("male"));
	}

	@Test
	public void testGetBundle_locale() {
		ResourceBundle bundle = Resources.getBundle("global", Locale.US);
		Assert.assertEquals("爷们", bundle.getString("male"));
	}

	@Test
	public void testGetProperty() {
		ResourceBundle bundle = Resources.getBundle("global", Locale.US);
		Assert.assertEquals("爷们", Resources.getProperty(bundle, "male"));
		Assert.assertEquals("yes", Resources.getProperty(bundle, "yes"));
		Assert.assertEquals("OK", Resources.getProperty(bundle, "OK"));
	}
}
