package com.example.planefight3d;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class lifemanager {
  
	public static ArrayList<life> lifes=new ArrayList<life>();
	public static int life_value;
	public boolean is_game_over=true;
	private float xpos;
	private float ypos;
	private float zpos;
	private static float width;
	private static int  texid;
	
	
	public lifemanager(int value,int id,float wid,float height,float x,float y,float z)
	{
		this.xpos=x;
		this.ypos=y;
		this.zpos=z;
		life_value=value;
		width=wid;
		texid=id;
		this.init_manager(texid, width, height);
	}
	
	public void init_manager(int texid,float width,float height)
	{
		life life_icon;
		for(int i=0;i<life_value;i++)
		{
			life_icon=new life(texid,width,height);
			lifes.add(life_icon);
		}
	}
	
	public static void life_add_action()
	{
		life_value+=1;
		life tmp_life=new life(texid,width,width);
		lifes.add(tmp_life);
		
		
	}
	
	public  void life_decrease()
	{
		if(playerthread.is_life_decrease==true)
		{
			if(life_value>0)
			{
				life_value--;
			
			}
			else
			{
				this.is_game_over=false;
			}
			playerthread.is_life_decrease=false;
		}
	}
	
	public void drawself(GL10 gl)
	{
		gl.glPushMatrix();
		gl.glTranslatef(this.xpos,this.ypos,this.zpos);
		for(int i=0;i<life_value;i++)
		{
			lifes.get(i).drawself(gl);
			gl.glTranslatef(width+0.02f,0f,0f);
		}
		gl.glPopMatrix();
	}
	

}
