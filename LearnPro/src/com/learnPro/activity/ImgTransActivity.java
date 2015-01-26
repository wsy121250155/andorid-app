package com.learnPro.activity;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.example.learnpro.R;
import com.learnPro.data.IPAddress;
//import com.learnPro.data.MyMemoryInfo;

import android.support.v7.app.ActionBarActivity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ImgTransActivity extends ActionBarActivity {
	private ImageView imageView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_trans_img);
		Button button = (Button) findViewById(R.id.button1);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				PullImg pullImg = new PullImg();
				pullImg.execute("");
			}
		});
	}

	private Bitmap bitmap;

	class PullImg extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			Log.i("wsy", "doInBack");
			// MyMemoryInfo.displayBriefMemory(ImgTransActivity.this);
			// TODO Auto-generated method stub
			// 回收之前的图片
			if (bitmap != null && !bitmap.isRecycled()) {
				// 實際上有沒有這句差別不大
				// bitmap.recycle();
			}
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(IPAddress.PATH + "/ImagServlet");
			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);
				HttpEntity httpEntity = httpResponse.getEntity();
				if (httpEntity != null) {
					bitmap = BitmapFactory
							.decodeStream(httpEntity.getContent());
				} else {
					bitmap = null;
				}
			} catch (ClientProtocolException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
			Log.i("wsy", "post");
			// MyMemoryInfo.displayBriefMemory(ImgTransActivity.this);
			if (bitmap != null) {
				imageView1.setImageBitmap(bitmap);
			}
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.trans_img, menu);
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
