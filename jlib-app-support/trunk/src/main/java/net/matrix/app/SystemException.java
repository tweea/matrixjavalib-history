package net.matrix.app;

import net.matrix.app.message.CodedMessage;
import net.matrix.app.message.CodedMessageLevels;
import net.matrix.app.message.CodedMessageList;

/**
 * 交换平台的根异常
 */
public class SystemException
	extends Exception
{
	private static final long serialVersionUID = -6689502787927232992L;

	private CodedMessageList messages = new CodedMessageList();

	public SystemException()
	{
		super();
		messages.add(createRootMessage());
	}

	public SystemException(Throwable cause)
	{
		super(cause);
		if(cause instanceof SystemException){
			SystemException se = (SystemException)cause;
			messages.addAll(se.messages);
		}else{
			messages.add(createRootMessage());
		}
	}

	public SystemException(CodedMessage rootMessage)
	{
		super();
		messages.add(rootMessage);
	}

	public SystemException(String rootMessageCode)
	{
		this(new CodedMessage(rootMessageCode, CodedMessageLevels.ERROR));
	}

	public SystemException(Throwable cause, CodedMessage rootMessage)
	{
		super(cause);
		messages.add(rootMessage);
		if(cause instanceof SystemException){
			SystemException se = (SystemException)cause;
			messages.addAll(se.messages);
		}
	}

	public SystemException(Throwable cause, String rootMessageCode)
	{
		this(cause, new CodedMessage(rootMessageCode, CodedMessageLevels.ERROR, cause.getMessage()));
	}

	/**
	 * @return 根消息
	 */
	protected CodedMessage createRootMessage()
	{
		CodedMessage message = new CodedMessage("System.Error", CodedMessageLevels.ERROR);
		message.addArgument(super.getMessage());
		return message;
	}

	/**
	 * @return 根消息
	 */
	public final CodedMessage getRootMessage()
	{
		return messages.get(0);
	}

	/**
	 * @return 获取消息
	 */
	public CodedMessageList getMessageList()
	{
		return messages;
	}

	@Override
	public String getMessage()
	{
		return getRootMessage().format();
	}
}