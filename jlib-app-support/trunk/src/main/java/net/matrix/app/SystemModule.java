/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

/**
 * 系统模块
 */
public interface SystemModule
{
	void setContext(SystemContext context);

	SystemContext getContext();

	/**
	 * 初始化
	 */
	void init();
}
