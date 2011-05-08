/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.web.ecmascript.jscook;

/**
 * 根据树中的信息生成 TreeMenu 树型结构的一个节点的信息
 * @author Tweea
 * @since 2005.10.28
 */
public interface JSCookMenuItemSource<ITEM>
{
	public String getBlank();

	public StringBuffer getIcon(StringBuffer sb, ITEM item, int level);

	public StringBuffer getTitle(StringBuffer sb, ITEM item, int level);

	public StringBuffer getURL(StringBuffer sb, ITEM item, int level);

	public StringBuffer getTarget(StringBuffer sb, ITEM item, int level);

	public StringBuffer getDescription(StringBuffer sb, ITEM item, int level);
}
