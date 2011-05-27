/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

import net.matrix.lang.Resettable;

/**
 * 系统控制器
 */
public interface SystemController
	extends Resettable
{
	void setContext(SystemContext context);

	SystemContext getContext();

	/**
	 * 初始化
	 */
	void init();

	/**
	 * 启动
	 */
	void start();

	/**
	 * 停止
	 */
	void stop();
}
