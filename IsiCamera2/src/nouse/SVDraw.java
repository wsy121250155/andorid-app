package nouse;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class SVDraw extends SurfaceView implements SurfaceHolder.Callback {
	protected SurfaceHolder sh;
	private int mWidth;
	private int mHeight;
	// wsy 1-20
	private Canvas canvas;

	public SVDraw(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		sh = getHolder();
		sh.addCallback(this);
		// ******很重要，影响显示******
		sh.setFormat(PixelFormat.TRANSPARENT);
		setZOrderOnTop(true);
		// wsy 1-20
		canvas = sh.lockCanvas();
		if (null == canvas) {
			Log.i("wsy1", "canvas is null");
		}
	}

	void clearDraw() {
		Canvas canvas = sh.lockCanvas();
		canvas.drawColor(Color.BLUE);
		sh.unlockCanvasAndPost(canvas);
	}

	public void drawLine() {
		// Canvas canvas = sh.lockCanvas();
		if (null == canvas) {
			Log.i("wsy2", "canvas is null");
		}
		canvas.drawColor(Color.TRANSPARENT);
		Paint p = new Paint();
		p.setAntiAlias(true);
		p.setColor(Color.RED);
		p.setStyle(Style.STROKE);
		// canvas.drawPoint(100.0f, 100.0f, p);
		canvas.drawLine(0, 110, 500, 110, p);
		canvas.drawCircle(110, 110, 10.0f, p);
		sh.unlockCanvasAndPost(canvas);
	}

	// SurfaceHolder.Callback
	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int w, int h) {
		// TODO Auto-generated method stub
		mWidth = w;
		mHeight = h;
	}

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
	}
}
