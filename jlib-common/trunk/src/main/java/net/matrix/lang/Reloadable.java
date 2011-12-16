/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.lang;

/**
 * 实现此接口的对象可以从预定义位置或配置的位置重新加载对象的状态或内容。
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
