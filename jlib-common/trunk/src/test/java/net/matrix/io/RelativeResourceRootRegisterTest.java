package net.matrix.io;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class RelativeResourceRootRegisterTest
{
	@Test
	public void registerRoot()
	{
		RelativeResourceRootRegister register = new RelativeResourceRootRegister();
		register.registerRoot("test", new ClassPathResource(""));
		Assert.assertNotNull(register.getRoot("test"));
	}

	@Test
	public void getResource()
		throws IOException
	{
		RelativeResourceRootRegister register = new RelativeResourceRootRegister();
		register.registerRoot("test", new ClassPathResource(""));
		Assert.assertTrue(register.getResource(new RelativeResource("test", "bar.xml")).getFile().exists());
	}

	@Test(expected = IllegalStateException.class)
	public void getResource1()
		throws IOException
	{
		RelativeResourceRootRegister register = new RelativeResourceRootRegister();
		register.getResource(new RelativeResource("test", "bar.xml")).getFile();
	}
}
