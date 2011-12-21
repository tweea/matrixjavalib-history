package net.matrix.configuration;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * 读取 XML 格式的配置对象容器
 */
public class XMLConfigurationContainer
	implements ReloadableConfigurationContainer<XMLConfiguration>
{
	private static final Logger LOG = LoggerFactory.getLogger(XMLConfigurationContainer.class);

	private XMLConfiguration config;

	@Override
	public void load(Resource resource)
		throws ConfigurationException
	{
		if(LOG.isTraceEnabled()){
			LOG.trace("加载配置：" + resource);
		}
		try{
			config = new XMLConfiguration();
			config.setDelimiterParsingDisabled(isDelimiterParsingDisabled());
			try{
				config.load(resource.getFile());
				config.setReloadingStrategy(new FileChangedReloadingStrategy());
				config.addConfigurationListener(new ConfigurationReloadingListener(this));
			}catch(IOException e){
				config.load(resource.getInputStream());
			}
		}catch(IOException e){
			throw new ConfigurationException(e);
		}
		reset();
	}

	/**
	 * @return 是否解析分段字符
	 */
	protected boolean isDelimiterParsingDisabled()
	{
		return false;
	}

	@Override
	public void reload()
	{
		config.reload();
	}

	@Override
	public void checkReload()
	{
		config.reload();
	}

	@Override
	public void reset()
	{
		LOG.debug(this.getClass().getName() + ": 重新加载");
	}

	@Override
	public XMLConfiguration getConfig()
	{
		return config;
	}
}
