package net.matrix.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.core.io.Resource;

import net.matrix.lang.Resettable;

/**
 * 支持重新加载的配置对象容器，用于从指定资源加载内容形成配置对象
 */
public interface ReloadableConfigurationContainer<CONFIG>
	extends Resettable
{
	/**
	 * 从资源加载配置
	 * @param resource 资源
	 * @throws ConfigurationException 加载失败
	 */
	void load(Resource resource)
		throws ConfigurationException;

	/**
	 * 从资源重新加载配置
	 * @throws ConfigurationException 重新加载失败
	 */
	void reload()
		throws ConfigurationException;

	/**
	 * 检查是否需要重载，如果需要就重载
	 */
	void checkReload();

	/**
	 * 获得已加载的配置对象
	 * @return 配置对象
	 */
	CONFIG getConfig();
}
