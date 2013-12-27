/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.message;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;

import net.matrix.text.MessageFormats;

/**
 * 编码消息。
 */
public class CodedMessage {
	/**
	 * 编码。
	 */
	private final String code;

	/**
	 * 记录时间。
	 */
	private final long time;

	/**
	 * 消息级别。
	 */
	private final CodedMessageLevel level;

	/**
	 * 参数列表。
	 */
	private final List<String> arguments;

	/**
	 * 不参与格式化的参数列表。
	 */
	private final List<String> unformattedArguments;

	/**
	 * 依附消息列表。
	 */
	private final List<CodedMessage> messages;

	public CodedMessage(final String code, final CodedMessageLevel level, final String... arguments) {
		this(code, System.currentTimeMillis(), level, arguments);
	}

	public CodedMessage(final String code, final long time, final CodedMessageLevel level, final String... arguments) {
		this.code = code;
		this.time = time;
		this.level = level;
		this.arguments = new ArrayList<String>();
		for (String argument : arguments) {
			this.arguments.add(argument);
		}
		this.unformattedArguments = new ArrayList<String>();
		this.messages = new ArrayList<CodedMessage>();
	}

	/**
	 * @return 编码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return 记录时间
	 */
	public long getTime() {
		return time;
	}

	/**
	 * @return 消息级别
	 */
	public CodedMessageLevel getLevel() {
		return level;
	}

	/**
	 * @return 参数列表
	 */
	public List<String> getArguments() {
		return arguments;
	}

	/**
	 * @return 不参与格式化的参数列表。
	 */
	public List<String> getUnformattedArguments() {
		return unformattedArguments;
	}

	/**
	 * @return 依附消息列表
	 */
	public List<CodedMessage> getMessages() {
		return messages;
	}

	/**
	 * 在参数列表中增加一个参数。
	 * 
	 * @param argument
	 *            参数
	 */
	public void addArgument(final String argument) {
		arguments.add(argument);
	}

	/**
	 * 在不参与格式化的参数列表中增加一个参数。
	 * 
	 * @param argument
	 *            参数
	 */
	public void addUnformattedArgument(final String argument) {
		unformattedArguments.add(argument);
	}

	/**
	 * 判断消息中是否包含指定级别的消息。
	 * 
	 * @param targetLevel
	 *            目标级别
	 * @return true 包含
	 */
	public boolean hasLevel(final CodedMessageLevel targetLevel) {
		if (ObjectUtils.equals(level, targetLevel)) {
			return true;
		}
		for (CodedMessage message : messages) {
			if (message.hasLevel(targetLevel)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 将消息格式化为字符串。
	 * 
	 * @return 消息字符串
	 */
	public String format() {
		StringBuilder sb = new StringBuilder();
		format(sb);
		return sb.toString();
	}

	/**
	 * 将消息格式化为字符串。
	 */
	private void format(final StringBuilder sb) {
		CodedMessageDefinition def = CodedMessageDefinition.getDefinition(code);
		if (def == null) {
			sb.append(MessageFormats.formatFallback(code, arguments.toArray()));
		} else {
			sb.append(MessageFormats.format(def.getTemplate(), def.getLocale(), arguments.toArray()));
		}
		for (String unformattedArgument : unformattedArguments) {
			sb.append('，');
			sb.append(unformattedArgument);
		}
	}

	/**
	 * 将所有消息格式化为字符串。
	 * 
	 * @return 消息字符串
	 */
	public String formatAll() {
		StringBuilder sb = new StringBuilder();
		formatAll(sb, 0);
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}

	/**
	 * 将所有消息格式化为字符串。
	 */
	private void formatAll(final StringBuilder sb, final int depth) {
		for (int i = 0; i < depth; i++) {
			sb.append('\t');
		}
		format(sb);
		sb.append('\n');
		for (CodedMessage message : messages) {
			message.formatAll(sb, depth + 1);
		}
	}
}
