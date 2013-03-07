/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 编码消息定义。
 */
public class CodedMessageDefinition {
	/**
	 * 所有加载的消息定义。
	 */
	private static final Map<String, CodedMessageDefinition> DEFINITIONS = new HashMap<String, CodedMessageDefinition>();

	private String code;

	private String template;

	/**
	 * 获取编码消息定义。
	 * 
	 * @param code
	 *            编码
	 * @return 编码消息定义
	 */
	public static CodedMessageDefinition getDefinition(String code) {
		return DEFINITIONS.get(code);
	}

	/**
	 * 定义编码消息。
	 * 
	 * @param definition
	 *            编码消息定义
	 */
	public static void define(CodedMessageDefinition definition) {
		DEFINITIONS.put(definition.getCode(), definition);
	}

	/**
	 * 默认构造器。
	 */
	public CodedMessageDefinition(String code, String template) {
		this.code = code;
		this.template = template;
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
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		CodedMessageDefinition other = (CodedMessageDefinition) obj;
		if (code == null) {
			if (other.code != null) {
				return false;
			}
		} else if (!code.equals(other.code)) {
			return false;
		}
		return true;
	}
}
