/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.data;

import java.util.List;

/**
 * 指明如何获得树型结构的节点
 * @author Tweea
 * @since 2005.10.28
 */
public interface TreeSource<ID, DATA>
{
	ID getRootId();

	List<ID> listChildrenId(ID parentId);

	DATA getItem(ID id);
}
