/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app.configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.commons.configuration.ConfigurationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;

import net.matrix.app.repository.ResourceContext;
import net.matrix.app.repository.ResourceContextConfig;
import net.matrix.app.repository.ResourceRepository;
import net.matrix.app.repository.ResourceSelection;
import net.matrix.configuration.ReloadableConfigurationContainer;

/**
 * 配置仓库加载环境，加载和缓存配置信息。
 */
public final class ConfigurationContext
	extends ResourceContext {
	/**
	 * 日志记录器。
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationContext.class);

	/**
	 * 缓存的配置信息。
	 */
	private Map<Resource, ReloadableConfigurationContainer> containerCache;

	/**
	 * 从资源仓库的某位置加载。
	 * 
	 * @param repository
	 *            资源仓库
	 * @param selection
	 *            资源仓库选择
	 * @return 加载环境
	 * @throws ConfigurationException
	 *             加载错误
	 */
	public static ConfigurationContext load(final ResourceRepository repository, final ResourceSelection selection)
		throws ConfigurationException {
		LOG.debug("从 {} 加载配置集合", selection);
		Resource resource = repository.getResource(selection);
		if (resource == null) {
			throw new ConfigurationException(selection + " 解析失败");
		}
		ResourceContextConfig contextConfig = new ResourceContextConfig();
		contextConfig.load(resource);
		return new ConfigurationContext(repository, contextConfig);
	}

	/**
	 * 根据必要信息构造。
	 * 
	 * @param repository
	 *            资源仓库
	 * @param contextConfig
	 *            资源仓库加载环境配置
	 */
	public ConfigurationContext(final ResourceRepository repository, final ResourceContextConfig contextConfig) {
		super(repository, contextConfig);
		this.containerCache = new ConcurrentHashMap<Resource, ReloadableConfigurationContainer>();
	}

	/**
	 * 重新加载配置，清空缓存。
	 */
	@Override
	public void reload() {
		super.reload();
		containerCache = new ConcurrentHashMap<Resource, ReloadableConfigurationContainer>();
		LOG.info("重新加载配置，清空缓存。");
	}

	/**
	 * 定位配置资源。
	 * 
	 * @param selection
	 *            配置资源选择
	 * @return 配置资源
	 * @throws ConfigurationException
	 *             配置错误
	 */
	public Resource getConfigurationResource(final ResourceSelection selection)
		throws ConfigurationException {
		Resource resource = getResource(selection);
		if (resource == null) {
			String catalog = selection.getCatalog();
			String version = selection.getVersion();
			String name = selection.getName();
			throw new ConfigurationException("未找到类别 " + catalog + " 版本 " + version + " 的配置 " + name);
		}
		return resource;
	}

	/**
	 * 加载配置资源，返回配置对象。
	 * 
	 * @param <T>
	 *            配置对象的类型
	 * @param selection
	 *            配置资源选择
	 * @return 配置对象
	 * @throws ConfigurationException
	 *             配置错误
	 */
	public <T extends ReloadableConfigurationContainer> T getConfiguration(final Class<T> type, final ResourceSelection selection)
		throws ConfigurationException {
		Resource resource = getConfigurationResource(selection);
		T container = (T) containerCache.get(resource);
		if (container != null) {
			container.checkReload();
			return container;
		}
		synchronized (containerCache) {
			container = (T) containerCache.get(resource);
			if (container != null) {
				container.checkReload();
				return container;
			}
			try {
				container = type.newInstance();
			} catch (InstantiationException e) {
				throw new ConfigurationException("配置类 " + type.getName() + " 实例化失败", e);
			} catch (IllegalAccessException e) {
				throw new ConfigurationException("配置类 " + type.getName() + " 实例化失败", e);
			}
			// 加载配置
			LOG.debug("从 {}({}) 加载配置", selection, resource);
			try {
				container.load(resource);
			} catch (ConfigurationException e) {
				throw new ConfigurationException("配置 " + resource.getDescription() + " 加载错误", e);
			}
			containerCache.put(resource, container);
			return container;
		}
	}
}
