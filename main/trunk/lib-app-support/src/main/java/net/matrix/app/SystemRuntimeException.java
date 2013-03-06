/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

import org.springframework.core.NestedRuntimeException;

import net.matrix.app.message.CodedMessage;
import net.matrix.app.message.CodedMessageLevels;
import net.matrix.app.message.CodedMessageList;

/**
 * 应用系统的根异常，包含一个或多个编码消息。
 */
public class SystemRuntimeException
	extends RuntimeException
	implements CodedException {
	/**
	 * serialVersionUID。
	 */
	private static final long serialVersionUID = -6689502787927232992L;

	/**
	 * 异常包含的消息。
	 */
	private CodedMessageList messages = new CodedMessageList();

	public SystemRuntimeException() {
		messages.add(new CodedMessage(getDefaultMessageCode(), CodedMessageLevels.ERROR));
	}

	public SystemRuntimeException(final String rootMessageCode) {
		messages.add(new CodedMessage(rootMessageCode, CodedMessageLevels.ERROR));
	}

	public SystemRuntimeException(final CodedMessage rootMessage) {
		messages.add(rootMessage);
	}

	public SystemRuntimeException(final Throwable cause) {
		super(cause);
		if (cause instanceof CodedException) {
			CodedException se = (CodedException) cause;
			messages.add(new CodedMessage(getDefaultMessageCode(), CodedMessageLevels.ERROR));
			messages.addAll(se.getMessageList());
		} else {
			messages.add(new CodedMessage(getDefaultMessageCode(), CodedMessageLevels.ERROR, cause.getMessage()));
		}
	}

	public SystemRuntimeException(final Throwable cause, final String rootMessageCode) {
		super(cause);
		if (cause instanceof CodedException) {
			CodedException se = (CodedException) cause;
			messages.add(new CodedMessage(rootMessageCode, CodedMessageLevels.ERROR));
			messages.addAll(se.getMessageList());
		} else {
			messages.add(new CodedMessage(rootMessageCode, CodedMessageLevels.ERROR, cause.getMessage()));
		}
	}

	public SystemRuntimeException(final Throwable cause, final CodedMessage rootMessage) {
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

	/**
	 * Retrieve the innermost cause of this exception, if any.
	 * 
	 * @return the innermost exception, or {@code null} if none
	 */
	public Throwable getRootCause() {
		Throwable rootCause = null;
		Throwable cause = getCause();
		while (cause != null && cause != rootCause) {
			rootCause = cause;
			cause = cause.getCause();
		}
		return rootCause;
	}

	/**
	 * Retrieve the most specific cause of this exception, that is,
	 * either the innermost cause (root cause) or this exception itself.
	 * <p>
	 * Differs from {@link #getRootCause()} in that it falls back to the present exception if there
	 * is no root cause.
	 * 
	 * @return the most specific cause (never {@code null})
	 */
	public Throwable getMostSpecificCause() {
		Throwable rootCause = getRootCause();
		return (rootCause != null ? rootCause : this);
	}

	/**
	 * Check whether this exception contains an exception of the given type:
	 * either it is of the given class itself or it contains a nested cause
	 * of the given type.
	 * 
	 * @param exType
	 *            the exception type to look for
	 * @return whether there is a nested exception of the specified type
	 */
	public boolean contains(Class exType) {
		if (exType == null) {
			return false;
		}
		if (exType.isInstance(this)) {
			return true;
		}
		Throwable cause = getCause();
		if (cause == this) {
			return false;
		}
		if (cause instanceof NestedRuntimeException) {
			return ((NestedRuntimeException) cause).contains(exType);
		} else {
			while (cause != null) {
				if (exType.isInstance(cause)) {
					return true;
				}
				if (cause.getCause() == cause) {
					break;
				}
				cause = cause.getCause();
			}
			return false;
		}
	}
}
