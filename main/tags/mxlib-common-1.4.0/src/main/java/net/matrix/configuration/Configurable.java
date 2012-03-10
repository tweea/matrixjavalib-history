/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

import org.apache.commons.configuration.ConfigurationException;

/**
 * 可配置接口，实现此接口的类可以使用 CONFIG 对象进行配置
 */
public interface Configurable<CONFIG>
{
	/**
	 * 应用一个配置
	 * @param config 配置对象
	 * @throws ConfigurationException 应用配置失败
	 */
	void configure(CONFIG config)
		throws ConfigurationException;
}
