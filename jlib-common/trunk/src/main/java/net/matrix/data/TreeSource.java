/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.data;

import java.util.List;

/**
 * 指明如何获得树型结构的节点
 */
public interface TreeSource<ID, DATA>
{
	/**
	 * @return 根数据标识
	 */
	ID getRootId();

	/**
	 * @param parentId 父节点数据标识
	 * @return 子节点数据标识
	 */
	List<ID> listChildrenId(ID parentId);

	/**
	 * @param id 数据标识
	 * @return 数据
	 */
	DATA getItem(ID id);
}
