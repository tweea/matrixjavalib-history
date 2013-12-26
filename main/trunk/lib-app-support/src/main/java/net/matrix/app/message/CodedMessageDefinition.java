/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.message;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import net.matrix.text.Locales;

/**
 * 编码消息定义。
 */
public class CodedMessageDefinition {
	/**
	 * 加载的编码消息定义。
	 */
	private static final Map<String, Map<Locale, CodedMessageDefinition>> DEFINITIONS = new HashMap<String, Map<Locale, CodedMessageDefinition>>();

	/**
	 * 编码。
	 */
	private final String code;

	/**
	 * 区域。
	 */
	private final Locale locale;

	/**
	 * 模板。
	 */
	private final String template;

	/**
	 * 获取编码消息定义。
	 * 
	 * @param code
	 *            编码
	 * @return 编码消息定义
	 */
	public static CodedMessageDefinition getDefinition(String code) {
		Map<Locale, CodedMessageDefinition> definitions = DEFINITIONS.get(code);
		if (definitions == null) {
			return null;
		}

		CodedMessageDefinition definition = definitions.get(Locales.current());
		if (definition == null) {
			definition = definitions.get(Locale.ROOT);
		}
		return definition;
	}

	/**
	 * 定义编码消息。
	 * 
	 * @param definition
	 *            编码消息定义
	 */
	public static void define(final CodedMessageDefinition definition) {
		String code = definition.getCode();
		Locale locale = definition.getLocale();

		Map<Locale, CodedMessageDefinition> definitions = DEFINITIONS.get(code);
		if (definitions == null) {
			definitions = new HashMap<Locale, CodedMessageDefinition>();
			DEFINITIONS.put(code, definitions);
		}
		definitions.put(locale, definition);
	}

	/**
	 * 默认构造器。
	 */
	public CodedMessageDefinition(final String code, final Locale locale, final String template) {
		this.code = code;
		this.locale = locale;
		this.template = template;
	}

	/**
	 * @return 编码
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @return 区域
	 */
	public Locale getLocale() {
		return locale;
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
		result = prime * result + ((locale == null) ? 0 : locale.hashCode());
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
		if (locale == null) {
			if (other.locale != null) {
				return false;
			}
		} else if (!locale.equals(other.locale)) {
			return false;
		}
		return true;
	}
}
