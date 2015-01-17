package com.post.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.post.listener.FilsInitListener;

/**
 * Servlet implementation class ImagServlet
 */
@WebServlet("/ImagServlet")
public class ImagServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ImagServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int i = Integer.valueOf(request.getParameter("no"));
		if (i >= FilsInitListener.getCount()) {
			PrintWriter out = response.getWriter();
			out.println("fail");
		}

		File img = FilsInitListener.getFiles(i);
		if (img.exists()) {
			// System.out.println(path);
			@SuppressWarnings("resource")
			FileInputStream fis = new FileInputStream(img);
			ServletOutputStream out = response.getOutputStream();
			byte buf[] = new byte[512];
			@SuppressWarnings("unused")
			int n = 0; // ��¼��ʵ�ʶ�ȡ�����ֽ���
			while ((n = fis.read(buf)) != -1) {
				out.write(buf);
			}
			// System.out.println("success img: " + i);
		} else {
			PrintWriter out = response.getWriter();
			out.println("fail");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		out.print(FilsInitListener.getCount());
	}

}
