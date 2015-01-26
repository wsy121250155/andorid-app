package com.post.dataDeal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class MyImgZip {
	/**
	 * 压缩
	 * 
	 * @param zipFileName
	 *            压缩产生的zip包文件名--带路�?如果为null或空则默认按文件名生产压缩文件名
	 * @param inputFileName
	 *            �?��压缩的文件的名称
	 * @param is
	 *            对应inputFileName文件的输入流
	 * @author siyiwang
	 * @date 2015-1-19
	 */
	public static void zip(String zipFileName, String inputFileName,
			InputStream is) {
		// *********对输出文件名�?��处理*********
		String fileName = zipFileName;
		if (!fileName.endsWith(".zip")) {
			fileName = fileName + ".zip";
		}
		// *********压缩文件的输出流*********
		ZipOutputStream zos = null;
		try {
			zos = new ZipOutputStream(new FileOutputStream(fileName));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			ZipEntry entry = new ZipEntry(inputFileName);
			zos.putNextEntry(entry);
			try {
				int BUFFERSIZE = 2 << 10;
				int length = 0;
				byte[] buffer = new byte[BUFFERSIZE];
				while ((length = is.read(buffer, 0, BUFFERSIZE)) >= 0) {
					zos.write(buffer, 0, length);
				}
				zos.flush();
				zos.closeEntry();
			} catch (IOException ex) {
				throw ex;
			} finally {
				if (null != is) {
					is.close();
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (null != zos) {
				try {
					zos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 压缩
	 * 
	 * @param zipFileName
	 *            压缩产生的zip包文件名--带路�?如果为null或空则默认按文件名生产压缩文件名
	 * @param file
	 *            �?��压缩的文�?
	 * @author siyiwang
	 * @date 2015-1-19
	 */
	public static void zip(String zipFileName, File file) {
		// *********对输出文件名�?��处理*********
		String fileName = zipFileName;
		if (!fileName.endsWith(".zip")) {
			fileName = fileName + ".zip";
		}
		// *********�?��要压缩的文件是否存在、不是目录�?可读*********
		if (!file.exists() || !file.canRead() || !file.isFile()) {
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}
		InputStream is = null;
		try {
			is = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		zip(fileName, file.getName(), is);
	}
}
