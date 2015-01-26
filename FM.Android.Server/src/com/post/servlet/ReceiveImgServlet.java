package com.post.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;

import com.post.dataDeal.CompressImg;
import com.post.dataDeal.MyImgZip;
//import com.post.dataDeal.ShowFile;
import com.post.dataDeal.Tools;

/**
 * Servlet implementation class ReceiveImgServlet
 */
@SuppressWarnings("deprecation")
@WebServlet("/ReceiveImgServlet")
public class ReceiveImgServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ReceiveImgServlet() {
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
		String inputFileName = "WP_20141122_001.jpg";
		String inputDir = getServletContext().getRealPath("/") + "\\picture";
		String path = inputDir + "\\" + inputFileName;
		String outputDir = getServletContext().getRealPath("/") + "\\img";
		String outputFileName = Tools.newName(inputFileName, "compressed");
		File file = new File(path);

		// PrintWriter printWriter = response.getWriter();
		// printWriter.println(file.length() / 1024);
		// ShowFile.show(file, response.getOutputStream());
		new CompressImg().compressPic(inputDir, inputFileName, outputDir,
				outputFileName);
		String newPath = outputDir + "\\" + outputFileName;
		File file2 = new File(newPath);
		PrintWriter printWriter = response.getWriter();
		MyImgZip.zip(outputDir + "\\bigImg.zip", file);
		printWriter.println(file2.length() / 1024);
		printWriter.println(newPath);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("ReceiveImgServlet: receive file upload request");
		String outputdir = getServletContext().getRealPath("/") + "\\img";
		DiskFileUpload diskFileUpload = new DiskFileUpload();
		// 允许文件最大长度
		diskFileUpload.setSizeMax(100 * 1024 * 1024);
		// 设置内存缓冲大小
		diskFileUpload.setSizeThreshold(4096);
		// 设置临时目录:接收上传文件时会产生一个临时文件
		diskFileUpload.setRepositoryPath(outputdir + "\\tmp");
		@SuppressWarnings("rawtypes")
		List fileItems = null;
		try {
			fileItems = diskFileUpload.parseRequest(request);
		} catch (FileUploadException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		@SuppressWarnings("rawtypes")
		Iterator iter = fileItems.iterator();
		for (; iter.hasNext();) {
			FileItem fileItem = (FileItem) iter.next();
			if (fileItem.isFormField()) {
				// 当前是一个表单项
				System.out.println("form field : " + fileItem.getFieldName()
						+ ", " + fileItem.getString());
			} else {
				// 当前是一个上传的文件
				String fileName = fileItem.getName();
				try {
					fileItem.write(new File(outputdir + "\\" + fileName));
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		File file = new File(outputdir + "\\tmp");
		File[] files = file.listFiles();
		for (int i = 0; i < files.length; i++) {
			files[i].delete();
		}
	}
}
