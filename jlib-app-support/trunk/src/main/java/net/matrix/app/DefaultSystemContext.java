/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

import java.io.IOException;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

/**
 * 系统环境
 */
public class DefaultSystemContext
	implements SystemContext
{
	private static final Log LOG = LogFactory.getLog(DefaultSystemContext.class);

	private ResourceLoader resourceLoader;

	private Configuration config;

	private SystemController controller;

	@Override
	public void setResourceLoader(ResourceLoader loader)
	{
		resourceLoader = loader;
	}

	@Override
	public ResourceLoader getResourceLoader()
	{
		if(resourceLoader == null){
			resourceLoader = new DefaultResourceLoader();
		}
		return resourceLoader;
	}

	@Override
	public void setConfig(Configuration config)
	{
		this.config = config;
	}

	@Override
	public Configuration getConfig()
	{
		// 尝试加载默认位置
		if(config == null){
			LOG.info("加载默认配置");
			Resource resource = getResourceLoader().getResource("classpath:sysconfig.cfg");
			try{
				config = new PropertiesConfiguration(resource.getURL());
			}catch(IOException e){
				throw new RuntimeException("sysconfig.cfg 加载失败", e);
			}catch(ConfigurationException e){
				throw new RuntimeException("sysconfig.cfg 加载失败", e);
			}
		}
		return config;
	}

	@Override
	public void setController(SystemController controller)
	{
		this.controller = controller;
	}

	@Override
	public SystemController getController()
	{
		if(controller == null){
			controller = new DefaultSystemController(this);
		}
		return controller;
	}
}
