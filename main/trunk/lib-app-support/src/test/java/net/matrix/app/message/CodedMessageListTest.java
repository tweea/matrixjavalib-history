/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.message;

import org.junit.Assert;
import org.junit.Test;

public class CodedMessageListTest {
	@Test
	public void logMessageList() {
		CodedMessageList messageList = new CodedMessageList();
		Assert.assertEquals(0, messageList.size());
	}

	@Test
	public void addLogMessage() {
		CodedMessageList messageList = new CodedMessageList();
		Assert.assertEquals(0, messageList.size());
		CodedMessage message = CodedMessages.information("System.Error");
		messageList.add(message);
		Assert.assertEquals(1, messageList.size());
		Assert.assertEquals(message, messageList.get(0));
	}

	@Test
	public void addLogMessageList() {
		CodedMessageList messageList = new CodedMessageList();
		CodedMessageList messageList2 = new CodedMessageList();
		CodedMessage message = CodedMessages.information("System.Error");
		messageList.add(message);
		messageList2.add(message);
		messageList2.addAll(messageList);
		Assert.assertEquals(2, messageList2.size());
		Assert.assertEquals(message, messageList2.get(1));
	}
}
