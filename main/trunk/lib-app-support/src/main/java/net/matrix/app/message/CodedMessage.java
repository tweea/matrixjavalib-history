/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.message;

import java.util.ArrayList;
import java.util.List;

import net.matrix.text.MessageFormats;

/**
 * 编码消息。
 */
public class CodedMessage {
	/**
	 * 编码。
	 */
	private String code;

	/**
	 * 记录时间。
	 */
	private long time;

	/**
	 * 消息级别。
	 */
	private int level;

	/**
	 * 参数列表。
	 */
	private List<String> arguments;

	public CodedMessage(final String code, final int level, final String... arguments) {
		this(code, System.currentTimeMillis(), level, arguments);
	}

	public CodedMessage(final String code, final long time, final int level, final String... arguments) {
		this.code = code;
		this.time = time;
		this.level = level;
		this.arguments = new ArrayList<String>();
		for (String argument : arguments) {
			addArgument(argument);
		}
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
	public int getLevel() {
		return level;
	}

	/**
	 * @return 参数列表
	 */
	public List<String> getArguments() {
		return arguments;
	}

	/**
	 * @param index
	 *            参数索引
	 * @return 参数
	 */
	public String getArgument(int index) {
		return arguments.get(index);
	}

	/**
	 * 在参数列表中增加一个参数。
	 * 
	 * @param argument
	 *            参数
	 */
	public void addArgument(String argument) {
		arguments.add(argument);
	}

	/**
	 * 将消息格式化为字符串。
	 * 
	 * @return 消息字符串
	 */
	public String format() {
		CodedMessageDefinition def = CodedMessageDefinition.getDefinition(code);
		if (def == null) {
			return MessageFormats.formatFallback(code, arguments.toArray());
		}
		return MessageFormats.format(def.getTemplate(), def.getLocale(), arguments.toArray());
	}
}
