/*
 * $Id$
 * 版权所有 2012 Matrix。
 * 保留所有权利。
 */
package net.matrix.app;

import net.matrix.app.message.CodedMessage;

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
	 * 获取编码消息。
	 * 
	 * @return 编码消息
	 */
	CodedMessage getCodedMessage();
}
