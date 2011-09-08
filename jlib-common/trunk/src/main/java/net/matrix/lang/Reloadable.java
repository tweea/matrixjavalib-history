/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.lang;

/**
 * 可重新加载的
 * @author Tweea
 * @since 2005.06.14
 */
public interface Reloadable
{
	/**
	 * 重新加载对象状态或内容
	 * @throws ReloadException 重载失败
	 */
	void reload()
		throws ReloadException;
}
