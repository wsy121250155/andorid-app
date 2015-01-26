/* 取自《Android开发应用实战详解》p438
 * 进度：存储照片时抛出exception，暂时删掉照片存储代码
 * */
package com.demo.android.IsiCamera2;

import java.io.IOException;
import java.util.List;

import method2.AdjustPhoto;
import method2.DrawCaptureRect;
import method2.RotateBitmap;
import method2.SaveBitmap;
import method2.UsefulImgSize;

import com.demo.android.IsiCamera2.R;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

public class IsiCamera2Activity extends Activity {
	private ViewPicSize picSize;
	private ImageView imageView1;
	private final int RESULT = 1;
	private String TAG = "IsiCamera2Activity";// ??
	private Camera camera = null;
	private SurfaceHolder mSurfaceHolder;
	private SurfaceView mSurfaceView01;
	private Button button1;
	private Button button2;
	private boolean bPreviewing = false;
	private int intScreenWidth;
	private int intScreenHeight;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/* 使应用程序全屏幕运行，不使用title bar */
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.main);
		picSize = ViewPicSize.getInstance();
		/* 取得屏幕解析像素 */
		getDisplayMetrics();
		findViews();
		getSurfaceHolder();
		picSize.initViewParam(intScreenWidth, intScreenHeight);
		drawRect();
		addListener();
		hideBitmap();
	}

	@Override
	protected void onPause() {
		// 被交换到后台时执行
		// camera.release();
		// camera = null;
		bPreviewing = false;
		super.onPause();
	}

	@Override
	public void onStop() {
		resetCamera();
		super.onStop();
	}

	@Override
	protected void onResume() {
		try {
			initCamera();
		} catch (IOException e) {
			Log.e(TAG, "initCamera() in Resume() erorr!");
		}
		super.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == RESULT && resultCode == RESULT_OK && data != null) {

			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaStore.Images.Media.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();
			showPhoto(picturePath);
		}
	}

	// **********************camera**********************

	/*
	 * function: 非preview时：实例化Camera,开始preview 非preview时and相机打开时：再设置一次preview
	 * preview时：不动作
	 */
	@SuppressWarnings("deprecation")
	private void initCamera() throws IOException {
		if (!bPreviewing) {
			/* 若相机非在预览模式，则打开相机 */
			camera = Camera.open();
			camera.setDisplayOrientation(90);
		}
		// 非预览时and相机打开时，开启preview
		if (camera != null && !bPreviewing) {
			Log.i(TAG, "inside the camera");
			/* 创建Camera.Parameters对象 */
			Camera.Parameters parameters = camera.getParameters();
			/* 设置相片格式为JPEG */
			parameters.setPictureFormat(PixelFormat.JPEG);
			/* 指定preview的屏幕大小 */
			// parameters.setPreviewSize(intScreenWidth, intScreenHeight);
			/* 设置图片分辨率大小 */
			List<Size> sizes = parameters.getSupportedPictureSizes();
			Size optimalSize = UsefulImgSize.getOptimalSize(sizes, 500, 500);
			parameters.setPictureSize(optimalSize.width, optimalSize.height);
			/* 将Camera.Parameters设置予Camera */
			camera.setParameters(parameters);
			/* setPreviewDisplay唯一的参数为SurfaceHolder */
			camera.setPreviewDisplay(mSurfaceHolder);
			/* 立即运行Preview */
			camera.startPreview();
			bPreviewing = true;
		}
	}

	/* func:停止preview,释放Camera对象 */
	private void resetCamera() {
		if (camera != null && bPreviewing) {
			camera.stopPreview();
			/* 释放Camera对象 */
			camera.release();
			camera = null;
			bPreviewing = false;
		}
	}

	private void takeAPicture() {
		if (camera != null && bPreviewing) {
			/* 调用takePicture()方法拍照 */
			camera.takePicture(null, null, jpegCallback);
			// camera.takePicture(shutterCallback, rawCallback, jpegCallback);//
			// 调用PictureCallback
		}
	}

	/* func:获取屏幕分辨率 */
	private void getDisplayMetrics() {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		intScreenWidth = dm.widthPixels;
		intScreenHeight = dm.heightPixels;
	}

//	AutoFocusCallback autoFocusCallback = new AutoFocusCallback() {
//
//		@Override
//		public void onAutoFocus(boolean success, Camera camera) {
//			// TODO Auto-generated method stub
//			if (success) {
//				camera.takePicture(null, null, jpegCallback);
//			}
//		}
//	};

	private PictureCallback jpegCallback = new PictureCallback() {
		public void onPictureTaken(byte[] _data, Camera _camera) {
			Bitmap bitmap = BitmapFactory.decodeByteArray(_data, 0,
					_data.length);
			Bitmap rotaBitmap = RotateBitmap.adjustPhotoRotation(bitmap, 90);
			Log.i("pic rate", "" + (double) rotaBitmap.getWidth()
					/ (double) rotaBitmap.getHeight());
			Log.i("pic width", "" + rotaBitmap.getWidth());
			Log.i("pic height", "" + rotaBitmap.getHeight());
			bitmap.recycle();
			picSize.initPicParam(rotaBitmap.getWidth(), rotaBitmap.getHeight());
			Bitmap resultBitmap = cutScale(rotaBitmap);
			rotaBitmap.recycle();
			showBitmap(resultBitmap);
			SaveBitmap.save(resultBitmap, "", "myphoto.jpg");
			resetCamera();
			try {
				initCamera();
			} catch (Exception e) {
				Log.e(TAG, "initCamera Error after snapping");
			}
		}
	};

	private Bitmap cutScale(Bitmap bitmap) {
		// cut
		Bitmap contantBit = Bitmap.createBitmap(bitmap, picSize.getCutX(),
				picSize.getCutY(), picSize.getCutWidth(),
				picSize.getCutHeight());
		int aimSize = picSize.getCutWidth() > picSize.getCutHeight() ? picSize
				.getCutWidth() : picSize.getCutHeight();
		// scale
		return Bitmap.createScaledBitmap(contantBit, aimSize, aimSize, true);
	}

	/* get a fully initialized SurfaceHolder */
	@SuppressWarnings("deprecation")
	private void getSurfaceHolder() {
		mSurfaceHolder = mSurfaceView01.getHolder();
		// ************该函数可以preview窗口大小************
		// mSurfaceHolder.setFixedSize(500, 500);
		mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				if (!bPreviewing) {
					camera = Camera.open();
					camera.setDisplayOrientation(90);
				}
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				// TODO Auto-generated method stub
				if (bPreviewing) {
					camera.stopPreview();
				}
				Camera.Parameters params = camera.getParameters();
				// params.setPreviewSize(width, height);
				camera.setParameters(params);
				try {
					camera.setPreviewDisplay(mSurfaceHolder);
				} catch (IOException e) {
					e.printStackTrace();
				}
				camera.startPreview();
				bPreviewing = true;
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				camera.stopPreview();
				bPreviewing = false;
				camera.release();
				mSurfaceHolder = null;
			}
		});
		mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	/* func:宣告界面元件Button capture,FrameLayout frame */
	private void findViews() {
		/* 以SurfaceView作为相机Preview之用 */
		mSurfaceView01 = (SurfaceView) findViewById(R.id.mSurfaceView01);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		imageView1 = (ImageView) findViewById(R.id.imageView1);
	}

	private void drawRect() {
		// 这里的位置是相对于屏幕的位置
		DrawCaptureRect mDraw = new DrawCaptureRect(IsiCamera2Activity.this,
				picSize.getPreViewLeft(), picSize.getPreViewTop(),
				picSize.getPreViewWidth(), picSize.getPreViewHeight(),
				getResources().getColor(R.color.anycolor));
		addContentView(mDraw, new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
	}

	private void addListener() {
		button1.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if (event.getAction() == MotionEvent.ACTION_DOWN) {// 按下时自动对焦
					camera.autoFocus(null);
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {// 放开后拍照
					takeAPicture();
				}
				return true;
			}
		});

		button2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
				startActivityForResult(intent, RESULT);
			}
		});
	}

	private Bitmap bitmap;

	private void showPhoto(String picturePath) {
		if (picturePath.equals(""))
			return;
		Log.i("pic path", picturePath);
		if (bitmap != null && !bitmap.isRecycled()) {
			bitmap.recycle();
		}
		bitmap = BitmapFactory.decodeFile(picturePath);
		// 存在如下问题：如果是手机照片，会有90度的角度翻转
		if (picturePath.contains("Camera")) {
			bitmap = AdjustPhoto.adjustPhotoRotation(bitmap, 90);
		}
		showBitmap(bitmap);
	}

	private void hideBitmap() {
		imageView1.setVisibility(View.GONE);
	}

	private void showBitmap(Bitmap bitmap) {
		imageView1.setVisibility(View.VISIBLE);
		imageView1.setImageBitmap(bitmap);
	}
}