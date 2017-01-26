package com.example.planefight3d;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.view.MotionEvent;

public class gameview extends GLSurfaceView{

	
	
	private gamerender render;
	private player p;
	private DaojuManager d_manager;
	

	
	public static float dx=0f;
	public static float dy=0f;
	 
	//private float mPreviousY=0;//上次的触控位置Y坐标
   // private float mPreviousX=0;//上次的触控位置Y坐标
  //  public int state=-1;
	
	public gameview(Context context) {
		super(context);
		render=new gamerender(this);		
		this.setRenderer(render);
		this.setRenderMode(GLSurfaceView.RENDERMODE_CONTINUOUSLY);
		this.requestFocus();
		this.setFocusable(true);
		
		constant.init_land(this.getResources());
		
		
	}
	
	
	
	
	
	
	
	public void set_listen_obj(player t)
	{
		this.p=t;
	}
	
	
	
	public void set_daoju_touch(DaojuManager d)
	{
		this.d_manager=d;
	}
	
	
	
	
	public boolean onTouchEvent(MotionEvent e)
	{
		    float y = e.getY();
	        float x = e.getX();
	        switch (e.getAction())
	        {
	        case MotionEvent.ACTION_DOWN:
	        {
	        	if((x<constant.fire_button_area[1]&&x>constant.fire_button_area[0])&&(y>constant.fire_button_area[2]&&y<constant.fire_button_area[3]))
	        	this.p.fire(constant.bullet_tex);
	        	this.d_manager.listen_touch(x, y);
	        	ButtonForControlManager.listen_control_touch(x, y);
	        	requestRender();//重绘画面
	        	
	        }break;
	    //    case MotionEvent.ACTION_MOVE:
	   //     {
	    //         dy = y - mPreviousY;//计算触控笔Y位移
	  //           dx = x - mPreviousX;//计算触控笔Y位移
	//	         player.xpos+=dx*0.3f;
	//	         player.ypos+=dy*0.3f;
	          
	            
	   //     }break;
	        
	       
	        }
	     //   mPreviousY = y;//记录触控笔位置
	     //   mPreviousX = x;//记录触控笔位置
	        return true;
	}
	
	
	
	

}
