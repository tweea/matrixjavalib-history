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
	SystemContext getContext();

	void start();

	void stop();
}
