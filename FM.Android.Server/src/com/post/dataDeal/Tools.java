package com.post.dataDeal;

public class Tools {
	public static String newName(String oldName, String smallIcon) {
		// ����ʹ��lastIndexOf����Ϊ�е��ļ����м�Ҳ�е��
		String filePrex = oldName.substring(0, oldName.lastIndexOf("."));
		String newName = filePrex + smallIcon
				+ oldName.substring(filePrex.length());
		return newName;
	}
}
