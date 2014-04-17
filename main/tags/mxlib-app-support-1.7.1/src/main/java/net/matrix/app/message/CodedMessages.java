/*
 * $Id$
 * Copyright(C) 2009 matrix
 * All right reserved.
 */
package net.matrix.app.message;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * 编码消息实用工具。
 */
public final class CodedMessages {
	private static final DocumentBuilderFactory DOM_FACTORY = DocumentBuilderFactory.newInstance();

	private static final TransformerFactory TRANSFORMER_FACTORY = TransformerFactory.newInstance();

	/**
	 * 阻止实例化。
	 */
	private CodedMessages() {
	}

	/**
	 * 新建跟踪级别的消息。
	 * 
	 * @param code
	 *            编码
	 * @param arguments
	 *            参数列表
	 * @return 新建的消息
	 */
	public static CodedMessage trace(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.TRACE, arguments);
	}

	/**
	 * 新建调试级别的消息。
	 * 
	 * @param code
	 *            编码
	 * @param arguments
	 *            参数列表
	 * @return 新建的消息
	 */
	public static CodedMessage debug(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.DEBUG, arguments);
	}

	/**
	 * 新建消息级别的消息。
	 * 
	 * @param code
	 *            编码
	 * @param arguments
	 *            参数列表
	 * @return 新建的消息
	 */
	public static CodedMessage information(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.INFORMATION, arguments);
	}

	/**
	 * 新建警告级别的消息。
	 * 
	 * @param code
	 *            编码
	 * @param arguments
	 *            参数列表
	 * @return 新建的消息
	 */
	public static CodedMessage warning(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.WARNING, arguments);
	}

	/**
	 * 新建错误级别的消息。
	 * 
	 * @param code
	 *            编码
	 * @param arguments
	 *            参数列表
	 * @return 新建的消息
	 */
	public static CodedMessage error(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.ERROR, arguments);
	}

	/**
	 * 新建致命错误级别的消息。
	 * 
	 * @param code
	 *            编码
	 * @param arguments
	 *            参数列表
	 * @return 新建的消息
	 */
	public static CodedMessage fatal(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.FATAL, arguments);
	}

	/**
	 * 从文件加载。
	 * 
	 * @param reader
	 *            输入流
	 * @return 消息记录组
	 * @throws CodedMessageException
	 *             操作异常
	 */
	public static List<CodedMessage> load(final InputStream reader)
		throws CodedMessageException {
		Document document;
		try {
			Transformer transformer = TRANSFORMER_FACTORY.newTransformer();
			DOMResult result = new DOMResult();
			transformer.transform(new StreamSource(reader), result);
			document = (Document) result.getNode();
		} catch (TransformerException e) {
			throw new CodedMessageException(e, "CodedMessage.LoadXMLError");
		}
		return load0(document.getDocumentElement());
	}

	/**
	 * 从文件加载。
	 * 
	 * @param reader
	 *            输入流
	 * @return 消息记录组
	 * @throws CodedMessageException
	 *             操作异常
	 */
	public static List<CodedMessage> load(final Reader reader)
		throws CodedMessageException {
		Document document;
		try {
			Transformer transformer = TRANSFORMER_FACTORY.newTransformer();
			DOMResult result = new DOMResult();
			transformer.transform(new StreamSource(reader), result);
			document = (Document) result.getNode();
		} catch (TransformerException e) {
			throw new CodedMessageException(e, "CodedMessage.LoadXMLError");
		}
		return load0(document.getDocumentElement());
	}

	private static List<CodedMessage> load0(final Node node) {
		List<CodedMessage> messageList = new ArrayList<CodedMessage>();
		NodeList messageNodeList = node.getChildNodes();
		for (int i = 0; i < messageNodeList.getLength(); i++) {
			Node messageNode = messageNodeList.item(i);
			if (messageNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			if ("message".equals(messageNode.getNodeName())) {
				messageList.add(load1(messageNode));
			}
		}
		return messageList;
	}

	private static CodedMessage load1(final Node node) {
		NamedNodeMap messageNodeAttributes = node.getAttributes();
		String code = getCodeNode(messageNodeAttributes).getNodeValue();
		long time = Long.parseLong(messageNodeAttributes.getNamedItem("time").getNodeValue());
		CodedMessageLevel level = CodedMessageLevel.forCode(Integer.valueOf(messageNodeAttributes.getNamedItem("level").getNodeValue()));
		CodedMessage message = new CodedMessage(code, time, level);
		NodeList childNodeList = node.getChildNodes();
		for (int j = 0; j < childNodeList.getLength(); j++) {
			Node childNode = childNodeList.item(j);
			if (childNode.getNodeType() != Node.ELEMENT_NODE) {
				continue;
			}
			if ("argument".equals(childNode.getNodeName())) {
				message.addArgument(childNode.getTextContent());
			} else if ("unformattedArgument".equals(childNode.getNodeName())) {
				message.addUnformattedArgument(childNode.getTextContent());
			} else if ("message".equals(childNode.getNodeName())) {
				message.getMessages().add(load1(childNode));
			}
		}
		return message;
	}

	private static Node getCodeNode(final NamedNodeMap messageNodeAttributes) {
		Node codeNode = messageNodeAttributes.getNamedItem("logId");
		if (codeNode == null) {
			codeNode = messageNodeAttributes.getNamedItem("code");
		}
		return codeNode;
	}

	/**
	 * 保存到文件。
	 * 
	 * @param messageList
	 *            消息记录组
	 * @param writer
	 *            输出流
	 * @throws CodedMessageException
	 *             操作异常
	 */
	public static void save(final List<CodedMessage> messageList, final OutputStream writer)
		throws CodedMessageException {
		try {
			Document document = save0(messageList);
			Transformer transformer = TRANSFORMER_FACTORY.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.transform(new DOMSource(document), new StreamResult(writer));
		} catch (TransformerException e) {
			throw new CodedMessageException(e, "CodedMessage.SaveXMLError");
		}
	}

	/**
	 * 保存到文件。
	 * 
	 * @param messageList
	 *            消息记录组
	 * @param writer
	 *            输出流
	 * @throws CodedMessageException
	 *             操作异常
	 */
	public static void save(final List<CodedMessage> messageList, final Writer writer)
		throws CodedMessageException {
		try {
			Document document = save0(messageList);
			Transformer transformer = TRANSFORMER_FACTORY.newTransformer();
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.transform(new DOMSource(document), new StreamResult(writer));
		} catch (TransformerException e) {
			throw new CodedMessageException(e, "CodedMessage.SaveXMLError");
		}
	}

	private static Document save0(final List<CodedMessage> messageList)
		throws CodedMessageException {
		try {
			DocumentBuilder builder = DOM_FACTORY.newDocumentBuilder();
			Document document = builder.newDocument();
			Element messagesElement = document.createElement("messages");
			document.appendChild(messagesElement);
			for (CodedMessage message : messageList) {
				messagesElement.appendChild(save1(message, document));
			}
			return document;
		} catch (ParserConfigurationException e) {
			throw new CodedMessageException(e, "CodedMessage.SaveXMLError");
		}
	}

	private static Element save1(final CodedMessage message, final Document document) {
		Element messageElement = document.createElement("message");
		messageElement.setAttribute("time", Long.toString(message.getTime()));
		messageElement.setAttribute("code", message.getCode());
		messageElement.setAttribute("level", Integer.toString(message.getLevel().getCode()));
		for (String argument : message.getArguments()) {
			Element argumentElement = document.createElement("argument");
			messageElement.appendChild(argumentElement);
			argumentElement.setTextContent(argument);
		}
		for (String unformattedArgument : message.getUnformattedArguments()) {
			Element unformattedArgumentElement = document.createElement("unformattedArgument");
			messageElement.appendChild(unformattedArgumentElement);
			unformattedArgumentElement.setTextContent(unformattedArgument);
		}
		for (CodedMessage childMessage : message.getMessages()) {
			messageElement.appendChild(save1(childMessage, document));
		}
		return messageElement;
	}
}
