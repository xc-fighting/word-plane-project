package com.example.planefight3d;

import javax.microedition.khronos.opengles.GL10;

public class Timer {

	public static int time_value;
	public static int init_time_value;
	
	private TextureRect[] number=new TextureRect[10];
	private TextureRect show_miao;
	private int[] tex_ids=new int[10];
	private float x_translate;
	private float y_translate;
	private float z_translate;
	private float dx;
	private float width;
	public Timer(int tex_miao,int value,float width,float xpos,float ypos,float zpos,float dx)
	{
		//时间值和方框的宽度
		time_value=value;
		
		init_time_value=value;
		
		this.width=width;
		//秒字的显示位置
		this.x_translate=xpos;
		this.y_translate=ypos;
		this.z_translate=zpos;
		//每个方块之间的间隔
		this.dx=dx;
		//调用数字纹理id
		tex_ids=scoremanager.texIDS;
		for(int i=0;i<10;i++)
		{
			number[i]=new TextureRect(tex_ids[i],width,width);
		}
		this.show_miao=new TextureRect(tex_miao,width,width);
		
	}
	public void time_decrease()
	{
		time_value--;
	}
	public int get_time()
	{
		return time_value;
	}
	public void drawself(GL10 gl)
	{
		String value_str=time_value+"";
		gl.glPushMatrix();
		
		gl.glTranslatef(x_translate, y_translate, z_translate);
		this.show_miao.drawself(gl);
		
		for(int i=value_str.length()-1;i>=0;i--)
		{
			gl.glPushMatrix();
			char ch=value_str.charAt(i);
			gl.glTranslatef(-(dx+width)*(value_str.length()-i),0f,0f);
			number[ch-'0'].drawself(gl);
			gl.glPopMatrix();
		}
		
		gl.glPopMatrix();
	}
	
}
