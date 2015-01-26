package com.learnPro.activity;

import java.io.IOException;
import java.io.InputStream;

import com.example.learnpro.R;
import com.learnPro.data.ImageCompress;

import android.support.v7.app.ActionBarActivity;
import android.text.TextPaint;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ImgActivity extends ActionBarActivity {
	private String[] images = null;
	private AssetManager assets = null;
	private int currentImg = -1;
	private ImageView image;
	private TextView textView1;
	private TextView textView2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_img);
		textView1=(TextView)findViewById(R.id.textView1);
		Typeface face = Typeface.createFromAsset (getAssets() , "fonts/AvenirNextLTPro-UltLt.otf");
		textView1.setTypeface (face);
		TextPaint tp = textView1.getPaint();
		tp.setFakeBoldText(true); 
		
		textView2=(TextView)findViewById(R.id.textView2);
		textView2.setTypeface (face);
		
		image = (ImageView) findViewById(R.id.image);
		assets = getAssets();
		try {
			images = assets.list("");
			// images[0]是什么guobi不懂是什么东西，难怪要下面关于文件类型的判断
			// ，而且好像还是隐藏文件
			// Log.i("ImgActivity", images[0] + images[1] + images[2]);
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
					Log.i("wsy", "********************");
					bitmapDrawable.getBitmap().recycle();
				}
				// image.setImageBitmap(BitmapFactory.decodeStream(assetFile));
				image.setImageBitmap(ImageCompress.getSmallBitmap(assetFile));
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
