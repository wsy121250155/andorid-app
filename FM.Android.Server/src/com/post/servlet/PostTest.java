package com.post.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

/**
 * Servlet implementation class PostTest
 */
@WebServlet("/PostTest")
public class PostTest extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PostTest() {
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
		PrintWriter out = response.getWriter();
		out.println("success");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// System.out.println("receive post request");
		// String[] strArray = { "head", "body", "tail" };
		// JSONArray jsonArray = JSONArray.fromObject(strArray);
		// String name = request.getParameter("name");
		// String pw = request.getParameter("pw");
		// System.out.println("name: " + name + "; pw: " + pw);

		PrintWriter out = response.getWriter();

		// String str =
		// "{\"chinese\":\"88\",\"math\":\"78\",\"computer\":\"99\"}";
		// JSONObject jsonObject = JSONObject.fromObject(str);
		// out.println(jsonObject.toString());
		String[] object = { "chinese", "math" };
		JSONArray jsonArray = JSONArray.fromObject(object);
		out.println(jsonArray.toString());
	}
}
