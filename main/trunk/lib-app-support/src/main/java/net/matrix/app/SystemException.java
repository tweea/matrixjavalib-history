package net.matrix.app;

import net.matrix.app.message.CodedMessage;
import net.matrix.app.message.CodedMessageLevels;
import net.matrix.app.message.CodedMessageList;

/**
 * 应用系统的根异常。
 */
public class SystemException
	extends Exception {
	private static final long serialVersionUID = -6689502787927232992L;

	private CodedMessageList messages = new CodedMessageList();

	public SystemException() {
		messages.add(new CodedMessage(getDefaultMessageCode(), CodedMessageLevels.ERROR));
	}

	public SystemException(final Throwable cause) {
		super(cause);
		if (cause instanceof SystemException) {
			SystemException se = (SystemException) cause;
			messages.addAll(se.messages);
		} else {
			messages.add(new CodedMessage(getDefaultMessageCode(), CodedMessageLevels.ERROR, cause.getMessage()));
		}
	}

	public SystemException(final CodedMessage rootMessage) {
		messages.add(rootMessage);
	}

	public SystemException(final String rootMessageCode) {
		this(new CodedMessage(rootMessageCode, CodedMessageLevels.ERROR));
	}

	public SystemException(final Throwable cause, final CodedMessage rootMessage) {
		super(cause);
		messages.add(rootMessage);
		if (cause instanceof SystemException) {
			SystemException se = (SystemException) cause;
			messages.addAll(se.messages);
		}
	}

	public SystemException(final Throwable cause, final String rootMessageCode) {
		this(cause, new CodedMessage(rootMessageCode, CodedMessageLevels.ERROR, cause.getMessage()));
	}

	/**
	 * @return 默认消息编码
	 */
	public String getDefaultMessageCode() {
		return "System.Error";
	}

	/**
	 * @return 根消息
	 */
	public final CodedMessage getRootMessage() {
		return messages.get(0);
	}

	/**
	 * @return 获取消息
	 */
	public CodedMessageList getMessageList() {
		return messages;
	}

	@Override
	public String getMessage() {
		return getRootMessage().format();
	}
}
