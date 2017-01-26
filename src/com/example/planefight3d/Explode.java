package com.example.planefight3d;

import javax.microedition.khronos.opengles.GL10;

public class Explode {
	
	public static int[] explode_id=new int [6];
	
	private int cur_frame;
	private TextureRect[] explode_pic=new TextureRect[6];
	
	private float xpos;
	private float ypos;
	private float zpos;
	
	
	
	
	public Explode(float width,float height,float x,float y,float z)
	{
		
		this.xpos=x;
		this.ypos=y;
		this.zpos=z;
		this.cur_frame=0;
		for(int i=0;i<6;i++)
		{
			this.explode_pic[i]=new TextureRect(Explode.explode_id[i],width,height);
		}
	}
	
	public void drawself(GL10 gl)
	{
		gl.glEnable(GL10.GL_BLEND);						//开启混合
       	gl.glBlendFunc(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);//设置混合因子
		gl.glPushMatrix();
		gl.glTranslatef(this.xpos,this.ypos,this.zpos);
		this.explode_pic[this.cur_frame].drawself(gl);
		gl.glPopMatrix();
		gl.glDisable(GL10.GL_BLEND);
	}
	
	public boolean is_ani_over()//判断爆炸动画是否已经播放完成
	{
		if(this.cur_frame==5)return true;
		else return false;
	}
	
	public void set_frame_add()
	{
		this.cur_frame++;
	}
	

}
