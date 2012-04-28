/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

import net.matrix.app.message.CodedMessage;
import net.matrix.app.message.CodedMessageLevels;
import net.matrix.app.message.CodedMessageList;

/**
 * 应用系统的根异常，包含一个或多个编码消息。
 */
public class SystemException
	extends Exception
	implements CodedException {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -6689502787927232992L;

	/**
	 * 异常包含的消息。
	 */
	private CodedMessageList messages = new CodedMessageList();

	public SystemException() {
		messages.add(new CodedMessage(getDefaultMessageCode(), CodedMessageLevels.ERROR));
	}

	public SystemException(final String rootMessageCode) {
		messages.add(new CodedMessage(rootMessageCode, CodedMessageLevels.ERROR));
	}

	public SystemException(final CodedMessage rootMessage) {
		messages.add(rootMessage);
	}

	public SystemException(final Throwable cause) {
		super(cause);
		if (cause instanceof CodedException) {
			CodedException se = (CodedException) cause;
			messages.add(new CodedMessage(getDefaultMessageCode(), CodedMessageLevels.ERROR));
			messages.addAll(se.getMessageList());
		} else {
			messages.add(new CodedMessage(getDefaultMessageCode(), CodedMessageLevels.ERROR, cause.getMessage()));
		}
	}

	public SystemException(final Throwable cause, final String rootMessageCode) {
		super(cause);
		if (cause instanceof CodedException) {
			CodedException se = (CodedException) cause;
			messages.add(new CodedMessage(rootMessageCode, CodedMessageLevels.ERROR));
			messages.addAll(se.getMessageList());
		} else {
			messages.add(new CodedMessage(rootMessageCode, CodedMessageLevels.ERROR, cause.getMessage()));
		}
	}

	public SystemException(final Throwable cause, final CodedMessage rootMessage) {
		super(cause);
		if (cause instanceof CodedException) {
			CodedException se = (CodedException) cause;
			messages.add(rootMessage);
			messages.addAll(se.getMessageList());
		} else {
			messages.add(rootMessage);
			messages.add(new CodedMessage(getDefaultMessageCode(), CodedMessageLevels.ERROR, cause.getMessage()));
		}
	}

	@Override
	public String getDefaultMessageCode() {
		return "System.Error";
	}

	@Override
	public final CodedMessage getRootMessage() {
		return messages.get(0);
	}

	@Override
	public CodedMessageList getMessageList() {
		return messages;
	}

	@Override
	public String getMessage() {
		StringBuilder sb = new StringBuilder();
		for (CodedMessage message : messages) {
			sb.append(message.format());
			sb.append('\n');
		}
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();
	}
}
