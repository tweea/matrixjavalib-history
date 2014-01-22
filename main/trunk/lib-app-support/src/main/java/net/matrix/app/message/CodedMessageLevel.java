/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.message;

import java.util.HashMap;
import java.util.Map;

/**
 * 编码消息级别。
 */
public enum CodedMessageLevel {
	/**
	 * 跟踪。
	 */
	TRACE(1),

	/**
	 * 调试。
	 */
	DEBUG(2),

	/**
	 * 消息。
	 */
	INFORMATION(3),

	/**
	 * 警告。
	 */
	WARNING(4),

	/**
	 * 错误。
	 */
	ERROR(5),

	/**
	 * 致命错误。
	 */
	FATAL(6);

	/**
	 * 用于按编码查找。
	 */
	private static final Map<Integer, CodedMessageLevel> CODE_MAP;

	/**
	 * 编码。
	 */
	private final Integer code;

	static {
		CODE_MAP = new HashMap<Integer, CodedMessageLevel>();
		for (CodedMessageLevel level : values()) {
			CODE_MAP.put(level.code, level);
		}
	}

	private CodedMessageLevel(final Integer code) {
		this.code = code;
	}

	/**
	 * 根据编码查找。
	 * 
	 * @param code
	 *            编码
	 * @return 编码消息级别
	 */
	public static CodedMessageLevel forCode(final Integer code) {
		return CODE_MAP.get(code);
	}

	public Integer getCode() {
		return code;
	}
}
