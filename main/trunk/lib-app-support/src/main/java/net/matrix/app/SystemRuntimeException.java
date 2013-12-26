/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

import net.matrix.app.message.CodedMessage;
import net.matrix.app.message.CodedMessageLevel;
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

	/**
	 * 使用默认消息构造异常。原因异常没有初始化，可以随后调用 {@link #initCause} 进行初始化。
	 */
	public SystemRuntimeException() {
		messages.add(new CodedMessage(getDefaultMessageCode(), CodedMessageLevel.ERROR));
	}

	/**
	 * 使用指定消息编码构造异常。原因异常没有初始化，可以随后调用 {@link #initCause} 进行初始化。
	 * 
	 * @param rootMessageCode
	 *            消息编码。
	 */
	public SystemRuntimeException(final String rootMessageCode) {
		messages.add(new CodedMessage(rootMessageCode, CodedMessageLevel.ERROR));
	}

	/**
	 * 使用指定消息构造异常。原因异常没有初始化，可以随后调用 {@link #initCause} 进行初始化。
	 * 
	 * @param rootMessage
	 *            消息。
	 */
	public SystemRuntimeException(final CodedMessage rootMessage) {
		messages.add(rootMessage);
	}

	/**
	 * 使用指定原因异常构造异常，详细信息指定为 <tt>(cause==null ? null : cause.toString())</tt> （特别地指定原因异常的类和详细信息）。
	 * 
	 * @param cause
	 *            原因异常（使用 {@link #getCause()} 方法获取）。可以使用 <tt>null</tt> 值，指原因异常不存在或未知。
	 */
	public SystemRuntimeException(final Throwable cause) {
		super(cause);
		if (cause instanceof CodedException) {
			CodedException se = (CodedException) cause;
			messages.add(new CodedMessage(getDefaultMessageCode(), CodedMessageLevel.ERROR));
			messages.addAll(se.getMessageList());
		} else if (cause == null) {
			messages.add(new CodedMessage(getDefaultMessageCode(), CodedMessageLevel.ERROR));
		} else {
			messages.add(new CodedMessage(getDefaultMessageCode(), CodedMessageLevel.ERROR, cause.getMessage()));
		}
	}

	/**
	 * 使用指定消息编码和原因异常构造异常。
	 * <p>
	 * 注意与 <code>cause</code> 关联的详细信息<i>不会</i>自动出现在本异常的详细信息中。
	 * 
	 * @param cause
	 *            原因异常（使用 {@link #getCause()} 方法获取）。可以使用 <tt>null</tt> 值，指原因异常不存在或未知。
	 * @param rootMessageCode
	 *            消息编码。
	 */
	public SystemRuntimeException(final Throwable cause, final String rootMessageCode) {
		super(cause);
		messages.add(new CodedMessage(rootMessageCode, CodedMessageLevel.ERROR));
		if (cause instanceof CodedException) {
			CodedException se = (CodedException) cause;
			messages.addAll(se.getMessageList());
		} else if (cause != null) {
			messages.add(new CodedMessage(rootMessageCode, CodedMessageLevel.ERROR, cause.getMessage()));
		}
	}

	/**
	 * 使用指定消息和原因异常构造异常。
	 * <p>
	 * 注意与 <code>cause</code> 关联的详细信息<i>不会</i>自动出现在本异常的详细信息中。
	 * 
	 * @param cause
	 *            原因异常（使用 {@link #getCause()} 方法获取）。可以使用 <tt>null</tt> 值，指原因异常不存在或未知。
	 * @param rootMessage
	 *            消息。
	 */
	public SystemRuntimeException(final Throwable cause, final CodedMessage rootMessage) {
		super(cause);
		messages.add(rootMessage);
		if (cause instanceof CodedException) {
			CodedException se = (CodedException) cause;
			messages.addAll(se.getMessageList());
		} else if (cause != null) {
			messages.add(new CodedMessage(getDefaultMessageCode(), CodedMessageLevel.ERROR, cause.getMessage()));
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
