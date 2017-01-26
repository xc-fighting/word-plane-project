package com.example.planefight3d;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("DrawAllocation")
public class MenuView extends SurfaceView implements SurfaceHolder.Callback{

	MenuActivity m;
	
	
	
	MenuAnmiThread thread;
	
	private Bitmap[] menu=new Bitmap[10];
//	private Bitmap setting;
//	private Bitmap log;
	private Bitmap back_ground;
	//private Bitmap title;
//	private Bitmap banner;
	
	int currentIndex=3;				//当前选中的菜单编号	
	float mPreviousX;				//上次触控的X坐标
	float mPreviousY;				//上次触控的Y坐标	
	float changePercent=0;			//动画进行的百分比
	int anmiState=0;				//0-没有动画  1-向右走  2-向左走
	
	float currentSelectWidth;			//当前菜单项宽度
	float currentSelectHeight;		//当前菜单项高度
	float currentSelectX;			//当前菜单项X位置
	float currentSelectY;			//当前菜单项Y位置	
			
	float leftWidth;				//紧邻当前菜单项左侧菜单项的宽度		
	float leftHeight;				//紧邻当前菜单项左侧菜单项的高度	
	float tempxLeft;				//紧邻当前菜单项左侧菜单项的X坐标
	float tempyLeft;				//紧邻当前菜单项左侧菜单项的Y坐标	
	
	float rightWidth;				//紧邻当前菜单项右侧菜单项的宽度	
	float rightHeight;				//紧邻当前菜单项右侧菜单项的高度	
	float tempxRight;				//紧邻当前菜单项右侧菜单项的X坐标
	float tempyRight;				//紧邻当前菜单项右侧菜单项的Y坐标	
	
	public float wenzi_pos;
	
	public boolean repeat=true;
	
	public String tips="欢迎使用软件，请您登入";
	
	
	//以下变常量用于菜单动画界面
		static float screenWidth;		//屏幕宽度
		static float screenHeight;	//屏幕高度
		static float bigWidth;		//选中菜单项的宽度
		static float bigHeight;		//选中菜单项的高度
		static float smallWidth;		//未选中菜单项的宽度
		static float smallHeight;//未选中菜单项的高度
	    
		static float selectX;//选中菜单项左侧在屏幕上的X位置
		static float selectY;		//选中菜单项上侧在屏幕上的Y位置
		static int span=10;							//菜单项之间的间距
		static int slideSpan=30;					//滑动阈值
		
		static int totalSteps=10;					//动画的总步数
		static float percentStep=1.0f/totalSteps;	//每一步的动画百分比
		static int timeSpan=20;						//每一步动画的间隔时间
	
	
	
	
	public MenuView(Context menu) {
		super(menu);
		this.m=(MenuActivity)menu;
		this.getHolder().addCallback(this);
		this.init();
		this.init_bitmap();
		wenzi_pos=0;
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
		
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
	    
		
		thread=new MenuAnmiThread(this);
		thread.set_after(currentIndex);
		thread.start();
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		repeat=false;
		try
		{
			thread.join();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	public void init()
	{
		m.get_screen_info();
		MenuView.screenWidth=MenuActivity.width;
		MenuView.screenHeight=MenuActivity.height;
		MenuView.bigWidth=screenWidth/6;
		MenuView.bigHeight=screenHeight/4;
		MenuView.smallWidth=screenWidth/8;
		MenuView.smallHeight=screenHeight/6;
		MenuView.selectX=screenWidth/2-bigWidth/2;
		MenuView.selectY=7*screenHeight/8-bigHeight/2;
		
		
		this.currentSelectWidth=bigWidth;		//当前选中菜单宽度
		this.currentSelectHeight=bigHeight;		//当前选中菜单高度
		this.currentSelectX=selectX;				//当前选中菜单X位置
		this.currentSelectY=selectY;				//当前选中菜单Y位置	
		this.rightWidth=smallWidth;				//紧邻右侧的宽度		
		this.leftWidth=smallWidth;				//紧邻左侧的宽度		
		this.leftHeight=smallHeight;				//紧邻左侧的高度	
		this.rightHeight=smallHeight;			//紧邻右侧的高度
		this.tempxLeft=currentSelectX-(span+leftWidth);					//紧邻左侧的X
		this.tempyLeft=currentSelectY+(currentSelectHeight-leftHeight);	//紧邻左侧的Y坐标	
		this.tempxRight=currentSelectX+(span+currentSelectWidth);		//紧邻右侧的X	
		this.tempyRight=currentSelectY+(currentSelectHeight-rightHeight);//紧邻右侧的Y坐标
		
	
		
		
		
		
	}
	
	public void init_bitmap()
	{
		menu[0]=BitmapFactory.decodeResource(m.getResources(),R.drawable.beici1);
		menu[1]=BitmapFactory.decodeResource(m.getResources(),R.drawable.play1);
		menu[2]=BitmapFactory.decodeResource(m.getResources(),R.drawable.paihang);
		menu[3]=BitmapFactory.decodeResource(m.getResources(),R.drawable.aboutgame);
		menu[4]=BitmapFactory.decodeResource(m.getResources(),R.drawable.lianxi);
		menu[5]=BitmapFactory.decodeResource(m.getResources(),R.drawable.shengci);
		menu[6]=BitmapFactory.decodeResource(m.getResources(),R.drawable.niujin);
		menu[7]=BitmapFactory.decodeResource(m.getResources(),R.drawable.kaokaoni);
		menu[8]=BitmapFactory.decodeResource(m.getResources(),R.drawable.renwu);
		menu[9]=BitmapFactory.decodeResource(m.getResources(),R.drawable.settingbtn);
	//	setting=BitmapFactory.decodeResource(m.getResources(),R.drawable.renwu);		
	//	setting=Bitmap.createScaledBitmap(setting,(int)(screenWidth/8),(int)(screenHeight/8),true);
		
	//	log=BitmapFactory.decodeResource(m.getResources(),R.drawable.settingbtn);
	//	log=Bitmap.createScaledBitmap(log,(int)(screenWidth/8),(int)(screenHeight/8),true);
		
	//	banner=BitmapFactory.decodeResource(m.getResources(),R.drawable.banner);
	//	banner=Bitmap.createScaledBitmap(banner,(int)(screenWidth),(int)(screenHeight/8),true);
		
		//title=BitmapFactory.decodeResource(m.getResources(),R.drawable.logo);
	//	title=Bitmap.createScaledBitmap(title,(int)(3*screenWidth/4),(int)(screenHeight/4),true);
		
		back_ground=BitmapFactory.decodeResource(m.getResources(),R.drawable.yunxing1);
		back_ground=Bitmap.createScaledBitmap(back_ground, (int)screenWidth,(int)screenHeight,true);
		
	}
	
	
	public void onDraw(Canvas canvas)
	{
		//画背景
		canvas.drawBitmap(back_ground, 0,0,null);
		//canvas.drawBitmap(log, 0,screenHeight/8 ,null);
		//canvas.drawBitmap(setting, screenWidth-setting.getWidth(),screenHeight/8,null);
		//canvas.drawBitmap(title, screenWidth/8,screenHeight/4,null);
		
		//Paint p=new Paint();
		//p.setARGB(50,0,0,255);
		//canvas.drawRect(0,0,screenWidth,screenHeight/8,p);
		//canvas.drawBitmap(banner, 0,0 ,null);
		//获取当前图片
		Bitmap selectBM=this.menu[this.currentIndex];
		//根据参数计算出用于绘制当前菜单项的图片
				selectBM=Bitmap.createScaledBitmap(
						selectBM, 
						(int)currentSelectWidth, 
						(int)currentSelectHeight, 
						true
				);
					
				//绘制当前的菜单项
				canvas.drawBitmap(selectBM, currentSelectX, currentSelectY,null);
		//若当前菜单项不是第一项则绘制紧邻当前菜单项左侧的菜单项
				if(currentIndex>0){	
					//缩放出绘制用图片
					Bitmap left=Bitmap.createScaledBitmap
					(
							menu[currentIndex-1], 
							(int)leftWidth, 
							(int)leftHeight, 
							true
					);		
					//绘制图片
					canvas.drawBitmap(left, tempxLeft, tempyLeft,null);
				}	
				
				//若当前菜单项不是最后一项则绘制紧邻当前菜单项右侧的菜单项
				if(currentIndex<menu.length-1)
				{
					Bitmap right=Bitmap.createScaledBitmap	//缩放出绘制用图片
					(
							menu[currentIndex+1], 
							(int)rightWidth, 
							(int)rightHeight, 
							false
					);	
					canvas.drawBitmap(right, tempxRight, tempyRight, null);//绘制图片
				}
				
				
				

				//向左绘制其他未选中的菜单
				for(int i=currentIndex-2;i>=0;i--){	
					float tempx=tempxLeft-(span+smallWidth)*(currentIndex-1-i);//计算X值
					if(tempx<-smallWidth){								//若绘制出来不再屏幕上则不用绘制了
						break;
					}
					int tempy=(int)(selectY+(bigHeight-smallHeight));			//计算Y值
					Bitmap tempbm=Bitmap.createScaledBitmap				//缩放出绘制用图片
					(
							menu[i], 
							(int)smallWidth, 
							(int)smallHeight, 
							true
					);
					canvas.drawBitmap(tempbm, tempx, tempy,null);	//绘制图片
				}
				
				for(int i=currentIndex+2;i<menu.length;i++)			//向右绘制其他未选中的菜单
				{	
					//计算X值
		            float tempx=tempxRight+rightWidth+span+(span+smallWidth)*(i-(currentIndex+1)-1);			
					if(tempx>screenWidth)
					{//若绘制出来不再屏幕上则不用绘制了
						break;
					}			
					
					int tempy=(int)(selectY+(bigHeight-smallHeight));		//计算Y值	
					Bitmap tempbm=Bitmap.createScaledBitmap			//缩放出绘制用图片
					(
							menu[i], 
							(int)smallWidth, 
							(int)smallHeight, 
							true
					);	
					//绘制图片
					canvas.drawBitmap(tempbm, tempx, tempy,null);		
				}
				if(wenzi_pos<=screenWidth)wenzi_pos+=2f;
				else wenzi_pos=0;
				Paint paint=new Paint();
				paint.setColor(Color.YELLOW);
				paint.setTextSize(35);
				canvas.drawText(tips,wenzi_pos,screenHeight/12, paint);
								
				
				
	}
	
	//重绘界面的方法
		public void repaint()
		{
			SurfaceHolder holder=this.getHolder();		//SurfaceHolder
			Canvas canvas = holder.lockCanvas();		//获取画布
			try{
				synchronized(holder){
					onDraw(canvas);						//绘制
				}			
			}
			catch(Exception e){e.printStackTrace();}
			finally{
				if(canvas != null){
					holder.unlockCanvasAndPost(canvas);
				}
			}
		}
	
	public boolean onTouchEvent(MotionEvent e)
	{

    	if(anmiState!=0)
    	{//若动画播放中则触控无效
    		return true;
    	}
    	//获取当前触控点的XY坐标
        float x = e.getX();
        float y = e.getY();
        //根据触控的不同动作执行不同的业务逻辑
        switch (e.getAction())  {
        	case MotionEvent.ACTION_MOVE:
        		break;
        	case MotionEvent.ACTION_DOWN:
        	  //若动作为按下触控笔则记录XY位置
        	  mPreviousX=x;//记录触控笔X位置
        	  mPreviousY=y;//记录触控笔Y位置
        	  if((mPreviousX>=0&&mPreviousX<=screenWidth/8)&&(mPreviousY>=screenHeight/8&&mPreviousY<=screenHeight/4))
        	  {
        		  m.handler.sendEmptyMessage(8);
        	  }
        	  if((mPreviousX>=7*screenWidth/8&&mPreviousX<=screenWidth)&&(mPreviousY>=screenHeight/8&&mPreviousY<=screenHeight/4))
        	  {
        		  m.handler.sendEmptyMessage(9);
        	  }
            break;
            case MotionEvent.ACTION_UP:
              //若动作为抬起则根据X位移的不同执行左滑、右滑或选中菜单项的业务逻辑	
              float dx=x- mPreviousX; //计算X位移
              if(dx<-slideSpan)
              {//若X位移小于阈值则向左滑动
            	  if(currentIndex<menu.length-1)
            	  {//若当前菜单项不是最后一个菜单项则向左滑动
            		  //计算滑动完成后的当前菜单项编号
            		  int afterCurrentIndex=currentIndex+1;
            		  //动画状态值设置为2-向左走
            		  anmiState=2;
            		  //启动线程播放动画并更新状态值
            		  thread.set_after(afterCurrentIndex);
            	  } 
              }
              else if(dx>slideSpan)
              {//若X位移大于阈值则向右滑动
            	  if(currentIndex>0)
            	  {//若当前菜单项不是第一个菜单项则向左滑动
            		  //计算滑动完成后的当前菜单项编号
            		  int afterCurrentIndex=currentIndex-1;
            		  //动画状态值设置为2-向右走
            		  anmiState=1;
            		  //启动线程播放动画并更新状态值
            		  thread.set_after(afterCurrentIndex);
            	  }            	  
              }
              else
              {
            	  if(			//若X位移在阈值内则判断有否选中某菜单项
                     mPreviousX>selectX&&mPreviousX<selectX+bigWidth&&
                     mPreviousY>selectY&&mPreviousY<selectY+menu[currentIndex].getHeight()&&
                     x>selectX&&x<selectX+bigWidth&&
                     y>selectY&&y<selectY+menu[currentIndex].getHeight()
            	  )
            	  {
            		m.handler.sendEmptyMessage(currentIndex);
            	  }
              }                           
            break;            
        }   
        return true;        
                
        
	}
	
	
	
	
	
	
	//菜单动画线程
	public class MenuAnmiThread extends Thread{
		MenuView mv;					//MenuSurfaceView的引用
		int afterCurrentIndex;				//动画播放完成后的当前菜单编号
		public MenuAnmiThread(MenuView mv){
			this.mv=mv;
			
		}
		
		public void set_after(int index)
		{
			this.afterCurrentIndex=index;
		}
		
		public void run()
		{
		   while(repeat)
		   {
			for(int i=0;i<=totalSteps;i++)
			{		//循环指定的次数完成动画
				mv.changePercent=percentStep*i;	//计算此步的百分比
				mv.init();						//初始化各个位置值			
				if(mv.anmiState==1)
				{	//向右的动画
					//根据百分比计算当前菜单项位置、大小
					mv.currentSelectX=mv.currentSelectX+(bigWidth+span)*mv.changePercent;
					mv.currentSelectY=mv.currentSelectY+(bigHeight-smallHeight)*mv.changePercent;
					mv.currentSelectWidth=(int)(smallWidth+(bigWidth-smallWidth)*(1-mv.changePercent));
					mv.currentSelectHeight=(int)(smallHeight+(bigHeight-smallHeight)*(1-mv.changePercent));
					//由于动画向右，左侧菜单项会变大，因此计算左侧菜单项大小
					mv.leftWidth=(int)(smallWidth+(bigWidth-smallWidth)*mv.changePercent);
					mv.leftHeight=(int)(smallHeight+(bigHeight-smallHeight)*mv.changePercent);				
				}
				else if(mv.anmiState==2)
				{	//向左的动画
					//根据百分比计算当前菜单项位置、大小
					mv.currentSelectX=mv.currentSelectX-(smallWidth+span)*mv.changePercent;
					mv.currentSelectY=mv.currentSelectY+(bigHeight-smallHeight)*mv.changePercent;
					mv.currentSelectWidth=(int)(smallWidth+(bigWidth-smallWidth)*(1-mv.changePercent));
					mv.currentSelectHeight=(int)(smallHeight+(bigHeight-smallHeight)*(1-mv.changePercent));
					//由于动画向左，右侧菜单项会变大，因此计算右侧菜单项大小
					mv.rightWidth=(int)(smallWidth+(bigWidth-smallWidth)*mv.changePercent);
					mv.rightHeight=(int)(smallHeight+(bigHeight-smallHeight)*mv.changePercent);					
				}
				
				//计算出紧邻左侧的菜单项位置
				mv.tempxLeft=mv.currentSelectX-(span+mv.leftWidth);			
				mv.tempyLeft=mv.currentSelectY+(mv.currentSelectHeight-mv.leftHeight);	
				//计算出紧邻右侧的菜单项位置
				mv.tempxRight=mv.currentSelectX+(span+mv.currentSelectWidth);
				mv.tempyRight=mv.currentSelectY+(mv.currentSelectHeight-mv.rightHeight);
			
				mv.repaint();				//重绘画面
				try{
					Thread.sleep(timeSpan);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					}
			}
			
			mv.anmiState=0;						//动画完成后设置动画状态为0-无动画		
			mv.currentIndex=afterCurrentIndex; 	//动画完成后更新当前的菜单项编号
			mv.init();							//初始化各个位置值
			mv.repaint();						//重绘画面
		}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
		

}
