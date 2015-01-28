package com.example.learnpro;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;

import com.example.learnpro.R;
import com.learnPro.data.ImageCompress;

import android.app.Activity;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class ImgShowActivity extends Activity {
	private Bitmap[] bitmaps = new Bitmap[4];

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_img_show);
		// *************创建数据文件夹*************
		getDir("wsy", MODE_PRIVATE);

		// ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
		String filePath = "WP_20150119_002.jpg";
		AssetManager assets = getAssets();
		InputStream inputStream = null;
		try {
			inputStream = assets.open(filePath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// 原图
		// final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
		// 第一步：首先压缩按照480*800压缩
		final Bitmap combitmap = ImageCompress.getSmallBitmap(inputStream);
		bitmaps[0] = combitmap;
		new Thread() {
			public void run() {
				File sdCard = Environment.getExternalStorageDirectory();
				try {
					// 第二步：转换成字符串，也有一个压缩的过程(其中质量最好控制在90，不能低于80)
					String bitmapStr = ImageCompress.bitmapToString(combitmap);
					Log.i("bitmapStr size: ", "" + bitmapStr.getBytes().length
							/ 1024 + "kb");
					// 第三步：从字符串转回图片，由于这里不涉及到网络传输，所以质量直接为100
					bitmaps[1] = ImageCompress.convertStringToIcon(bitmapStr,
							sdCard.getCanonicalPath() + "/bitmapStr.jpg");
					saveMyBitmap(sdCard.getCanonicalPath(), "combitmap.jpg",
							combitmap);
					write(sdCard.getCanonicalPath(), "String.txt", bitmapStr);
					saveMyBitmap(sdCard.getCanonicalPath(),
							"string2Bitmap.jpg", bitmaps[1]);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				Log.i("wsy", "deal over");
			}
		}.start();

		ListView listView1 = (ListView) findViewById(R.id.listView1);
		listView1.setAdapter(new BaseAdapter() {

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				// TODO Auto-generated method stub
				ImageView imageView = new ImageView(ImgShowActivity.this);
				imageView.setImageBitmap(bitmaps[position]);
				return imageView;
			}

			@Override
			public long getItemId(int position) {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public Object getItem(int position) {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return 2;
			}
		});
	}

	private void write(String path, String fileName, String content) {
		FileOutputStream fos = null;
		File f = new File(path, fileName);
		if (f.exists()) {
			f.delete();
		}
		try {
			fos = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// try {
		// fos = openFileOutput(fileName, MODE_PRIVATE);
		// } catch (FileNotFoundException e) {
		// // TODO Auto-generated catch block
		// e.printStackTrace();
		// }
		PrintStream ps = new PrintStream(fos);
		ps.println(content);
	}

	public void saveMyBitmap(String path, String fileName, Bitmap bitmap) {
		FileOutputStream fOut = null;

		// 注意路径，两种方式
		// 方式一：
		// File f = new File(getFilesDir(), fileName);
		// getFilesDir()表示存到system中本程序安装目录对应的data目录下
		File f = new File(path, fileName);
		if (f.exists()) {
			f.delete();
		}
		try {
			fOut = new FileOutputStream(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		// 方式二：(此方式仅适用于应用程序的数据文件夹下的文件)
		// try {
		// fOut = openFileOutput(path + "/" + fileName, Context.MODE_PRIVATE);
		// } catch (FileNotFoundException e1) {
		// // TODO Auto-generated catch block
		// e1.printStackTrace();
		// }

		bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
		try {
			fOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			fOut.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
