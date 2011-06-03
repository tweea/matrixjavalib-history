package net.matrix.configuration;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

/**
 * 名称配置
 */
public class XMLConfigurationContainer
	implements ReloadableConfigurationContainer<XMLConfiguration>
{
	private static final Log LOG = LogFactory.getLog(XMLConfigurationContainer.class);

	private XMLConfiguration config;

	@Override
	public void load(Resource file)
		throws ConfigurationException
	{
		if(LOG.isTraceEnabled()){
			LOG.trace("加载配置文件：" + file);
		}
		try{
			config = new XMLConfiguration();
			config.setDelimiterParsingDisabled(isDelimiterParsingDisabled());
			config.load(file.getFile());
			config.setReloadingStrategy(new FileChangedReloadingStrategy());
			config.addConfigurationListener(new ConfigurationReloadingListener(this));
		}catch(IOException e){
			throw new ConfigurationException(e);
		}
		reload();
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
		LOG.debug(this.getClass().getName() + ": 重新加载");
	}

	@Override
	public XMLConfiguration getConfig()
	{
		return config;
	}

	@Override
	public void checkReload()
	{
		config.reload();
	}
}
