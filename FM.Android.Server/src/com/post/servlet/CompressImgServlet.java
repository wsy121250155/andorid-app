package com.post.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.post.dataDeal.CompressImg;
import com.post.dataDeal.ShowFile;
import com.post.dataDeal.Tools;
import com.post.listener.FilesInitListener;

/**
 * Servlet implementation class CompressImgServlet
 */
@WebServlet("/CompressImgServlet")
public class CompressImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	// private CompressImg compressImg;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public CompressImgServlet() {
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
		File file = FilesInitListener.getFiles(1);
		CompressImg compressImg = new CompressImg();
		String inputDir = file.getParent();
		// String inputFileName = file.getName();
		String inputFileName = "u=4245198817,693717552&fm=21&gp=0.jpg.gif";
		String outputDir = getServletContext().getRealPath("/") + "\\img";
		String outputFileName = Tools.newName(inputFileName, "compressed");
		boolean isTrans = compressImg.compressPic(inputDir, inputFileName,
				outputDir, outputFileName);
		String imgStr = inputDir + "\\" + inputFileName;
		File newImg = null;
		if (isTrans) {
			imgStr = outputDir + "\\" + outputFileName;
			newImg = new File(imgStr);
		}
		out = response.getOutputStream();
		ShowFile.show(newImg, out);
	}

	private ServletOutputStream out;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
