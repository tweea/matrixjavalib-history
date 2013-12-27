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

	public static CodedMessage trace(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.TRACE, arguments);
	}

	public static CodedMessage debug(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.DEBUG, arguments);
	}

	public static CodedMessage information(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.INFORMATION, arguments);
	}

	public static CodedMessage warning(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.WARNING, arguments);
	}

	public static CodedMessage error(final String code, final String... arguments) {
		return new CodedMessage(code, CodedMessageLevel.ERROR, arguments);
	}

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
	public static CodedMessageList load(InputStream reader)
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
		return load0(document);
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
	public static CodedMessageList load(Reader reader)
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
		return load0(document);
	}

	private static CodedMessageList load0(Document document) {
		CodedMessageList messageList = new CodedMessageList();
		NodeList messageNodeList = document.getElementsByTagName("message");
		for (int i = 0; i < messageNodeList.getLength(); i++) {
			Node messageNode = messageNodeList.item(i);
			NamedNodeMap messageNodeAttributes = messageNode.getAttributes();
			String code = getCodeNode(messageNodeAttributes).getNodeValue();
			long time = Long.parseLong(messageNodeAttributes.getNamedItem("time").getNodeValue());
			CodedMessageLevel level = CodedMessageLevel.forCode(Integer.valueOf(messageNodeAttributes.getNamedItem("level").getNodeValue()));
			CodedMessage message = new CodedMessage(code, time, level);
			NodeList argumentNodeList = messageNode.getChildNodes();
			for (int j = 0; j < argumentNodeList.getLength(); j++) {
				Node argumentNode = argumentNodeList.item(j);
				if (argumentNode.getNodeType() == Node.ELEMENT_NODE && "argument".equals(argumentNode.getNodeName())) {
					message.addArgument(argumentNode.getTextContent());
				}
			}
			messageList.add(message);
		}
		return messageList;
	}

	private static Node getCodeNode(NamedNodeMap messageNodeAttributes) {
		Node codeNode = messageNodeAttributes.getNamedItem("logId");
		if (codeNode == null) {
			codeNode = messageNodeAttributes.getNamedItem("code");
		}
		return codeNode;
	}

	/**
	 * 保存到文件。
	 * 
	 * @param writer
	 *            输出流
	 * @throws CodedMessageException
	 *             操作异常
	 */
	public static void save(CodedMessageList messageList, OutputStream writer)
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
	 * @param writer
	 *            输出流
	 * @throws CodedMessageException
	 *             操作异常
	 */
	public static void save(CodedMessageList messageList, Writer writer)
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

	private static Document save0(CodedMessageList messageList)
		throws CodedMessageException {
		try {
			DocumentBuilder builder = DOM_FACTORY.newDocumentBuilder();
			Document document = builder.newDocument();
			Element messagesElement = document.createElement("messages");
			document.appendChild(messagesElement);
			for (CodedMessage message : messageList) {
				Element messageElement = document.createElement("message");
				messagesElement.appendChild(messageElement);
				messageElement.setAttribute("time", Long.toString(message.getTime()));
				messageElement.setAttribute("code", message.getCode());
				messageElement.setAttribute("level", Integer.toString(message.getLevel().getCode()));
				for (int index = 0; index < message.getArguments().size(); index++) {
					Element argumentElement = document.createElement("argument");
					messageElement.appendChild(argumentElement);
					argumentElement.setTextContent(message.getArgument(index));
				}
			}
			return document;
		} catch (ParserConfigurationException e) {
			throw new CodedMessageException(e, "CodedMessage.SaveXMLError");
		}
	}
}
