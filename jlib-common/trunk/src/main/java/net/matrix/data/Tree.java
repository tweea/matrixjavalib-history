/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.data;

import java.io.Serializable;
import java.util.SortedMap;

/**
 * 树型结构
 * @author Tweea
 * @since 2005.10.28
 */
public interface Tree<ID, DATA>
{
	/**
	 * @return 节点标识
	 */
	Key getKey();

	/**
	 * @param id 数据标识
	 */
	void setId(ID id);

	/**
	 * @return 数据标识
	 */
	ID getId();

	/**
	 * @param data 数据
	 */
	void setData(DATA data);

	/**
	 * @return 数据
	 */
	DATA getData();

	/**
	 * 通过数据标识查找节点标识
	 * @param id 数据标识
	 * @return 节点标识
	 */
	Key findKey(ID id);

	/**
	 * @return 上级节点
	 */
	Tree<ID, DATA> getParent();

	/**
	 * 获得所有节点
	 */
	SortedMap<Key, ? extends Tree<ID, DATA>> getAllNodes();

	/**
	 * 获得所有子节点
	 */
	SortedMap<Key, ? extends Tree<ID, DATA>> getChildNodes();

	/**
	 * 获得节点
	 */
	Tree<ID, DATA> getNode(Key key);

	/**
	 * 获得节点
	 */
	Tree<ID, DATA> getNode(ID id);

	/**
	 * 获得子节点
	 */
	Tree<ID, DATA> getChildNode(Key key);

	/**
	 * 获得子节点
	 */
	Tree<ID, DATA> getChildNode(ID id);

	/**
	 * 增加新的子节点
	 */
	Tree<ID, DATA> appendChildNode(ID id, DATA data);

	/**
	 * 移除子节点
	 */
	void removeChildNode(Key key);

	/**
	 * 移除子节点
	 */
	void removeChildNode(ID id);

	/**
	 * @return 是否根节点
	 */
	boolean isRoot();

	/**
	 * @return 是否叶节点
	 */
	boolean isLeaf();

	/**
	 * 标识节点在树中的位置
	 */
	class Key
		implements Comparable<Key>, Serializable
	{
		private static final long serialVersionUID = 6009469890625904428L;

		private Key parent;

		private int level;

		private int index;

		/**
		 * hashCode()
		 */
		private int hash;

		/**
		 * toString()
		 */
		private String string;

		public Key()
		{
			this(0);
		}

		private Key(int index)
		{
			this.parent = null;
			this.level = 0;
			this.index = index;
		}

		public Key(Key parent, int index)
		{
			this.parent = parent;
			this.level = parent.level + 1;
			this.index = index;
		}

		public Key getParent()
		{
			return parent;
		}

		public int getIndex()
		{
			return index;
		}

		public int getLevel()
		{
			return level;
		}

		@Override
		public int compareTo(Key o)
		{
			if(level < o.level){
				return -1;
			}else if(level > o.level){
				return 1;
			}
			if(level != 0){
				int p = parent.compareTo(o.parent);
				if(p != 0){
					return p;
				}
			}
			if(index < o.index){
				return -1;
			}else if(index > o.index){
				return 1;
			}
			return 0;
		}

		@Override
		public boolean equals(Object obj)
		{
			if(obj instanceof Key){
				Key ok = (Key)obj;
				if(level != ok.level){
					return false;
				}
				if(level != 0 && !parent.equals(ok.parent)){
					return false;
				}
				return index == ok.index;
			}
			return false;
		}

		@Override
		public int hashCode()
		{
			if(hash == 0){
				if(parent != null){
					hash = parent.hashCode() * 31 + index;
				}else{
					hash = 31 + index;
				}
			}
			return hash;
		}

		@Override
		public String toString()
		{
			if(string == null){
				if(parent != null){
					string = parent.toString() + "," + index;
				}else{
					string = "" + index;
				}
			}
			return string;
		}
	}
}
