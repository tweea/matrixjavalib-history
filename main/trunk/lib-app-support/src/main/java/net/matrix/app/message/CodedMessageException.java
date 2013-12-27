/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.message;

import net.matrix.app.SystemException;

/**
 * 编码消息错误。
 */
public class CodedMessageException
	extends SystemException {
	private static final long serialVersionUID = 8050980676195083467L;

	public CodedMessageException() {
		super();
	}

	public CodedMessageException(final String rootMessageCode) {
		super(rootMessageCode);
	}

	public CodedMessageException(final CodedMessage rootMessage) {
		super(rootMessage);
	}

	public CodedMessageException(final Throwable cause) {
		super(cause);
	}

	public CodedMessageException(final Throwable cause, final String rootMessageCode) {
		super(cause, rootMessageCode);
	}

	public CodedMessageException(final Throwable cause, final CodedMessage rootMessage) {
		super(cause, rootMessage);
	}

	@Override
	public String getDefaultMessageCode() {
		return "CodedMessage.Error";
	}
}
