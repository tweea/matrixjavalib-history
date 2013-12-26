/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.message;

/**
 * 编码消息。
 */
public final class CodedMessages {
	/**
	 * 阻止实例化。
	 */
	private CodedMessages() {
	}

	public static CodedMessage trace(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.TRACE, arguments);
	}

	public static CodedMessage debug(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.DEBUG, arguments);
	}

	public static CodedMessage information(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.INFORMATION, arguments);
	}

	public static CodedMessage warning(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.WARNING, arguments);
	}

	public static CodedMessage error(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.ERROR, arguments);
	}

	public static CodedMessage fatal(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.FATAL, arguments);
	}
}
