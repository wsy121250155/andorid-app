package com.learnPro.activity;

import java.io.IOException;
import java.io.InputStream;

import com.example.learnpro.R;

import android.support.v7.app.ActionBarActivity;
import android.content.res.AssetManager;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class ImgActivity extends ActionBarActivity {
	private String[] images = null;
	private AssetManager assets = null;
	private int currentImg = -1;
	private ImageView image;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_img);
		image = (ImageView) findViewById(R.id.image);
		assets = getAssets();
		try {
			images = assets.list("");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		final Button next = (Button) findViewById(R.id.button1);
		next.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				currentImg++;
				if (currentImg >= images.length) {
					currentImg = 0;
				}
				while (!images[currentImg].endsWith(".png")
						&& !images[currentImg].endsWith(".jpg")
						&& !images[currentImg].endsWith(".gif")) {
					currentImg++;
					if (currentImg >= images.length) {
						currentImg = 0;
					}
				}
				InputStream assetFile = null;
				try {
					assetFile = assets.open(images[currentImg]);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				BitmapDrawable bitmapDrawable = (BitmapDrawable) image
						.getDrawable();
				if (bitmapDrawable != null
						&& bitmapDrawable.getBitmap().isRecycled()) {
					bitmapDrawable.getBitmap().recycle();
				}
				image.setImageBitmap(BitmapFactory.decodeStream(assetFile));
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.img, menu);
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
