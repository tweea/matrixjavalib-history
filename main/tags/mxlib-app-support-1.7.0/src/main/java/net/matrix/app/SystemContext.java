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
 * 系统环境，保存系统运行相关的信息。
 */
public interface SystemContext {
	/**
	 * 设置系统资源加载器。
	 * 
	 * @param loader
	 *            资源加载器
	 */
	void setResourceLoader(ResourceLoader loader);

	/**
	 * 获取系统资源加载器。
	 * 
	 * @return 资源加载器
	 */
	ResourceLoader getResourceLoader();

	/**
	 * 获取系统资源扫描器。
	 * 
	 * @return 资源扫描器
	 */
	ResourcePatternResolver getResourcePatternResolver();

	/**
	 * 设置系统配置。
	 * 
	 * @param config
	 *            系统配置
	 */
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

	/**
	 * 按类型注册对象。
	 * 
	 * @param <T>
	 *            对象类型
	 * @param type
	 *            类型
	 * @param object
	 *            对象
	 */
	<T> void registerObject(Class<T> type, T object);

	/**
	 * 按照名称查询对象。
	 * 
	 * @param name
	 *            名称
	 * @return 已注册对象
	 */
	Object lookupObject(String name);

	/**
	 * 按名称和类型查询对象。
	 * 
	 * @param name
	 *            名称
	 * @param type
	 *            类型
	 * @return 已注册对象
	 */
	<T> T lookupObject(String name, Class<T> type);

	/**
	 * 按类型查询对象。
	 * 
	 * @param type
	 *            类型
	 * @return 已注册对象
	 */
	<T> T lookupObject(Class<T> type);

	/**
	 * 设置关联的系统控制器。
	 * 
	 * @param controller
	 *            系统控制器
	 */
	void setController(SystemController controller);

	/**
	 * 返回关联的系统控制器。
	 * 
	 * @return 系统控制器
	 */
	SystemController getController();
}
