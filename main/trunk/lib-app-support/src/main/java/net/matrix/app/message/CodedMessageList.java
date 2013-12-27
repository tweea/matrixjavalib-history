/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.message;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

/**
 * 编码消息列表。
 */
public class CodedMessageList
	implements Iterable<CodedMessage> {
	private final List<CodedMessage> messages;

	public CodedMessageList() {
		this.messages = new ArrayList<CodedMessage>();
	}

	@Override
	public Iterator<CodedMessage> iterator() {
		return messages.iterator();
	}

	/**
	 * @return 消息数量
	 */
	public int size() {
		return messages.size();
	}

	/**
	 * @param index
	 *            消息索引
	 * @return 消息
	 */
	public CodedMessage get(int index) {
		return messages.get(index);
	}

	/**
	 * @param message
	 *            消息
	 */
	public void add(CodedMessage message) {
		messages.add(message);
	}

	/**
	 * @param messageList
	 *            消息记录组
	 */
	public void addAll(CodedMessageList messageList) {
		for (int index = 0; index < messageList.size(); index++) {
			add(messageList.get(index));
		}
	}

	public boolean hasLevel(CodedMessageLevel level) {
		for (CodedMessage message : messages) {
			if (ObjectUtils.equals(message.getLevel(), level)) {
				return true;
			}
		}
		return false;
	}
}
