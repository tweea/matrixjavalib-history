/*
 * $Id$
 * Copyright(C) 2009 北航冠新
 * All right reserved.
 */
package net.matrix.app.repository;

import java.util.Set;

import org.apache.commons.configuration.ConfigurationException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

/**
 * 
 */
public class ResourceContextConfigTest
{
	private static ResourceRepository repo = new ResourceRepository(new ClassPathResource("repo1/"));

	private static ResourceSelection selection1 = new ResourceSelection("configset", "set1", "configset.xml");

	private static ResourceSelection selection2 = new ResourceSelection("configset", "set2", "configset.xml");

	private static ResourceContextConfig set1;

	private static ResourceContextConfig set2;

	@BeforeClass
	public static void setUp()
		throws ConfigurationException
	{
		set1 = new ResourceContextConfig();
		set1.load(repo.getResource(selection1));
		set2 = new ResourceContextConfig();
		set2.load(repo.getResource(selection2));
	}

	@Test
	public void new1()
	{
		Assert.assertEquals(5, set1.catalogNames().size());
		Assert.assertTrue(set1.catalogNames().contains("naming"));
		Assert.assertTrue(set1.catalogNames().contains("test/orz"));
		Assert.assertEquals(2, set1.resourceNames("test").size());
		Assert.assertTrue(set1.resourceNames("test").contains("big.xml"));
		Assert.assertNotNull(set1.getSelection("configset", "configset.xml"));
	}

	@Test
	public void new2()
	{
		Assert.assertEquals(5, set2.catalogNames().size());
		Assert.assertTrue(set2.catalogNames().contains("naming"));
		Assert.assertTrue(set2.catalogNames().contains("test/orz"));
		Assert.assertEquals(2, set2.resourceNames("test").size());
		Assert.assertTrue(set2.resourceNames("test").contains("big.xml"));
		Assert.assertNotNull(set2.getSelection("configset", "configset.xml"));
	}

	@Test
	public void checkDiff()
	{
		Set<ResourceSelection> updateInfoList = set1.checkDiff(set2);
		Assert.assertEquals(5, updateInfoList.size());
	}
}
