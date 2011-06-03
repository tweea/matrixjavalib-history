package net.matrix.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class XMLConfigurationContainerTest
{
	@Test
	public void load()
		throws ConfigurationException
	{
		ReloadableConfigurationContainer<XMLConfiguration> container = new XMLConfigurationContainer();
		container.load(new ClassPathResource("bar.xml"));
		Assert.assertEquals(50, container.getConfig().getInt("[@length]"));
	}
}
