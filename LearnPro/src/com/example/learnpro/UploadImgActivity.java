package com.example.learnpro;

import java.io.IOException;

import com.example.learnpro.R;
import com.learnPro.data.MyUploadImg;

import android.support.v7.app.ActionBarActivity;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class UploadImgActivity extends ActionBarActivity {
	private TextView imgSource;
	private AssetManager assets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_upload_img);
		assets = getAssets();
		imgSource = (TextView) findViewById(R.id.imgSource);
		new UploadImg().execute("");
	}

	class UploadImg extends AsyncTask<String, String, String> {

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String[] images = null;
			try {
				images = assets.list("");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String image;
			int currentImg = 0;
			while (!images[currentImg].endsWith(".png")
					&& !images[currentImg].endsWith(".jpg")
					&& !images[currentImg].endsWith(".gif")) {
				// while (!images[currentImg].endsWith(".zip")) {
				currentImg++;
				if (currentImg >= images.length) {
					break;
				}
			}
			image = images[currentImg];

			MyUploadImg.uploadFile(image, assets);
			return image;
		}

		@Override
		protected void onPostExecute(String result) {
			imgSource.setText(result);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.upload_img, menu);
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
