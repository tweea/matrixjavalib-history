/*
 * $Id$
 * Copyright(C) 2009 北航冠新
 * All right reserved.
 */
package net.matrix.app.repository;

import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 */
public class ResourceSelectionSetTest
{
	private static ResourceSelectionSet set1;

	private static ResourceSelectionSet set2;

	@BeforeClass
	public static void setUp()
	{
		set1 = new ResourceSelectionSet();
		set1.add(new ResourceSelection("configset", "set1/1", "configset.xml"));
		set1.add(new ResourceSelection("naming", "1", "paths.xml"));
		set1.add(new ResourceSelection("test", "1/mouse", "big.xml"));
		set1.add(new ResourceSelection("test", "2/mouse", "small.xml"));
		set1.add(new ResourceSelection("test/orz", "1", "foo.xml"));
		set1.add(new ResourceSelection("test/virtual", "1", "foo.xml"));
		set2 = new ResourceSelectionSet();
		set2.add(new ResourceSelection("configset", "set2/1", "configset.xml"));
		set2.add(new ResourceSelection("naming", "1", "paths.xml"));
		set2.add(new ResourceSelection("test", "1/rat", "big.xml"));
		set2.add(new ResourceSelection("test", "1/mouse", "small.xml"));
		set2.add(new ResourceSelection("test/orz", "1", "foo.xml"));
		set2.add(new ResourceSelection("test/orz", "1", "bar.xml"));
		set2.add(new ResourceSelection("test/virtual", "2", "foo.xml"));
	}

	@Test
	public void new1()
	{
		Assert.assertEquals(5, set1.catalogNames().size());
		Assert.assertTrue(set1.catalogNames().contains("naming"));
		Assert.assertTrue(set1.catalogNames().contains("test/orz"));
		Assert.assertEquals(2, set1.resourceNames("test").size());
		Assert.assertTrue(set1.resourceNames("test").contains("big.xml"));
		Assert.assertFalse(set1.getSelections("configset", "configset.xml").isEmpty());
	}

	@Test
	public void new2()
	{
		Assert.assertEquals(5, set2.catalogNames().size());
		Assert.assertTrue(set2.catalogNames().contains("naming"));
		Assert.assertTrue(set2.catalogNames().contains("test/orz"));
		Assert.assertEquals(2, set2.resourceNames("test").size());
		Assert.assertTrue(set2.resourceNames("test").contains("big.xml"));
		Assert.assertFalse(set2.getSelections("configset", "configset.xml").isEmpty());
	}

	@Test
	public void checkDiff()
	{
		Set<ResourceSelection> updateInfoList = set1.checkDiff(set2);
		Assert.assertEquals(5, updateInfoList.size());
	}
}
