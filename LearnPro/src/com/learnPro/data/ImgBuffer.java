package com.learnPro.data;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ImgBuffer {

	public ImgBuffer(int len) {
		length = len;
		imgCount = pullImgCount();
		positions = new Integer[BLOCKCOUNT][length];
		bitmaps = new Bitmap[BLOCKCOUNT][length];
		for (int i = 0; i < BLOCKCOUNT; i++) {
			for (int j = 0; j < length; j++) {
				positions[i][j] = -1;
				bitmaps[i][j] = null;
			}
		}
	}

	private static final int BLOCKCOUNT = 3;
	private int length;
	// 窗口当前的中间区域：用于检测当前是否需要进行缓存
	// 此处设置为-1，这样在第一次调用的时候就实现缓存
	private int currentBlock = -1;

	// 这两个二维数组中，第一个参数为block，第二个为seat
	// ！！！！！！！！！！！注意，这两个数组中对应的位置要动一起改动！！！！！！！！！！！
	private Integer[][] positions;
	private Bitmap[][] bitmaps;

	public Bitmap getBitmap(int position) {
		// 如果存在freshbuffer线程运行，则等待至线程结束
		while (mutex) {
		}
		int seat = position % length;
		int block = (position / length) % BLOCKCOUNT;
		// 检测缓存中是否有要求的bitmap，如果没有，
		// 先释放前一个的资源(关于能否释放的判断由释放函数完成)，然后再从服务器获取要求的资源
		if (positions[block][seat] != position) {
			FreeBitMap.freeBitMap(bitmaps[block][seat]);
			positions[block][seat] = position;
			bitmaps[block][seat] = pullImg(position);
		}
		// 如果position所在的位置不处于当前窗口的中间区域，开始刷新缓存
		if (block != currentBlock) {
			// 将position所在区域设置为中间区域
			currentBlock = block;
			// 开始刷新所有的块
			new FreshBuffer(position).start();
		}
		return bitmaps[block][seat];
	}

	private boolean mutex = false;

	class FreshBuffer extends Thread {
		private int position;

		public FreshBuffer(int position) {
			this.position = position;
		}

		public void run() {
			// 获取信号量
			mutex = true;

			// position所在的块在全局中所处的序号
			int blockCount = position / length;
			// 窗口的第一个block
			int firstBlock = blockCount;
			if (blockCount > 0) {
				firstBlock--;
			}
			// 窗口的最后一个block
			int lastBlcok = blockCount;
			if (blockCount != imgCount / length) {
				lastBlcok++;
			}

			// 窗口最左边的position,窗口最右边的的后面一个的position
			int firstPosition, lastPosition;
			firstPosition = firstBlock * length;
			lastPosition = (lastBlcok + 1) * length;
			if (lastPosition > imgCount) {
				lastPosition = imgCount;
			}
			int block, seat;
			for (int i = firstPosition; i < lastPosition; i++) {
				block = (i / length) % BLOCKCOUNT;
				seat = i % length;
				if (positions[block][seat] != i) {
					FreeBitMap.freeBitMap(bitmaps[block][seat]);
					positions[block][seat] = i;
					bitmaps[block][seat] = pullImg(i);
				}
			}
			// 释放信号量
			mutex = false;
		}
	}

	private int imgCount;

	public int getImgCount() {
		return imgCount;
	}

	public Bitmap pullImg(int count) {
		HttpGet httpGet = new HttpGet(
				"http://10.0.1.103:8080/FM.Android.Server/ImagServlet?no="
						+ count);
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse;
		Bitmap bitmap = null;
		try {
			httpResponse = httpClient.execute(httpGet);
			HttpEntity httpEntity = httpResponse.getEntity();
			bitmap = BitmapFactory.decodeStream(httpEntity.getContent());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return bitmap;
	}

	private int pullImgCount() {
		int no_img = 0;
		HttpPost httpPost = new HttpPost(
				"http://10.0.1.103:8080/FM.Android.Server/ImagServlet");
		HttpClient httpClient = new DefaultHttpClient();
		HttpResponse httpResponse;
		try {
			httpResponse = httpClient.execute(httpPost);
			HttpEntity httpEntity = httpResponse.getEntity();
			InputStream inputStream = httpEntity.getContent();
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					inputStream));
			no_img = Integer.valueOf(bf.readLine());
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return no_img;
	}
}
