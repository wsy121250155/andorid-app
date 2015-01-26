package com.post.dataDeal;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/*
 * ʹ��JPEG��ʵ�֡�
 * 
 * */
public class CompressImg {
	private int outputWidth; // Ĭ�����ͼƬ��
	private int outputHeight; // Ĭ�����ͼƬ��
	private boolean proportion; // �Ƿ�ȱ����ű��(Ĭ��Ϊ�ȱ����ţ����ճ�����)

	public CompressImg() {
		// TODO Auto-generated constructor stub
		outputWidth = 128;
		outputHeight = 128;
		proportion = true;
	}

	// ͼƬ����
	public boolean compressPic(String inputDir, String inputFileName,
			String outputDir, String outputFileName) {
		try {
			// ���Դ�ļ�
			File file = new File(inputDir + "\\" + inputFileName);
			if (!file.exists()) {
				try {
					throw new Exception();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return false;
			}
			Image img = ImageIO.read(file);
			// �ж�ͼƬ��ʽ�Ƿ���ȷ
			if (img.getWidth(null) == -1) {
				System.out.println(" can't read,retry!" + "<BR>");
				return false;
			} else {
				int newWidth;
				int newHeight;
				// �ж��Ƿ��ǵȱ�����
				if (proportion == true) {
					// Ϊ�ȱ����ż��������ͼƬ��ȼ��߶�
					double rate1 = ((double) img.getWidth(null))
							/ (double) outputWidth + 0.1;
					double rate2 = ((double) img.getHeight(null))
							/ (double) outputHeight + 0.1;
					// �������ű��ʴ�Ľ������ſ���
					double rate = rate1 > rate2 ? rate1 : rate2;
					newWidth = (int) (((double) img.getWidth(null)) / rate);
					newHeight = (int) (((double) img.getHeight(null)) / rate);
				} else {
					newWidth = outputWidth; // �����ͼƬ���
					newHeight = outputHeight; // �����ͼƬ�߶�
				}
				BufferedImage tag = new BufferedImage((int) newWidth,
						(int) newHeight, BufferedImage.TYPE_INT_RGB);

				/*
				 * Image.SCALE_SMOOTH �������㷨 ��������ͼƬ��ƽ���ȵ� ���ȼ����ٶȸ� ���ɵ�ͼƬ�����ȽϺ� ���ٶ���
				 */
				tag.getGraphics().drawImage(
						img.getScaledInstance(newWidth, newHeight,
								Image.SCALE_SMOOTH), 0, 0, null);
				FileOutputStream out = new FileOutputStream(outputDir + "\\"
						+ outputFileName);
				// JPEGImageEncoder������������ͼƬ���͵�ת��
				JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
				encoder.encode(tag);
				out.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return true;
	}
}
