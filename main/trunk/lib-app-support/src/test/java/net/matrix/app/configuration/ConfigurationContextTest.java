package net.matrix.app.configuration;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import net.matrix.app.repository.ResourceRepository;
import net.matrix.app.repository.ResourceSelection;
import net.matrix.configuration.ReloadableConfigurationContainer;
import net.matrix.configuration.XMLConfigurationContainer;
import net.matrix.io.RelativeResource;
import net.matrix.io.RelativeResourceRootRegister;

public class ConfigurationContextTest
{
	@Test
	public void ConfigContext()
		throws ConfigurationException
	{
		ResourceRepository repository = new ResourceRepository(new ClassPathResource("repo1/"));
		ResourceSelection selection = new ResourceSelection("configset", "set2", "configset.xml");
		ConfigurationContext context = ConfigurationContext.load(repository, selection);

		Assert.assertEquals(5, context.getContextConfig().catalogNames().size());
	}

	public ConfigurationContext loadContext()
		throws ConfigurationException
	{
		ResourceRepository repository = new ResourceRepository(new ClassPathResource("repo1/"));
		ResourceSelection selection = new ResourceSelection("configset", "set1", "configset.xml");
		return ConfigurationContext.load(repository, selection);
	}

	@Test
	public void getConfigurationResource()
		throws ConfigurationException, IOException
	{
		ConfigurationContext context = loadContext();
		RelativeResourceRootRegister register = new RelativeResourceRootRegister();
		register.registerRoot("test", new ClassPathResource("repo1/"));

		Resource resource = context.getConfigurationResource(new ResourceSelection("test", null, "small.xml"));
		Assert.assertEquals(register.getResource(new RelativeResource("test", "test/small.xml")), resource);
		resource = context.getConfigurationResource(new ResourceSelection("test", "1", "middle.xml"));
		Assert.assertEquals(register.getResource(new RelativeResource("test", "test/1/middle.xml")), resource);
		resource = context.getConfigurationResource(new ResourceSelection("test", "1/mouse", "big.xml"));
		Assert.assertEquals(register.getResource(new RelativeResource("test", "test/1/mouse/big.xml")), resource);
		resource = context.getConfigurationResource(new ResourceSelection("test", "1/rat", "middle.xml"));
		Assert.assertEquals(register.getResource(new RelativeResource("test", "test/1/middle.xml")), resource);
	}

	@Test
	public void getConfiguration()
		throws ConfigurationException
	{
		ConfigurationContext context = loadContext();
		ResourceSelection selection = new ResourceSelection("test", null, "small.xml");
		ReloadableConfigurationContainer<XMLConfiguration> container = context.getConfiguration(XMLConfigurationContainer.class, selection);
		Assert.assertEquals(50, container.getConfig().getInt("[@length]"));
	}

	@Test(expected = ConfigurationException.class)
	public void getConfiguration2()
		throws ConfigurationException
	{
		loadContext().getConfiguration(ReloadableConfigurationContainer.class, new ResourceSelection("testxxx", null, "abc.xml")).getConfig();
	}

	@Test(expected = ConfigurationException.class)
	public void getConfiguration3()
		throws ConfigurationException
	{
		loadContext().getConfiguration(ReloadableConfigurationContainer.class, new ResourceSelection("test", null, "abc")).getConfig();
	}
}
