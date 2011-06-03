/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.configuration;

/**
 * 使用一个 MxConfiguration 配置
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
