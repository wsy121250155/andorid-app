package com.post.dataDeal;

public class Tools {
	public static String newName(String oldName, String smallIcon) {
		// 这里使用lastIndexOf是因为有的文件名中间也有点号
		String filePrex = oldName.substring(0, oldName.lastIndexOf("."));
		String newName = filePrex + smallIcon
				+ oldName.substring(filePrex.length());
		return newName;
	}
}
