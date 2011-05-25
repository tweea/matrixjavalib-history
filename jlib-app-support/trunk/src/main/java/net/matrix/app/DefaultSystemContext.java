/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 系统环境
 */
public class DefaultSystemContext
	implements SystemContext
{
	private static final Log LOG = LogFactory.getLog(DefaultSystemContext.class);

	private ResourceLoader resourceLoader;

	private ResourcePatternResolver resourceResolver;

	private Configuration config;

	private Map<Class, Object> objects;

	private SystemController controller;

	public DefaultSystemContext()
	{
		objects = new HashMap<Class, Object>();
	}

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
	public ResourcePatternResolver getResourcePatternResolver()
	{
		if(resourceResolver == null){
			if(getResourceLoader() instanceof ResourcePatternResolver){
				resourceResolver = (ResourcePatternResolver)getResourceLoader();
			}else{
				resourceResolver = new PathMatchingResourcePatternResolver(getResourceLoader());
			}
		}
		return resourceResolver;
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
	public <T> void registerObject(Class<T> type, T object)
	{
		objects.put(type, object);
	}

	@Override
	public <T> T lookupObject(Class<T> type)
	{
		Object object = objects.get(type);
		if(object == null){
			return null;
		}
		return (T)object;
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
			controller = new DefaultSystemController();
			controller.setContext(this);
		}
		return controller;
	}
}
