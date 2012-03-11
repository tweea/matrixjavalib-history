/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

/**
 * 读取 XML 格式的配置对象容器。
 */
public class XMLConfigurationContainer
	implements ReloadableConfigurationContainer<XMLConfiguration> {
	/**
	 * 日志记录器
	 */
	private static final Logger LOG = LoggerFactory.getLogger(XMLConfigurationContainer.class);

	/**
	 * 原始 XML 格式配置对象。
	 */
	private XMLConfiguration config;

	/**
	 * 构造一个空的 {@code XMLConfigurationContainer}。
	 */
	public XMLConfigurationContainer() {
	}

	@Override
	public void load(final Resource resource)
		throws ConfigurationException {
		if (LOG.isTraceEnabled()) {
			LOG.trace("加载配置：" + resource);
		}
		try {
			config = new XMLConfiguration();
			config.setDelimiterParsingDisabled(isDelimiterParsingDisabled());
			try {
				config.load(resource.getFile());
				config.setReloadingStrategy(new FileChangedReloadingStrategy());
				config.addConfigurationListener(new ConfigurationReloadingListener(this));
			} catch (IOException e) {
				config.load(resource.getInputStream());
			}
		} catch (IOException e) {
			throw new ConfigurationException(e);
		}
		reset();
	}

	/**
	 * 是否解析配置内容中的分隔符。如果是配置内容中带分隔符的内容会被解析为数组，否则解析为单个值。
	 * 
	 * @return true 为解析
	 */
	protected boolean isDelimiterParsingDisabled() {
		return false;
	}

	@Override
	public void reload() {
		config.reload();
	}

	@Override
	public void checkReload() {
		config.reload();
	}

	@Override
	public void reset() {
		LOG.debug(this.getClass().getName() + ": 重新加载");
	}

	@Override
	public XMLConfiguration getConfig() {
		return config;
	}
}
