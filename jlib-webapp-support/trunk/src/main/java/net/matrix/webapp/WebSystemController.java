/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.webapp;

import javax.servlet.ServletContext;

import net.matrix.app.SystemController;

public interface WebSystemController
	extends SystemController
{
	ServletContext getServletContext();
}
