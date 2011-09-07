/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

/**
 * 可配置接口，实现此接口的类可以使用 CONFIG 对象进行配置
 */
public interface MxConfigurable<CONFIG>
{
	/**
	 * 应用一个配置
	 * @param config 配置
	 * @return 是否成功
	 */
	boolean configure(CONFIG config);
}
