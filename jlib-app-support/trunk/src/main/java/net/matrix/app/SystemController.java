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
	extends Resettable, SystemModule
{
	/**
	 * 启动
	 */
	void start();

	/**
	 * 停止
	 */
	void stop();
}
