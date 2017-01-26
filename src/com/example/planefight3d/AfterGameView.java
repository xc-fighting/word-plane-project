package com.example.planefight3d;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

import android.util.AttributeSet;

import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;


public class AfterGameView  extends SurfaceView implements Callback{

	
	public static String button_str = "txt";
	
	private SurfaceHolder sfh;
	private Canvas canvas;
	
	private Bitmap back_ground;
	AfterGameActivity m;
	
	static float screenWidth;		//��Ļ���
	static float screenHeight;	//��Ļ�߶�

	

	public AfterGameView(Context context, AttributeSet attrs) {//��ע1

		super(context, attrs);
		
		sfh = this.getHolder();
		sfh.addCallback(this);
		
		this.setKeepScreenOn(true);
		setFocusable(true);
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		
		screenWidth = this.getWidth();//ȡ��Ļ���                                                    
		screenHeight = this.getHeight(); 
		this.draw();

	} 
	public void draw() {
		
		back_ground = BitmapFactory.decodeResource( this.getContext().getResources(), R.drawable.bg);
		back_ground=Bitmap.createScaledBitmap(back_ground, (int)screenWidth,(int)screenHeight,true); //����ͼƬ
		
		canvas = sfh.lockCanvas();
		
		canvas.drawBitmap(back_ground, 0,0,null);
		sfh.unlockCanvasAndPost(canvas);
	}

	

	


	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub

	}
}
