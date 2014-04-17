/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.app;

import net.matrix.app.message.CodedMessage;
import net.matrix.app.message.CodedMessages;

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
	private final CodedMessage message;

	/**
	 * 使用默认消息构造异常。原因异常没有初始化，可以随后调用 {@link #initCause} 进行初始化。
	 */
	public SystemRuntimeException() {
		this.message = CodedMessages.error(getDefaultMessageCode());
	}

	/**
	 * 使用指定消息编码构造异常。原因异常没有初始化，可以随后调用 {@link #initCause} 进行初始化。
	 * 
	 * @param messageCode
	 *            消息编码。
	 */
	public SystemRuntimeException(final String messageCode) {
		this.message = CodedMessages.error(messageCode);
	}

	/**
	 * 使用指定消息构造异常。原因异常没有初始化，可以随后调用 {@link #initCause} 进行初始化。
	 * 
	 * @param message
	 *            消息。
	 */
	public SystemRuntimeException(final CodedMessage message) {
		this.message = message;
	}

	/**
	 * 使用指定原因异常构造异常，详细信息指定为 <tt>(cause==null ? null : cause.toString())</tt> （特别地指定原因异常的类和详细信息）。
	 * 
	 * @param cause
	 *            原因异常（使用 {@link #getCause()} 方法获取）。可以使用 <tt>null</tt> 值，指原因异常不存在或未知。
	 */
	public SystemRuntimeException(final Throwable cause) {
		super(cause);
		this.message = CodedMessages.error(getDefaultMessageCode());
		if (cause instanceof CodedException) {
			CodedException ce = (CodedException) cause;
			this.message.getMessages().add(ce.getCodedMessage());
		} else if (cause != null) {
			this.message.addUnformattedArgument(cause.getMessage());
		}
	}

	/**
	 * 使用指定消息编码和原因异常构造异常。
	 * <p>
	 * 注意与 <code>cause</code> 关联的详细信息<i>不会</i>自动出现在本异常的详细信息中。
	 * 
	 * @param cause
	 *            原因异常（使用 {@link #getCause()} 方法获取）。可以使用 <tt>null</tt> 值，指原因异常不存在或未知。
	 * @param messageCode
	 *            消息编码。
	 */
	public SystemRuntimeException(final Throwable cause, final String messageCode) {
		super(cause);
		this.message = CodedMessages.error(messageCode);
		if (cause instanceof CodedException) {
			CodedException ce = (CodedException) cause;
			this.message.getMessages().add(ce.getCodedMessage());
		} else if (cause != null) {
			this.message.addUnformattedArgument(cause.getMessage());
		}
	}

	/**
	 * 使用指定消息和原因异常构造异常。
	 * <p>
	 * 注意与 <code>cause</code> 关联的详细信息<i>不会</i>自动出现在本异常的详细信息中。
	 * 
	 * @param cause
	 *            原因异常（使用 {@link #getCause()} 方法获取）。可以使用 <tt>null</tt> 值，指原因异常不存在或未知。
	 * @param message
	 *            消息。
	 */
	public SystemRuntimeException(final Throwable cause, final CodedMessage message) {
		super(cause);
		this.message = message;
		if (cause instanceof CodedException) {
			CodedException ce = (CodedException) cause;
			this.message.getMessages().add(ce.getCodedMessage());
		} else if (cause != null) {
			this.message.addUnformattedArgument(cause.getMessage());
		}
	}

	@Override
	public String getDefaultMessageCode() {
		return "System.Error";
	}

	@Override
	public final CodedMessage getCodedMessage() {
		return message;
	}

	@Override
	public String getMessage() {
		return message.formatAll();
	}
}
