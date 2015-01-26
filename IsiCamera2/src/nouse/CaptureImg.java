package nouse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.security.auth.callback.Callback;

import com.demo.android.IsiCamera2.R;
import com.demo.android.IsiCamera2.R.id;
import com.demo.android.IsiCamera2.R.layout;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

public class CaptureImg extends Activity {
	SurfaceView sView;
	SurfaceHolder surfaceHolder;
	int screenWidth, screenHeight;
	Camera camera;
	boolean isPreview = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_capture_img);
		WindowManager wm = getWindowManager();
		// display 可以获得屏幕相关的信息
		Display display = wm.getDefaultDisplay();// wsy
		DisplayMetrics metrics = new DisplayMetrics();
		display.getMetrics(metrics);
		screenWidth = metrics.widthPixels;
		screenHeight = metrics.heightPixels;
		sView = (SurfaceView) findViewById(R.id.sView);
		sView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceHolder = sView.getHolder();
		surfaceHolder.addCallback(new SurfaceHolder.Callback() {
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				if (null != camera) {
					if (isPreview)
						camera.stopPreview();
					camera.release();
					camera = null;
				}
			}
		});
	}

	@SuppressLint("NewApi")
	private void initCamera() {
		if (!isPreview) {
			camera = Camera.open(0);
			camera.setDisplayOrientation(90);
		}
		if (camera != null && !isPreview) {
			try {
				Camera.Parameters parameters = camera.getParameters();
				parameters.setPreviewSize(screenWidth, screenHeight);
				parameters.setPreviewFpsRange(4, 10);
				parameters.setPictureFormat(ImageFormat.JPEG);
				parameters.set("jpeg-quality", 85);
				parameters.setPictureSize(screenWidth, screenHeight);
				camera.setPreviewDisplay(surfaceHolder);
				camera.startPreview();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			isPreview = true;
		}
	}

	public void capture(View source) {
		if (camera != null) {
			camera.autoFocus(autoFocusCallback);
		}
	}

	AutoFocusCallback autoFocusCallback = new AutoFocusCallback() {

		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			// TODO Auto-generated method stub
			if (success) {
				camera.takePicture(new ShutterCallback() {

					@Override
					public void onShutter() {
						// TODO Auto-generated method stub

					}
				}, new PictureCallback() {
					public void onPictureTaken(byte[] data, Camera c) {

					}
				}, myJpegCallback);
			}
		}
	};
	PictureCallback myJpegCallback = new PictureCallback() {

		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			// TODO Auto-generated method stub
			final Bitmap bm = BitmapFactory.decodeByteArray(data, 0,
					data.length);
			View saveDialog = getLayoutInflater().inflate(R.layout.save, null);
			ImageView show = (ImageView) findViewById(R.id.show);
			show.setImageBitmap(bm);
			new AlertDialog.Builder(CaptureImg.this).setView(saveDialog)
					.setPositiveButton("save", new OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							File file = new File(Environment
									.getExternalStorageDirectory()
									.getAbsoluteFile(), "photo.jpg");
							FileOutputStream outStream = null;
							try {
								outStream = new FileOutputStream(file);
								bm.compress(CompressFormat.JPEG, 100, outStream);
								outStream.close();
							} catch (FileNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}).show();
			camera.stopPreview();
			camera.startPreview();
			isPreview = true;
		}
	};
}
