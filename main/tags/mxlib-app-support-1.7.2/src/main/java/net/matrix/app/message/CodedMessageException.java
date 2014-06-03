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

	/**
	 * 使用默认消息构造异常。原因异常没有初始化，可以随后调用 {@link #initCause} 进行初始化。
	 */
	public CodedMessageException() {
		super();
	}

	/**
	 * 使用指定消息编码构造异常。原因异常没有初始化，可以随后调用 {@link #initCause} 进行初始化。
	 * 
	 * @param messageCode
	 *            消息编码。
	 */
	public CodedMessageException(final String messageCode) {
		super(messageCode);
	}

	/**
	 * 使用指定消息构造异常。原因异常没有初始化，可以随后调用 {@link #initCause} 进行初始化。
	 * 
	 * @param message
	 *            消息。
	 */
	public CodedMessageException(final CodedMessage message) {
		super(message);
	}

	/**
	 * 使用指定原因异常构造异常，详细信息指定为 <tt>(cause==null ? null : cause.toString())</tt> （特别地指定原因异常的类和详细信息）。
	 * 
	 * @param cause
	 *            原因异常（使用 {@link #getCause()} 方法获取）。可以使用 <tt>null</tt> 值，指原因异常不存在或未知。
	 */
	public CodedMessageException(final Throwable cause) {
		super(cause);
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
	public CodedMessageException(final Throwable cause, final String messageCode) {
		super(cause, messageCode);
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
	public CodedMessageException(final Throwable cause, final CodedMessage message) {
		super(cause, message);
	}

	@Override
	public String getDefaultMessageCode() {
		return "CodedMessage.Error";
	}
}
