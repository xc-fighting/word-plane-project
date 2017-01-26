package com.example.planefight3d;

import javax.microedition.khronos.opengles.GL10;

public class Daoju {
	private TextureRect rect;
	
	public float width;
	public float height;
	
	public float[] touch_areas=new float[4];
	
	public int type;
	
	public Daoju(int texid,float width,float height,int type)
	{
		this.type=type;
		this.width=width;
		this.height=height;
		this.rect=new TextureRect(texid,width,height);
	}
	public void drawself(GL10 gl)
	{
		gl.glPushMatrix();
		
		this.rect.drawself(gl);
		gl.glPopMatrix();
	}
	
	
}
