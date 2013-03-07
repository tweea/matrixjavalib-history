/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

import net.matrix.lang.Resettable;

/**
 * 系统控制器，负责系统的初始化、启动、停止。
 */
public interface SystemController
	extends Resettable {
	/**
	 * 设置与控制器关联的系统环境。
	 * 
	 * @param context
	 *            系统环境
	 */
	void setContext(SystemContext context);

	/**
	 * 获取与控制器关联的系统环境。
	 * 
	 * @return 系统环境
	 */
	SystemContext getContext();

	/**
	 * 初始化系统。
	 */
	void init();

	/**
	 * 启动系统。
	 */
	void start();

	/**
	 * 停止系统。
	 */
	void stop();
}
