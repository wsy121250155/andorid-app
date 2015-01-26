package com.post.dataDeal;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.ServletOutputStream;

public class ShowFile {
	public static void show(File file, ServletOutputStream out) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
			byte buf[] = new byte[512];
			@SuppressWarnings("unused")
			int n = 0; // 记录是实际读取到的字节数
			while ((n = fis.read(buf)) != -1) {
				out.write(buf);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
