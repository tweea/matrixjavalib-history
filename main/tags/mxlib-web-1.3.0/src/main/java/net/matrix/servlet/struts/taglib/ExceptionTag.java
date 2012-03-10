/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.struts.taglib;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.matrix.servlet.struts.StrutsExceptionHelper;
import net.matrix.util.ObjectUtil;
import net.matrix.web.html.HTMLUtil;

public class ExceptionTag
	extends TagSupport
{
	private static final long serialVersionUID = 2913572499674617629L;

	private static final Log LOG = LogFactory.getLog(ExceptionTag.class);

	private static final String TD_START = "<td style=\"border: solid black 1px;\">";

	private static final String TD_END = "</td>";

	protected String id;

	@Override
	public String getId()
	{
		return id;
	}

	@Override
	public void setId(String id)
	{
		this.id = id;
	}

	@Override
	public int doStartTag()
		throws JspException
	{
		HttpServletRequest request = (HttpServletRequest)pageContext.getRequest();
		JspWriter out = pageContext.getOut();

		try{
			Exception e = StrutsExceptionHelper.getException(request);
			if(e != null){
				out.append("<table");
				if(id != null)
					out.append(" id=\"").append(id).append("\"");
				out.append(" style=\"border: solid black 1px; border-collapse: collapse;\">");
				out.append("<tr>");
				out.append(TD_START).append("class").append(TD_END);
				out.append(TD_START).append(e.getClass().toString()).append(TD_END);
				out.append("</tr>");
				out.append("<tr>");
				out.append(TD_START).append("message").append(TD_END);
				out.append(TD_START).append(HTMLUtil.formatInputHTML(ObjectUtil.isNull(e.getMessage(), ""), Integer.MAX_VALUE)).append(TD_END);
				out.append("</tr>");
				StackTraceElement[] stack = e.getStackTrace();
				for(int i = 0; i < stack.length; i++){
					out.append("<tr>");
					out.append(TD_START).append(Integer.toString(i)).append(TD_END);
					out.append(TD_START).append(stack[i].toString()).append(TD_END);
					out.append("</tr>");
				}
				int j = 0;
				Throwable t = e.getCause();
				while(t != null){
					out.append("<tr>");
					out.append(TD_START).append("class").append(TD_END);
					out.append(TD_START).append(t.getClass().toString()).append(TD_END);
					out.append("</tr>");
					out.append("<tr>");
					out.append(TD_START).append("message").append(TD_END);
					out.append(TD_START).append(HTMLUtil.formatInputHTML(ObjectUtil.isNull(t.getMessage(), ""), Integer.MAX_VALUE)).append(TD_END);
					out.append("</tr>");
					stack = t.getStackTrace();
					for(int i = 0; i < stack.length; i++){
						out.append("<tr>");
						out.append(TD_START).append(Integer.toString(i)).append(TD_END);
						out.append(TD_START).append(stack[i].toString()).append(TD_END);
						out.append("</tr>");
					}
					t = t.getCause();
					if(j++ == 10)
						break;
				}
				out.append("</table>");
			}
		}catch(IOException e){
			LOG.error("", e);
		}
		return SKIP_BODY;
	}

	@Override
	public int doEndTag()
	{
		return EVAL_PAGE;
	}
}
