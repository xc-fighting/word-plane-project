package com.example.planefight3d;

import javax.microedition.khronos.opengles.GL10;

public class bullet {
	
	private sphere bomb;
	private float xpos;
	private float ypos;
	private float  zpos;
	public float vz;
	
	public bullet(float x,float y,float z,float vz,int tex)
	{
		this.xpos=x;
		this.ypos=y;
		this.zpos=z;
		this.vz=vz;
		bomb=new sphere(tex,10,10,0.1f);
		bomb.initAll();
		
	}
	public void settrranslate()
	{		
		this.zpos+=this.vz;
	}
	public float getz()
	{
		return this.zpos;
	}
	
	public void drawself(GL10 gl)
	{
		gl.glPushMatrix();
		gl.glTranslatef(this.xpos,this.ypos,zpos);
		bomb.drawself(gl);
		gl.glPopMatrix();
	}
	
	public float[] getxyzr()
	{
		float[] info=new float[4];
		info[0]=this.xpos;
		info[1]=this.ypos;
		info[2]=this.zpos;
		info[3]=0.1f;//°ë¾¶
		return info;
	}


}
