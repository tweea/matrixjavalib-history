/*
 * $Id$
 * Copyright(C) 2008 Matrix
 * All right reserved.
 */
package net.matrix.servlet.servlet;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import net.matrix.web.html.HTMLUtil;

/**
 * 发送文件的 Servlet
 * @author Tweea
 * @since 2005.11.10
 */
public class FileDownload
	extends HttpServlet
{
	private static final long serialVersionUID = 6088934368070094651L;

	private final static Log LOG = LogFactory.getLog(FileDownload.class);

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		handleRequest(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
		throws ServletException, IOException
	{
		handleRequest(request, response);
	}

	private void handleRequest(HttpServletRequest request, HttpServletResponse response)
	{
		boolean hardEncode = "true".equals(request.getParameter("hardEncode"));
		String filepath = hardEncode ? HTMLUtil.decodeFromHexString(request.getParameter("filepath")) : request.getParameter("filepath");

		File file = new File(request.getSession().getServletContext().getRealPath("/"), filepath);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try{
			long p = 0;
			long l = 0;
			response.reset();
			// 如果是第一次下,还没有断点续传,状态是默认的 200,无需显式设置
			// 响应的格式是:
			// HTTP/1.1 200 OK
			if(request.getHeader("Range") != null){ // 客户端请求的下载的文件块的开始字节
				// 如果是下载文件的范围而不是全部,向客户端声明支持并开始文件块下载要设置状态
				// 响应的格式是:
				// HTTP/1.1 206 Partial Content
				response.setStatus(javax.servlet.http.HttpServletResponse.SC_PARTIAL_CONTENT);
				// 从请求中得到开始的字节
				// 请求的格式是:
				// Range: bytes=[文件块的开始字节]-
				p = Long.parseLong(request.getHeader("Range").replaceAll("bytes=", "").replaceAll("-", ""));
			}
			// 告诉客户端允许断点续传多线程连接下载
			// 响应的格式是:
			// Accept-Ranges: bytes
			response.setHeader("Accept-Ranges", "bytes");
			// 奇怪，加了他就出错？？？
			// 下载的文件(或块)长度
			// 响应的格式是:
			// Content-Length: [文件的总大小] - [客户端请求的下载的文件块的开始字节]
			// response.setContentLength((int)(l - p));
			if(p != 0){
				// 不是从最开始下载,
				// 响应的格式是:
				// Content-Range: bytes [文件块的开始字节]-[文件的总大小 - 1]/[文件的总大小]
				response.setHeader("Content-Range", "bytes " + Long.toString(p) + "-" + Long.toString(l - 1) + "/" + Long.toString(l));
			}
			// response.setHeader("Connection", "Close"); //如果有此句话不能用 IE 直接下载
			// 使客户端直接下载
			// 响应的格式是:
			// Content-Type: application/octet-stream
			response.setContentType("application/octet-stream");
			// 为客户端下载指定默认的下载文件名称
			// 响应的格式是:
			// Content-Disposition: attachment;filename="[文件名]"
			response.setHeader("Content-Disposition", "attachment;filename=\"" + HTMLUtil.encodeFormURL(file.getName()) + "\"");
			int ch;
			bos = new BufferedOutputStream(response.getOutputStream());
			bis = new BufferedInputStream(new FileInputStream(file));
			bis.skip(p);
			while((ch = bis.read()) != -1){
				bos.write(ch);
			}
		}catch(IOException ex){
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
			LOG.error("IO 错误", ex);
		}finally{
			try{
				if(bis != null){
					bis.close();
				}
				if(bos != null){
					bos.flush();
					bos.close();
				}
			}catch(IOException ex){
				LOG.error("IO 错误", ex);
			}
		}
	}
}
