/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

import net.matrix.lang.Resettable;

public interface SystemController
	extends Resettable
{
	void start();

	void stop();
}
