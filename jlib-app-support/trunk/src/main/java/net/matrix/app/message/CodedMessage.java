/*
 * $Id$
 * Copyright(C) 2009 北航冠新
 * All right reserved.
 */
package net.matrix.app.message;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 编码消息
 */
public class CodedMessage
{
	private String code;

	private long time;

	private int level;

	private List<String> arguments;

	public CodedMessage(String code, int level)
	{
		this(code, System.currentTimeMillis(), level);
	}

	public CodedMessage(String code, long time, int level)
	{
		this.code = code;
		this.time = time;
		this.level = level;
		this.arguments = new ArrayList<String>();
	}

	/**
	 * 建立编码消息
	 * @param code 编码
	 * @param arguments 参数
	 * @return 消息记录
	 */
	public static CodedMessage create(String code, int level, String... arguments)
	{
		CodedMessage message = new CodedMessage(code, level);
		for(String argument : arguments){
			message.addArgument(argument);
		}
		return message;
	}

	/**
	 * @return 编码
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * @return 记录时间
	 */
	public long getTime()
	{
		return time;
	}

	/**
	 * @return 消息级别
	 */
	public int getLevel()
	{
		return level;
	}

	/**
	 * @return 参数列表
	 */
	public List<String> getArguments()
	{
		return arguments;
	}

	/**
	 * @param index 参数索引
	 * @return 参数
	 */
	public String getArgument(int index)
	{
		return arguments.get(index);
	}

	/**
	 * @param argument 参数
	 */
	public void addArgument(String argument)
	{
		arguments.add(argument);
	}

	/**
	 * 格式化为消息字符串
	 * @return 格式化消息字符串
	 */
	public String format()
	{
		CodedMessageDefinition def = CodedMessageDefinition.getDefinition(code);
		if(def == null){
			def = CodedMessageDefinition.createUnkownDefinition(code, arguments.size());
			CodedMessageDefinition.define(def);
		}
		return MessageFormat.format(def.getTemplate(), arguments.toArray());
	}
}
