/*
 * $Id$
 * Copyright(C) 2010 北航冠新
 * All right reserved.
 */
package net.matrix.app.repository;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class ResourceRepositoryTest
{
	@Test
	public void getFile0()
	{
		ResourceRepository repo = new ResourceRepository(new ClassPathResource("repo1/"));
		// 默认文件名
		Assert.assertTrue(repo.getResource(new ResourceSelection("test", "1", null)).exists());
		Assert.assertNull(repo.getResource(new ResourceSelection("test/orz", "1", null)));
	}

	@Test
	public void getFile1()
	{
		ResourceRepository repo = new ResourceRepository(new ClassPathResource("repo1/"));
		// 分类
		Assert.assertTrue(repo.getResource(new ResourceSelection("naming", "1", "paths.xml")).exists());
		Assert.assertTrue(repo.getResource(new ResourceSelection("test", "1", "middle.xml")).exists());
		Assert.assertNull(repo.getResource(new ResourceSelection("test1", "1", "middle.xml")));
		// 版本
		Assert.assertNull(repo.getResource(new ResourceSelection("test", "2", "middle.xml")));
		// 文件名
		Assert.assertNull(repo.getResource(new ResourceSelection("test", "1", "middle1.xml")));
		Assert.assertNull(repo.getResource(new ResourceSelection("test", "1", "big.xml")));
	}

	@Test
	public void getFile2()
	{
		ResourceRepository repo = new ResourceRepository(new ClassPathResource("repo1/"));
		// 默认版本
		Assert.assertTrue(repo.getResource(new ResourceSelection("test", null, "small.xml")).exists());
		Assert.assertNull(repo.getResource(new ResourceSelection("test", null, "big.xml")));
		Assert.assertNull(repo.getResource(new ResourceSelection("test/orz", null, "small.xml")));
	}

	@Test
	public void getFile3()
	{
		ResourceRepository repo = new ResourceRepository(new ClassPathResource("repo1/"));
		// 多级
		Assert.assertTrue(repo.getResource(new ResourceSelection("test/orz", "1", "bar.xml")).exists());
		Assert.assertNull(repo.getResource(new ResourceSelection("test/orz", "2", "big.xml")));
		Assert.assertNull(repo.getResource(new ResourceSelection("test/ora", "1", "big.xml")));
		Assert.assertTrue(repo.getResource(new ResourceSelection("test", "1/mouse", "big.xml")).exists());
		Assert.assertTrue(repo.getResource(new ResourceSelection("test", "1/mouse", "middle.xml")).exists());
		Assert.assertNull(repo.getResource(new ResourceSelection("test", "2/rat", "big.xml")));
		Assert.assertTrue(repo.getResource(new ResourceSelection("test", "1/rat", "small.xml")).exists());
	}
}
