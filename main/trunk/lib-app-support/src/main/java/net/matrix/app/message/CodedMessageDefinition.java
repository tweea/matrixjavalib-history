/*
 * $Id$
 * Copyright(C) 2009 北航冠新
 * All right reserved.
 */
package net.matrix.app.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 编码消息定义
 */
public class CodedMessageDefinition {
	private static final Map<String, CodedMessageDefinition> DEFINITIONS = new HashMap<String, CodedMessageDefinition>();

	private String code;

	private String template;

	/**
	 * 获取编码消息定义
	 * 
	 * @param code
	 *            编码
	 * @return 编码消息定义
	 */
	public static CodedMessageDefinition getDefinition(String code) {
		return DEFINITIONS.get(code);
	}

	/**
	 * 定义编码消息
	 * 
	 * @param definition
	 *            编码消息定义
	 */
	public static void define(CodedMessageDefinition definition) {
		DEFINITIONS.put(definition.getCode(), definition);
	}

	/**
	 * 默认构造器
	 */
	public CodedMessageDefinition(String code, String template) {
		this.code = code;
		this.template = template;
	}

	/**
	 * 根据编码建立未知的编码消息定义
	 * 
	 * @param code
	 *            编号
	 * @param argumentSize
	 *            参数数目
	 * @return 编码消息定义
	 */
	public static CodedMessageDefinition createUnkownDefinition(String code, int argumentSize) {
		StringBuilder sb = new StringBuilder("未定义的消息，编号：");
		sb.append(code);
		sb.append("，内容：");
		for (int i = 0; i < argumentSize; i++) {
			sb.append("{");
			sb.append(i);
			sb.append("}");
			if (i < argumentSize - 1) {
				sb.append("，");
			}
		}
		return new CodedMessageDefinition(code, sb.toString());
	}

	/**
	 * @return 编码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return 消息模板
	 */
	public String getTemplate() {
		return template;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CodedMessageDefinition other = (CodedMessageDefinition) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		return true;
	}
}
