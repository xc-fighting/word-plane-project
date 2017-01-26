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
	
	int currentIndex=3;				//��ǰѡ�еĲ˵����	
	float mPreviousX;				//�ϴδ��ص�X����
	float mPreviousY;				//�ϴδ��ص�Y����	
	float changePercent=0;			//�������еİٷֱ�
	int anmiState=0;				//0-û�ж���  1-������  2-������
	
	float currentSelectWidth;			//��ǰ�˵�����
	float currentSelectHeight;		//��ǰ�˵���߶�
	float currentSelectX;			//��ǰ�˵���Xλ��
	float currentSelectY;			//��ǰ�˵���Yλ��	
			
	float leftWidth;				//���ڵ�ǰ�˵������˵���Ŀ��		
	float leftHeight;				//���ڵ�ǰ�˵������˵���ĸ߶�	
	float tempxLeft;				//���ڵ�ǰ�˵������˵����X����
	float tempyLeft;				//���ڵ�ǰ�˵������˵����Y����	
	
	float rightWidth;				//���ڵ�ǰ�˵����Ҳ�˵���Ŀ��	
	float rightHeight;				//���ڵ�ǰ�˵����Ҳ�˵���ĸ߶�	
	float tempxRight;				//���ڵ�ǰ�˵����Ҳ�˵����X����
	float tempyRight;				//���ڵ�ǰ�˵����Ҳ�˵����Y����	
	
	public float wenzi_pos;
	
	public boolean repeat=true;
	
	public String tips="��ӭʹ���������������";
	
	
	//���±䳣�����ڲ˵���������
		static float screenWidth;		//��Ļ���
		static float screenHeight;	//��Ļ�߶�
		static float bigWidth;		//ѡ�в˵���Ŀ��
		static float bigHeight;		//ѡ�в˵���ĸ߶�
		static float smallWidth;		//δѡ�в˵���Ŀ��
		static float smallHeight;//δѡ�в˵���ĸ߶�
	    
		static float selectX;//ѡ�в˵����������Ļ�ϵ�Xλ��
		static float selectY;		//ѡ�в˵����ϲ�����Ļ�ϵ�Yλ��
		static int span=10;							//�˵���֮��ļ��
		static int slideSpan=30;					//������ֵ
		
		static int totalSteps=10;					//�������ܲ���
		static float percentStep=1.0f/totalSteps;	//ÿһ���Ķ����ٷֱ�
		static int timeSpan=20;						//ÿһ�������ļ��ʱ��
	
	
	
	
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
		
		
		this.currentSelectWidth=bigWidth;		//��ǰѡ�в˵����
		this.currentSelectHeight=bigHeight;		//��ǰѡ�в˵��߶�
		this.currentSelectX=selectX;				//��ǰѡ�в˵�Xλ��
		this.currentSelectY=selectY;				//��ǰѡ�в˵�Yλ��	
		this.rightWidth=smallWidth;				//�����Ҳ�Ŀ��		
		this.leftWidth=smallWidth;				//�������Ŀ��		
		this.leftHeight=smallHeight;				//�������ĸ߶�	
		this.rightHeight=smallHeight;			//�����Ҳ�ĸ߶�
		this.tempxLeft=currentSelectX-(span+leftWidth);					//��������X
		this.tempyLeft=currentSelectY+(currentSelectHeight-leftHeight);	//��������Y����	
		this.tempxRight=currentSelectX+(span+currentSelectWidth);		//�����Ҳ��X	
		this.tempyRight=currentSelectY+(currentSelectHeight-rightHeight);//�����Ҳ��Y����
		
	
		
		
		
		
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
		//������
		canvas.drawBitmap(back_ground, 0,0,null);
		//canvas.drawBitmap(log, 0,screenHeight/8 ,null);
		//canvas.drawBitmap(setting, screenWidth-setting.getWidth(),screenHeight/8,null);
		//canvas.drawBitmap(title, screenWidth/8,screenHeight/4,null);
		
		//Paint p=new Paint();
		//p.setARGB(50,0,0,255);
		//canvas.drawRect(0,0,screenWidth,screenHeight/8,p);
		//canvas.drawBitmap(banner, 0,0 ,null);
		//��ȡ��ǰͼƬ
		Bitmap selectBM=this.menu[this.currentIndex];
		//���ݲ�����������ڻ��Ƶ�ǰ�˵����ͼƬ
				selectBM=Bitmap.createScaledBitmap(
						selectBM, 
						(int)currentSelectWidth, 
						(int)currentSelectHeight, 
						true
				);
					
				//���Ƶ�ǰ�Ĳ˵���
				canvas.drawBitmap(selectBM, currentSelectX, currentSelectY,null);
		//����ǰ�˵���ǵ�һ������ƽ��ڵ�ǰ�˵������Ĳ˵���
				if(currentIndex>0){	
					//���ų�������ͼƬ
					Bitmap left=Bitmap.createScaledBitmap
					(
							menu[currentIndex-1], 
							(int)leftWidth, 
							(int)leftHeight, 
							true
					);		
					//����ͼƬ
					canvas.drawBitmap(left, tempxLeft, tempyLeft,null);
				}	
				
				//����ǰ�˵�������һ������ƽ��ڵ�ǰ�˵����Ҳ�Ĳ˵���
				if(currentIndex<menu.length-1)
				{
					Bitmap right=Bitmap.createScaledBitmap	//���ų�������ͼƬ
					(
							menu[currentIndex+1], 
							(int)rightWidth, 
							(int)rightHeight, 
							false
					);	
					canvas.drawBitmap(right, tempxRight, tempyRight, null);//����ͼƬ
				}
				
				
				

				//�����������δѡ�еĲ˵�
				for(int i=currentIndex-2;i>=0;i--){	
					float tempx=tempxLeft-(span+smallWidth)*(currentIndex-1-i);//����Xֵ
					if(tempx<-smallWidth){								//�����Ƴ���������Ļ�����û�����
						break;
					}
					int tempy=(int)(selectY+(bigHeight-smallHeight));			//����Yֵ
					Bitmap tempbm=Bitmap.createScaledBitmap				//���ų�������ͼƬ
					(
							menu[i], 
							(int)smallWidth, 
							(int)smallHeight, 
							true
					);
					canvas.drawBitmap(tempbm, tempx, tempy,null);	//����ͼƬ
				}
				
				for(int i=currentIndex+2;i<menu.length;i++)			//���һ�������δѡ�еĲ˵�
				{	
					//����Xֵ
		            float tempx=tempxRight+rightWidth+span+(span+smallWidth)*(i-(currentIndex+1)-1);			
					if(tempx>screenWidth)
					{//�����Ƴ���������Ļ�����û�����
						break;
					}			
					
					int tempy=(int)(selectY+(bigHeight-smallHeight));		//����Yֵ	
					Bitmap tempbm=Bitmap.createScaledBitmap			//���ų�������ͼƬ
					(
							menu[i], 
							(int)smallWidth, 
							(int)smallHeight, 
							true
					);	
					//����ͼƬ
					canvas.drawBitmap(tempbm, tempx, tempy,null);		
				}
				if(wenzi_pos<=screenWidth)wenzi_pos+=2f;
				else wenzi_pos=0;
				Paint paint=new Paint();
				paint.setColor(Color.YELLOW);
				paint.setTextSize(35);
				canvas.drawText(tips,wenzi_pos,screenHeight/12, paint);
								
				
				
	}
	
	//�ػ����ķ���
		public void repaint()
		{
			SurfaceHolder holder=this.getHolder();		//SurfaceHolder
			Canvas canvas = holder.lockCanvas();		//��ȡ����
			try{
				synchronized(holder){
					onDraw(canvas);						//����
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
    	{//�������������򴥿���Ч
    		return true;
    	}
    	//��ȡ��ǰ���ص��XY����
        float x = e.getX();
        float y = e.getY();
        //���ݴ��صĲ�ͬ����ִ�в�ͬ��ҵ���߼�
        switch (e.getAction())  {
        	case MotionEvent.ACTION_MOVE:
        		break;
        	case MotionEvent.ACTION_DOWN:
        	  //������Ϊ���´��ر����¼XYλ��
        	  mPreviousX=x;//��¼���ر�Xλ��
        	  mPreviousY=y;//��¼���ر�Yλ��
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
              //������Ϊ̧�������Xλ�ƵĲ�ִͬ���󻬡��һ���ѡ�в˵����ҵ���߼�	
              float dx=x- mPreviousX; //����Xλ��
              if(dx<-slideSpan)
              {//��Xλ��С����ֵ�����󻬶�
            	  if(currentIndex<menu.length-1)
            	  {//����ǰ�˵�������һ���˵��������󻬶�
            		  //���㻬����ɺ�ĵ�ǰ�˵�����
            		  int afterCurrentIndex=currentIndex+1;
            		  //����״ֵ̬����Ϊ2-������
            		  anmiState=2;
            		  //�����̲߳��Ŷ���������״ֵ̬
            		  thread.set_after(afterCurrentIndex);
            	  } 
              }
              else if(dx>slideSpan)
              {//��Xλ�ƴ�����ֵ�����һ���
            	  if(currentIndex>0)
            	  {//����ǰ�˵���ǵ�һ���˵��������󻬶�
            		  //���㻬����ɺ�ĵ�ǰ�˵�����
            		  int afterCurrentIndex=currentIndex-1;
            		  //����״ֵ̬����Ϊ2-������
            		  anmiState=1;
            		  //�����̲߳��Ŷ���������״ֵ̬
            		  thread.set_after(afterCurrentIndex);
            	  }            	  
              }
              else
              {
            	  if(			//��Xλ������ֵ�����ж��з�ѡ��ĳ�˵���
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
	
	
	
	
	
	
	//�˵������߳�
	public class MenuAnmiThread extends Thread{
		MenuView mv;					//MenuSurfaceView������
		int afterCurrentIndex;				//����������ɺ�ĵ�ǰ�˵����
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
			{		//ѭ��ָ���Ĵ�����ɶ���
				mv.changePercent=percentStep*i;	//����˲��İٷֱ�
				mv.init();						//��ʼ������λ��ֵ			
				if(mv.anmiState==1)
				{	//���ҵĶ���
					//���ݰٷֱȼ��㵱ǰ�˵���λ�á���С
					mv.currentSelectX=mv.currentSelectX+(bigWidth+span)*mv.changePercent;
					mv.currentSelectY=mv.currentSelectY+(bigHeight-smallHeight)*mv.changePercent;
					mv.currentSelectWidth=(int)(smallWidth+(bigWidth-smallWidth)*(1-mv.changePercent));
					mv.currentSelectHeight=(int)(smallHeight+(bigHeight-smallHeight)*(1-mv.changePercent));
					//���ڶ������ң����˵��������˼������˵����С
					mv.leftWidth=(int)(smallWidth+(bigWidth-smallWidth)*mv.changePercent);
					mv.leftHeight=(int)(smallHeight+(bigHeight-smallHeight)*mv.changePercent);				
				}
				else if(mv.anmiState==2)
				{	//����Ķ���
					//���ݰٷֱȼ��㵱ǰ�˵���λ�á���С
					mv.currentSelectX=mv.currentSelectX-(smallWidth+span)*mv.changePercent;
					mv.currentSelectY=mv.currentSelectY+(bigHeight-smallHeight)*mv.changePercent;
					mv.currentSelectWidth=(int)(smallWidth+(bigWidth-smallWidth)*(1-mv.changePercent));
					mv.currentSelectHeight=(int)(smallHeight+(bigHeight-smallHeight)*(1-mv.changePercent));
					//���ڶ��������Ҳ�˵��������˼����Ҳ�˵����С
					mv.rightWidth=(int)(smallWidth+(bigWidth-smallWidth)*mv.changePercent);
					mv.rightHeight=(int)(smallHeight+(bigHeight-smallHeight)*mv.changePercent);					
				}
				
				//������������Ĳ˵���λ��
				mv.tempxLeft=mv.currentSelectX-(span+mv.leftWidth);			
				mv.tempyLeft=mv.currentSelectY+(mv.currentSelectHeight-mv.leftHeight);	
				//����������Ҳ�Ĳ˵���λ��
				mv.tempxRight=mv.currentSelectX+(span+mv.currentSelectWidth);
				mv.tempyRight=mv.currentSelectY+(mv.currentSelectHeight-mv.rightHeight);
			
				mv.repaint();				//�ػ滭��
				try{
					Thread.sleep(timeSpan);
				}
				catch(Exception e)
				{
					e.printStackTrace();
					}
			}
			
			mv.anmiState=0;						//������ɺ����ö���״̬Ϊ0-�޶���		
			mv.currentIndex=afterCurrentIndex; 	//������ɺ���µ�ǰ�Ĳ˵�����
			mv.init();							//��ʼ������λ��ֵ
			mv.repaint();						//�ػ滭��
		}
		}
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
		

}
