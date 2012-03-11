/*
 * $Id$
 * Copyright(C) 2009 北航冠新
 * All right reserved.
 */
package net.matrix.app.message;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 */
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
		CodedMessage message = new CodedMessage("System.Error", CodedMessageLevels.INFORMATION);
		messageList.add(message);
		Assert.assertEquals(1, messageList.size());
		Assert.assertEquals(message, messageList.get(0));
	}

	@Test
	public void addLogMessageList() {
		CodedMessageList messageList = new CodedMessageList();
		CodedMessageList messageList2 = new CodedMessageList();
		CodedMessage message = new CodedMessage("System.Error", CodedMessageLevels.INFORMATION);
		messageList.add(message);
		messageList2.add(message);
		messageList2.addAll(messageList);
		Assert.assertEquals(2, messageList2.size());
		Assert.assertEquals(message, messageList2.get(1));
	}

	@Test
	public void save()
		throws IOException, CodedMessageException {
		CodedMessageList messageList = new CodedMessageList();
		CodedMessage message = new CodedMessage("System.Error", CodedMessageLevels.INFORMATION);
		message.addArgument("test1");
		message.addArgument("test2");
		messageList.add(message);
		message = new CodedMessage("100000000", CodedMessageLevels.INFORMATION);
		message.addArgument("test3");
		messageList.add(message);
		StringWriter os = new StringWriter();
		messageList.save(os);
		os.close();
		// 读取
		Reader is = new StringReader(os.toString());
		CodedMessageList messageList2 = CodedMessageList.load(is);
		is.close();
		Assert.assertEquals(messageList.size(), messageList2.size());
		for (int index = 0; index < messageList.size(); index++) {
			CodedMessage message1 = messageList.get(index);
			CodedMessage message2 = messageList2.get(index);
			Assert.assertEquals(message1.getCode(), message2.getCode());
			Assert.assertEquals(message1.getTime(), message2.getTime());
			Assert.assertEquals(message1.getArguments().size(), message2.getArguments().size());
		}
	}
}
