package net.matrix.text.resources;

import org.junit.Assert;
import org.junit.Test;

public class ResourcesTest
{
	@Test
	public void testResources()
	{
		Resources res = Resources.getResources("global");
		Assert.assertEquals("男性", res.getProperty("male"));
	}
}
