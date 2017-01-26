package com.example.planefight3d;

import javax.microedition.khronos.opengles.GL10;


//这个类用来实现控制方向按键的功能

public class ButtonForControl {
	
	TextureRect button;
	private int texture_id;
	private float length;
	private int dir_id;
	
	public float[] touch_area=new float[4];
	public ButtonForControl(int texid,float len,int id)
	{
		this.texture_id=texid;
		this.length=len;
		this.dir_id=id;
		button=new TextureRect(texture_id,length,length);
	}
	public int get_dir_id()
	{
		return this.dir_id;
	}
	public void drawself(GL10 gl)
	{
		gl.glPushMatrix();
		this.button.drawself(gl);
		gl.glPopMatrix();
	}

}
