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
public class SystemContext
{
	private static final Log LOG = LogFactory.getLog(SystemContext.class);

	private static SystemContext GLOBAL;

	private ResourceLoader resourceLoader;

	private Configuration config;

	private SystemController controller;

	public static SystemContext global()
	{
		if(GLOBAL == null){
			GLOBAL = new SystemContext();
		}
		return GLOBAL;
	}

	public void setResourceLoader(ResourceLoader loader)
	{
		resourceLoader = loader;
	}

	public ResourceLoader getResourceLoader()
	{
		if(resourceLoader == null){
			resourceLoader = new DefaultResourceLoader();
		}
		return resourceLoader;
	}

	public void setConfig(Configuration config)
	{
		this.config = config;
	}

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

	public void setController(SystemController controller)
	{
		this.controller = controller;
	}

	public SystemController getController()
	{
		if(controller == null){
			controller = new DefaultSystemController(this);
		}
		return controller;
	}
}
