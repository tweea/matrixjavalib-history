/*
 * $Id$
 * Copyright(C) 2009 北航冠新
 * All right reserved.
 */
package net.matrix.app.message;

/**
 * 编码消息级别
 */
public interface CodedMessageLevels {
	/**
	 * 跟踪
	 */
	int TRACE = 1;

	/**
	 * 调试
	 */
	int DEBUG = 2;

	/**
	 * 消息
	 */
	int INFORMATION = 3;

	/**
	 * 警告
	 */
	int WARNING = 4;

	/**
	 * 错误
	 */
	int ERROR = 5;

	/**
	 * 致命错误
	 */
	int FATAL = 6;
}
