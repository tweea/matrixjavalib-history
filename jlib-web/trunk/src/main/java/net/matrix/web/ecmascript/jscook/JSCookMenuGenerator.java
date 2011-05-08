/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.web.ecmascript.jscook;

import net.matrix.data.Tree;

/**
 * 生成 TreeMenu 树型结构
 * @author Tweea
 * @since 2005.10.28
 */
public class JSCookMenuGenerator
{
	/**
	 * 生成 ECMAScript 中的树型结构
	 */
	public static String generateTreeData(JSCookMenuItemSource source, Tree tree)
	{
		if(tree == null){
			return source.getBlank();
		}
		StringBuffer sb = new StringBuffer(4096);
		sb.append("[");
		generateTreeData(source, tree, 0, sb);
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}

	/**
	 * 从数组中生成 ECMAScript 中的树型结构
	 */
	public static String generateTreeData(JSCookMenuItemSource source, Tree[] trees)
	{
		if(trees == null){
			return source.getBlank();
		}
		StringBuffer sb = new StringBuffer(4096);
		sb.append("[");
		for(Tree tree : trees){
			generateTreeData(source, tree, 0, sb);
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("]");
		return sb.toString();
	}

	private static void generateTreeData(JSCookMenuItemSource source, Tree<? extends Object, ? extends Object> node, int level, StringBuffer sb)
	{
		Object data = node.getData();
		sb.append("[");
		source.getIcon(sb, data, level).append(",");
		source.getTitle(sb, data, level).append(",");
		source.getURL(sb, data, level).append(",");
		source.getTarget(sb, data, level).append(",");
		source.getDescription(sb, data, level).append(",");
		for(Tree subNode : node.getChildNodes().values()){
			generateTreeData(source, subNode, level + 1, sb);
		}
		sb.deleteCharAt(sb.length() - 1);
		sb.append("],");
	}
}
