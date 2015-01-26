package com.demo.android.IsiCamera2;

import android.util.Log;

public class ViewPicSize {
	
	private static ViewPicSize size;
	private ViewPicSize(){}
	public static ViewPicSize getInstance(){
		if(null==size){
			size=new ViewPicSize();
		}
		return size;
	}
	
	
	public int getPreViewWidth() {
		judgePre();
		return preViewWidth;
	}
	public int getPreViewHeight() {
		judgePre();
		return preViewHeight;
	}
	public int getPreViewLeft() {
		judgePre();
		return preViewLeft;
	}
	public int getPreViewTop() {
		judgePre();
		return preViewTop;
	}
	
	private void judgePre(){
		if(order<1){
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public int getCutWidth() {
		judgeCut();
		return cutWidth;
	}
	public int getCutHeight() {
		judgeCut();
		return cutHeight;
	}
	public int getCutX() {
		judgeCut();
		return cutX;
	}
	public int getCutY() {
		judgeCut();
		return cutY;
	}
	
	private void judgeCut(){
		if(order<2){
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// 预览的比例
	final private double PRESIZERATE = 1.0;
	final private double PRETOPRATE = 0.0;
	
	private int preViewWidth;
	private int preViewHeight;
	private int preViewLeft;
	private int preViewTop;
	
	private int cutWidth;
	private int cutHeight;
	private int cutX;
	private int cutY;
	private double heightRate;
	
	private int order=0;
	
	// 预览的尺寸(相对于layout而言):onCreate中,在getDisplayMetrics之后调用
	public void initViewParam(int intScreenWidth, int intScreenHeight) {
		preViewWidth = (int) (intScreenWidth * PRESIZERATE)+1;
//		check preViewWidth<intScreenWidth
		preViewWidth=preViewWidth>intScreenWidth?intScreenWidth:preViewWidth;
		
		preViewLeft = (intScreenWidth - preViewWidth) / 2;
		preViewTop = (int) (intScreenHeight * PRETOPRATE);
		preViewHeight = preViewWidth;
		heightRate = ((double) preViewHeight+1 )/ (double) intScreenHeight;
		Log.i("preview heightrate",""+heightRate);
		order=1;
	}
	
	// 截图的尺寸(相对于截的bitmap而言):在initViewParam()之后开始调用
	public void initPicParam(int picWidth, int picHeight) {
		if(order!=1){
			try {
				throw new Exception();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		cutY = (int) (picHeight * PRETOPRATE)-1;
		cutY=cutY<0?0:cutY;
		cutWidth = (int) (picWidth * PRESIZERATE)+1;
		cutWidth=cutWidth>picWidth?picWidth:cutWidth;
		cutX = (picWidth - cutWidth) / 2;
		cutX=cutX<0?0:cutX;
		cutHeight = (int) (picHeight * heightRate*1.1)+1;
		order=2;
	}
}
