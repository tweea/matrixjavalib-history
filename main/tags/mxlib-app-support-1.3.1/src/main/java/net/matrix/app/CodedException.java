/*
 * $Id$
 * 版权所有 2012 Matrix。
 * 保留所有权利。
 */
package net.matrix.app;

import net.matrix.app.message.CodedMessage;
import net.matrix.app.message.CodedMessageList;

/**
 * 包含编码消息的异常。
 */
public interface CodedException {
	/**
	 * 获取异常的默认消息编码。
	 * 
	 * @return 默认消息编码
	 */
	String getDefaultMessageCode();

	/**
	 * 获取异常的根消息，每个异常都必须包含一个根消息。
	 * 
	 * @return 根消息
	 */
	CodedMessage getRootMessage();

	/**
	 * 获取异常的所有消息，包括根消息。
	 * 
	 * @return 所有消息
	 */
	CodedMessageList getMessageList();
}
