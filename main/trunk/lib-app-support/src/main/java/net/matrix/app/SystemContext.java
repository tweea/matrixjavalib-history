/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

import org.apache.commons.configuration.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.ResourcePatternResolver;

/**
 * 系统环境。
 */
public interface SystemContext {
	void setResourceLoader(ResourceLoader loader);

	/**
	 * 获取系统资源加载器。
	 * 
	 * @return 资源加载器
	 */
	ResourceLoader getResourceLoader();

	ResourcePatternResolver getResourcePatternResolver();

	void setConfig(Configuration config);

	/**
	 * 获取系统配置。
	 * 
	 * @return 系统配置
	 */
	Configuration getConfig();

	/**
	 * 按名称注册对象。
	 * 
	 * @param name
	 *            名称
	 * @param object
	 *            对象
	 */
	void registerObject(String name, Object object);

	<T> void registerObject(Class<T> type, T object);

	/**
	 * 按照名称查询对象。
	 * 
	 * @param name
	 *            名称
	 * @return 对象
	 */
	Object lookupObject(String name);

	<T> T lookupObject(String name, Class<T> type);

	<T> T lookupObject(Class<T> type);

	void setController(SystemController controller);

	/**
	 * 返回关联的系统控制器。
	 * 
	 * @return 系统控制器
	 */
	SystemController getController();
}
