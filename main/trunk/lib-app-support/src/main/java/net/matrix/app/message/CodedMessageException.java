/*
 * $Id$
 * Copyright(C) 2009 北航冠新
 * All right reserved.
 */
package net.matrix.app.message;

import net.matrix.app.SystemException;

/**
 * 配置错误
 */
public class CodedMessageException
	extends SystemException {
	private static final long serialVersionUID = 8050980676195083467L;

	public CodedMessageException() {
		super();
	}

	public CodedMessageException(Throwable cause) {
		super(cause);
	}

	public CodedMessageException(CodedMessage rootMessage) {
		super(rootMessage);
	}

	public CodedMessageException(String rootMessageCode) {
		super(rootMessageCode);
	}

	public CodedMessageException(Throwable cause, CodedMessage rootMessage) {
		super(cause, rootMessage);
	}

	public CodedMessageException(Throwable cause, String rootMessageCode) {
		super(cause, rootMessageCode);
	}

	/**
	 * @return 根消息
	 */
	@Override
	public String getDefaultMessageCode() {
		return "CodedMessage.Error";
	}
}
