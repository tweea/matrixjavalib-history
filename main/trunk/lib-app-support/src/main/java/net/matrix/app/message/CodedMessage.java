/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.message;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import net.matrix.text.MessageFormats;

/**
 * 编码消息。
 */
public class CodedMessage {
	private String code;

	private long time;

	private int level;

	private List<String> arguments;

	public CodedMessage(String code, int level, String... arguments) {
		this(code, System.currentTimeMillis(), level, arguments);
	}

	public CodedMessage(String code, long time, int level, String... arguments) {
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
	 * @param argument
	 *            参数
	 */
	public void addArgument(String argument) {
		arguments.add(argument);
	}

	/**
	 * 格式化为消息字符串。
	 * 
	 * @return 格式化消息字符串
	 */
	public String format() {
		CodedMessageDefinition def = CodedMessageDefinition.getDefinition(code);
		if (def == null) {
			return MessageFormats.formatFallback(code, arguments);
		}
		return MessageFormat.format(def.getTemplate(), arguments.toArray());
	}
}
