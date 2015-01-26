package com.learnPro.activity;

import com.example.learnpro.R;
import com.learnPro.data.ImgBuffer;
import com.learnPro.data.MyMemoryInfo;

import android.support.v7.app.ActionBarActivity;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

public class ImgListTransActivity extends ActionBarActivity {
	private ListView listView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_img_list_trans);
		listView1 = (ListView) findViewById(R.id.listView1);
		listView1.setAdapter(new MyAdapter());
	}

	@SuppressLint("UseSparseArrays")
	class MyAdapter extends BaseAdapter {
		// public MyAdapter() {
		// for (int i = 0; i < LENGTH; i++) {
		// positions[i] = -1;
		// }
		// }

		private ImgBuffer imgBuffer = new ImgBuffer(LENGTH);
		// private Integer[] positions = new Integer[LENGTH];
		// private Bitmap[] bitmaps = new Bitmap[LENGTH];
		// 根据初始化的时候getView被调用的次数来决定的（具体对不对不知道）
		private static final int LENGTH = 5;

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ImageView imageView;
			// 判断convertView的状态，来达到复用效果
			if (null == convertView) {
				// 如果convertView为空，则表示第一次显示该条目，需要创建一个view
				imageView = new ImageView(ImgListTransActivity.this);
				// count++;
			} else {
				// 否则表示可以复用convertView
				imageView = (ImageView) convertView;
			}
			// ***************Bitmap 缓冲方案一***************
			// 实际上使用了上面的convertView复用机制之后，imageView回收机制就没有发挥作用
			// FreeBitMap.free(imageView);
			// if (positions[position % LENGTH] != position) {
			// FreeBitMap.freeBitMap(bitmaps[position % LENGTH]);
			// positions[position % LENGTH] = position;
			// bitmaps[position % LENGTH] = imgBuffer.pullImg(position);
			// }
			// imageView.setImageBitmap(bitmaps[position % LENGTH]);
			// ***************Bitmap 缓冲方案二***************
			imageView.setImageBitmap(imgBuffer.getBitmap(position));
			showTimes();
			return imageView;
		}

		private void showTimes() {
			// MyMemoryInfo.displayBriefMemory(ImgListTransActivity.this);
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
			return imgBuffer.getImgCount();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.img_list_trans, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
