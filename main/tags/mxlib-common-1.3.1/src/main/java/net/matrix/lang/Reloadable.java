/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.lang;

/**
 * 可重载的
 * @author Tweea
 * @since 2005.06.14
 */
public interface Reloadable
{
	void reload()
		throws ReloadException;
}
