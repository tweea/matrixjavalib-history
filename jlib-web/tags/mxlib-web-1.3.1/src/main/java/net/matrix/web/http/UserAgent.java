/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.web.http;

import javax.servlet.http.HttpServletRequest;

public class UserAgent
{
	private String userAgent;

	private UserAgentType type;

	public UserAgent(HttpServletRequest request)
	{
		userAgent = request.getHeader("user-agent");
		if(userAgent.toLowerCase().indexOf("opera") != -1){
			type = UserAgentType.OPERA;
		}else if(userAgent.toLowerCase().indexOf("netscape") != -1){
			type = UserAgentType.NETSCAPE;
		}else if(userAgent.indexOf("MSIE") != -1){
			type = UserAgentType.MSIE;
		}else if(userAgent.toLowerCase().indexOf("konqueror") != -1){
			type = UserAgentType.KONQUEROR;
		}else if(userAgent.toLowerCase().indexOf("gecko") != -1){
			type = UserAgentType.GECKO;
		}else{
			type = UserAgentType.UNKNOWN;
		}
	}

	public String getUserAgent()
	{
		return userAgent;
	}

	public UserAgentType getType()
	{
		return type;
	}
}
