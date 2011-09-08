/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.transaction;

/**
 * 事务处理
 * @since 2006.07.05
 */
public interface UndoOperation
{
	/**
	 * 撤销操作
	 */
	void undo();
}
