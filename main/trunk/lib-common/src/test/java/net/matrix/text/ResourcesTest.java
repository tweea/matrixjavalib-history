package net.matrix.text;

import java.util.ResourceBundle;

import org.junit.Assert;
import org.junit.Test;

public class ResourcesTest {
	@Test
	public void testGetBundle() {
		ResourceBundle bundle = Resources.getBundle("global");
		Assert.assertEquals("男性", bundle.getString("male"));
	}
}
