package net.matrix.configuration;

import org.apache.commons.configuration.ConfigurationException;
import org.springframework.core.io.Resource;

import net.matrix.lang.Reloadable;

/**
 * 配置对象容器
 */
public interface ReloadableConfigurationContainer<CONFIG>
	extends Reloadable
{
	/**
	 * 从资源加载配置
	 * @param resource 资源
	 * @throws ConfigurationException 加载失败
	 */
	public void load(Resource resource)
		throws ConfigurationException;

	/**
	 * 获得已加载的配置对象
	 * @return 配置对象
	 */
	public CONFIG getConfig();

	/**
	 * 检查是否需要重载，如果需要就重载
	 */
	public void checkReload();
}
