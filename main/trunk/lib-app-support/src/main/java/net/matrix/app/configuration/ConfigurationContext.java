package net.matrix.app.configuration;

import java.util.HashMap;
import java.util.Map;

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
 * 配置仓库加载环境
 */
public final class ConfigurationContext
	extends ResourceContext {
	private static final Logger LOG = LoggerFactory.getLogger(ConfigurationContext.class);

	private Map<Resource, ReloadableConfigurationContainer> containerCache;

	public static ConfigurationContext load(ResourceRepository repository, ResourceSelection selection)
		throws ConfigurationException {
		LOG.debug("从 " + selection + " 加载配置集合");
		Resource resource = repository.getResource(selection);
		if (resource == null) {
			throw new ConfigurationException(selection + " 解析失败");
		}
		ResourceContextConfig contextConfig = new ResourceContextConfig();
		contextConfig.load(resource);
		return new ConfigurationContext(repository, contextConfig);
	}

	public ConfigurationContext(ResourceRepository repository, ResourceContextConfig contextConfig) {
		super(repository, contextConfig);
		this.containerCache = new HashMap<Resource, ReloadableConfigurationContainer>();
	}

	public void reload() {
		getContextConfig().reload();
		containerCache = new HashMap<Resource, ReloadableConfigurationContainer>();
		LOG.info("重新加载配置");
	}

	/**
	 * 定位配置资源
	 * 
	 * @param selection
	 *            配置资源选择
	 * @return 配置资源
	 * @throws ConfigurationException
	 *             配置错误
	 */
	public Resource getConfigurationResource(ResourceSelection selection)
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
	 * @param selection
	 *            配置资源选择
	 * @return 配置对象
	 * @throws ConfigurationException
	 *             配置错误
	 */
	public <T extends ReloadableConfigurationContainer> T getConfiguration(Class<T> type, ResourceSelection selection)
		throws ConfigurationException {
		Resource resource = getConfigurationResource(selection);
		synchronized (containerCache) {
			T container = (T) containerCache.get(resource);
			if (container == null) {
				try {
					container = type.newInstance();
				} catch (InstantiationException e) {
					throw new ConfigurationException("配置类 " + type.getName() + " 实例化失败", e);
				} catch (IllegalAccessException e) {
					throw new ConfigurationException("配置类 " + type.getName() + " 实例化失败", e);
				}
				// 加载配置
				LOG.debug("从 " + selection + "(" + resource + ") 加载配置");
				try {
					container.load(resource);
				} catch (ConfigurationException e) {
					throw new ConfigurationException("配置 " + resource.getDescription() + " 加载错误", e);
				}
				containerCache.put(resource, container);
			} else {
				container.checkReload();
			}
			return container;
		}
	}
}
