/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 树型结构
 * 
 * @author Tweea
 * @since 2005.10.28
 */
public class DefaultTree<ID, DATA>
	implements Serializable, Tree<ID, DATA> {
	private static final long serialVersionUID = 1853100024141572756L;

	/**
	 * 节点标识
	 */
	private Key key;

	/**
	 * 数据标识
	 */
	private ID id;

	/**
	 * 数据对象
	 */
	private DATA data;

	/**
	 * 父节点
	 */
	private DefaultTree<ID, DATA> parent;

	/**
	 * 编号映射
	 */
	private Map<ID, Key> keyMap;

	/**
	 * 所有节点的列表
	 */
	private SortedMap<Key, DefaultTree<ID, DATA>> nodes;

	/**
	 * 种一棵树
	 * 
	 * @param id
	 *            数据标识
	 * @param data
	 *            数据对象
	 */
	public DefaultTree(ID id, DATA data) {
		this.key = new Key();
		this.id = id;
		this.data = data;
		this.parent = null;

		this.keyMap = new HashMap<ID, Key>();
		this.nodes = Collections.synchronizedSortedMap(new TreeMap<Key, DefaultTree<ID, DATA>>());

		keyMap.put(id, key);
		nodes.put(key, this);
	}

	/**
	 * 种一棵树
	 * 
	 * @param parent
	 *            父节点
	 * @param id
	 *            数据标识
	 * @param data
	 *            数据对象
	 */
	public DefaultTree(DefaultTree<ID, DATA> parent, ID id, DATA data) {
		this.key = new Key(parent.key, parent.getChildNodes().size());
		this.id = id;
		this.data = data;
		this.parent = parent;

		this.keyMap = parent.keyMap;
		this.nodes = parent.nodes;

		keyMap.put(id, key);
		nodes.put(key, this);
	}

	@Override
	public Key getKey() {
		return key;
	}

	@Override
	public void setId(ID id) {
		this.id = id;
	}

	@Override
	public ID getId() {
		return id;
	}

	@Override
	public void setData(DATA data) {
		this.data = data;
	}

	@Override
	public DATA getData() {
		return data;
	}

	@Override
	public Key findKey(ID nodeId) {
		return keyMap.get(nodeId);
	}

	@Override
	public DefaultTree<ID, DATA> getParent() {
		return parent;
	}

	/**
	 * 获得所有节点
	 */
	@Override
	public SortedMap<Key, DefaultTree<ID, DATA>> getAllNodes() {
		return nodes;
	}

	/**
	 * 获得所有子节点
	 */
	@Override
	public SortedMap<Key, DefaultTree<ID, DATA>> getChildNodes() {
		return nodes.subMap(new Key(key, 0), new Key(key, Integer.MAX_VALUE));
	}

	/**
	 * 找一个树叉
	 */
	@Override
	public DefaultTree<ID, DATA> getNode(Key nodeKey) {
		return nodes.get(nodeKey);
	}

	/**
	 * 找一个树叉
	 */
	@Override
	public DefaultTree<ID, DATA> getNode(ID nodeId) {
		Key nodeKey = findKey(nodeId);
		if (nodeKey == null) {
			return null;
		}
		return getNode(nodeKey);
	}

	/**
	 * 找一个树叉
	 */
	@Override
	public DefaultTree<ID, DATA> getChildNode(Key nodeKey) {
		return getChildNodes().get(nodeKey);
	}

	/**
	 * 找一个树叉
	 */
	@Override
	public DefaultTree<ID, DATA> getChildNode(ID nodeId) {
		Key nodeKey = findKey(nodeId);
		if (nodeKey == null) {
			return null;
		}
		return getChildNode(nodeKey);
	}

	/**
	 * 增加新的子节点
	 */
	@Override
	public DefaultTree<ID, DATA> appendChildNode(ID nodeId, DATA nodeData) {
		return new DefaultTree<ID, DATA>(this, nodeId, nodeData);
	}

	/**
	 * 移除子节点
	 */
	@Override
	public void removeChildNode(Key nodeKey) {
		DefaultTree<ID, DATA> node = getChildNode(nodeKey);
		if (node == null) {
			return;
		}
		for (Key childKey : new HashSet<Key>(node.getChildNodes().keySet())) {
			node.removeChildNode(childKey);
		}
		keyMap.remove(node.id);
		nodes.remove(nodeKey);
	}

	/**
	 * 移除子节点
	 */
	@Override
	public void removeChildNode(ID nodeId) {
		Key nodeKey = findKey(nodeId);
		if (nodeKey == null) {
			return;
		}
		removeChildNode(nodeKey);
	}

	@Override
	public boolean isRoot() {
		return parent == null;
	}

	@Override
	public boolean isLeaf() {
		return getChildNodes().size() == 0;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(super.toString()).append("[id=").append(id);
		sb.append(",key=").append(key);
		sb.append(",parent=").append(parent == null ? "null" : parent.getKey());
		sb.append(",data=").append(data).append(",subnodes=").append(getChildNodes().values()).append("]");
		return sb.toString();
	}

	/*
	 * 从 TreeSource 产生树状结构
	 */
	public static synchronized <ID, DATA> DefaultTree<ID, DATA> generate(TreeSource<ID, DATA> source) {
		ID rootId = source.getRootId();
		DATA rootData = source.getItem(rootId);
		DefaultTree<ID, DATA> tree = new DefaultTree<ID, DATA>(rootId, rootData);
		generateSubNode(source, tree);
		return tree;
	}

	/*
	 * 从 TreeSource 增加新的节点
	 */
	private static <ID, DATA> void generateSubNode(TreeSource<ID, DATA> source, DefaultTree<ID, DATA> node) {
		List<ID> items = source.listChildrenId(node.getId());
		if (items == null || items.size() == 0) {
			return;
		}
		for (ID id : items) {
			DATA item = source.getItem(id);
			node.appendChildNode(id, item);
		}
		for (DefaultTree<ID, DATA> subNode : new ArrayList<DefaultTree<ID, DATA>>(node.getChildNodes().values())) {
			generateSubNode(source, subNode);
		}
	}
}
