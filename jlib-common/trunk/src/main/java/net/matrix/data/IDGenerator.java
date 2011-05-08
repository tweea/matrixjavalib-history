/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.data;

/**
 * ID 生成器
 * @author Tweea
 * @since 2005.10.28
 */
public interface IDGenerator
{
	String nextID()
		throws IllegalStateException;
}
