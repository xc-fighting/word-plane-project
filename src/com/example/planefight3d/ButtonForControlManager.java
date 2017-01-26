package com.example.planefight3d;

import javax.microedition.khronos.opengles.GL10;

public class ButtonForControlManager {
	
	public static int[] button_ids=new int[4];//用来保存四个方向按钮的纹理id,0,1,2,3 ----left,right,up,down
	
	private static ButtonForControl[] buttons=new ButtonForControl[4];
	private float button_len;
	private float zposition;
	
	public ButtonForControlManager(float len,float zpos)
	{
		this.zposition=zpos;
		this.button_len=len;
		for(int i=0;i<4;i++)
		{
			buttons[i]=new ButtonForControl(button_ids[i],button_len,i);
		}
	}
	
	public void set_touch_area_of_all()
	{
		buttons[0].touch_area[0]=0;//left
		buttons[0].touch_area[1]=GameActivity.width*(this.button_len/2);//right
		buttons[0].touch_area[2]=GameActivity.height*(0.5f-(0.25f*this.button_len/constant.edge));//top
		buttons[0].touch_area[3]=GameActivity.height*(0.5f+(0.25f*this.button_len/constant.edge));//bottom
		
		buttons[1].touch_area[0]=GameActivity.width*this.button_len;
		buttons[1].touch_area[1]=GameActivity.width*1.5f*this.button_len;
		buttons[1].touch_area[2]=GameActivity.height*(0.5f-(0.25f*this.button_len/constant.edge));
		buttons[1].touch_area[3]=GameActivity.height*(0.5f+(0.25f*this.button_len/constant.edge));
				
		buttons[2].touch_area[0]=GameActivity.width*(0.5f*this.button_len);
		buttons[2].touch_area[1]=GameActivity.width*(this.button_len);
		buttons[2].touch_area[2]=GameActivity.height*(0.5f-(0.75f*this.button_len/constant.edge));
		buttons[2].touch_area[3]=GameActivity.height*(0.5f-(0.25f*this.button_len/constant.edge));
								
		buttons[3].touch_area[0]=GameActivity.width*(0.5f*this.button_len);
		buttons[3].touch_area[1]=GameActivity.width*(this.button_len);
		buttons[3].touch_area[2]=GameActivity.height*(0.5f+(0.25f*this.button_len/constant.edge));
		buttons[3].touch_area[3]=GameActivity.height*(0.5f+(0.75f*this.button_len/constant.edge));
		
		
				
	}
	
	public static void listen_control_touch(float x,float y)
	{
		for(int i=0;i<4;i++)
		{
			if((x>buttons[i].touch_area[0]&&x<buttons[i].touch_area[1])&&(y>buttons[i].touch_area[2]&&y<buttons[i].touch_area[3]))
			{
				switch(i)
				{
					case 0://left
					{
						player.xpos-=0.1f;
					}break;
					case 1://right
					{
						player.xpos+=0.1f;
					}break;
					case 2://up
					{
						player.ypos+=0.1f;
					}break;
					case 3://down
					{
						player.ypos-=0.1f;
					}break;
				}
			}
		}
	}
	
	public void drawself(GL10 gl)
	{
		gl.glPushMatrix();
		
		gl.glTranslatef(0f,0f,this.zposition);
		
		//画左按钮
		gl.glPushMatrix();
		gl.glTranslatef(-1+this.button_len/2,0,0);
		buttons[0].drawself(gl);
		gl.glPopMatrix();
		//画右按钮
		gl.glPushMatrix();
		gl.glTranslatef(-1+2.5f*this.button_len,0,0);
		buttons[1].drawself(gl);
		gl.glPopMatrix();
		//画上按钮
		gl.glPushMatrix();
		gl.glTranslatef(-1+1.5f*this.button_len,this.button_len,0);
		buttons[2].drawself(gl);
		gl.glPopMatrix();
		//画下按钮
		gl.glPushMatrix();
		gl.glTranslatef(-1+1.5f*this.button_len,-this.button_len,0);
		buttons[3].drawself(gl);
		gl.glPopMatrix();
		
		gl.glPopMatrix();
	}
	
	
	
	

}
