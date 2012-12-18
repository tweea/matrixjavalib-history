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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 默认的系统环境。
 */
public class DefaultSystemContext
	implements SystemContext {
	private static final Logger LOG = LoggerFactory.getLogger(DefaultSystemContext.class);

	private ResourceLoader resourceLoader;

	private ResourcePatternResolver resourceResolver;

	private Configuration config;

	private Map<String, Object> objects;

	private SystemController controller;

	public DefaultSystemContext() {
		objects = new HashMap<String, Object>();
	}

	@Override
	public void setResourceLoader(ResourceLoader loader) {
		resourceLoader = loader;
	}

	@Override
	public ResourceLoader getResourceLoader() {
		if (resourceLoader == null) {
			resourceLoader = new DefaultResourceLoader();
		}
		return resourceLoader;
	}

	@Override
	public ResourcePatternResolver getResourcePatternResolver() {
		if (resourceResolver == null) {
			if (getResourceLoader() instanceof ResourcePatternResolver) {
				resourceResolver = (ResourcePatternResolver) getResourceLoader();
			} else {
				resourceResolver = new PathMatchingResourcePatternResolver(getResourceLoader());
			}
		}
		return resourceResolver;
	}

	@Override
	public void setConfig(Configuration config) {
		this.config = config;
	}

	@Override
	public Configuration getConfig() {
		// 尝试加载默认位置
		if (config == null) {
			LOG.info("加载默认配置");
			Resource resource = getResourceLoader().getResource("classpath:sysconfig.cfg");
			try {
				config = new PropertiesConfiguration(resource.getURL());
			} catch (IOException e) {
				throw new RuntimeException("sysconfig.cfg 加载失败", e);
			} catch (ConfigurationException e) {
				throw new RuntimeException("sysconfig.cfg 加载失败", e);
			}
		}
		return config;
	}

	@Override
	public void registerObject(String name, Object object) {
		objects.put(name, object);
	}

	@Override
	public <T> void registerObject(Class<T> type, T object) {
		registerObject(type.getName(), object);
	}

	@Override
	public Object lookupObject(String name) {
		return objects.get(name);
	}

	@Override
	public <T> T lookupObject(String name, Class<T> type) {
		return type.cast(lookupObject(name));
	}

	@Override
	public <T> T lookupObject(Class<T> type) {
		return lookupObject(type.getName(), type);
	}

	@Override
	public void setController(SystemController controller) {
		this.controller = controller;
	}

	@Override
	public SystemController getController() {
		if (controller == null) {
			controller = new DefaultSystemController();
			controller.setContext(this);
		}
		return controller;
	}
}
