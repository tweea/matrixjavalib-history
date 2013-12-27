/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.message;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class CodedMessagesTest {
	@Test
	public void testSaveAndLoad()
		throws IOException, CodedMessageException {
		List<CodedMessage> messageList = new ArrayList<CodedMessage>();
		messageList.add(CodedMessages.information("System.Error", "test1", "test2"));
		messageList.add(CodedMessages.information("100000000", "test3"));
		messageList.get(1).getMessages().add(CodedMessages.debug("12345", "moo..."));
		StringWriter os = new StringWriter();
		CodedMessages.save(messageList, os);
		os.close();
		// 读取
		Reader is = new StringReader(os.toString());
		List<CodedMessage> messageList2 = CodedMessages.load(is);
		is.close();
		Assert.assertEquals(messageList.size(), messageList2.size());
		for (int index = 0; index < messageList.size(); index++) {
			CodedMessage message1 = messageList.get(index);
			CodedMessage message2 = messageList2.get(index);
			Assert.assertEquals(message1.getCode(), message2.getCode());
			Assert.assertEquals(message1.getTime(), message2.getTime());
			Assert.assertEquals(message1.getArguments(), message2.getArguments());
			Assert.assertEquals(message1.getMessages().size(), message2.getMessages().size());
		}
	}
}
