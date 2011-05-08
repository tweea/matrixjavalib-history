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

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.matrix.servlet.session.WebProcess;

public class MessageTag
	extends TagSupport
{
	private static final long serialVersionUID = 2913572499674617629L;

	private static final Log LOG = LogFactory.getLog(MessageTag.class);

	protected String id = null;

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

		boolean isMessage = true;
		String message = WebProcess.getMessage(request);
		if(StringUtils.isEmpty(message)){
			isMessage = false;
			message = WebProcess.getError(request);
		}
		try{
			if(!StringUtils.isEmpty(message) || id != null){
				out.append("<span ");
				if(id != null)
					out.append("id=\"").append(id).append("\" ");
				if(isMessage)
					out.append("class=\"message\">");
				else
					out.append("class=\"error\">");
				out.append(message);
				out.append("</span>");
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
