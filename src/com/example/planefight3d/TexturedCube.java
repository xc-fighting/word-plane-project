package com.example.planefight3d;

import javax.microedition.khronos.opengles.GL10;

//这个是专门负责单词方块的
public class TexturedCube {
	
	private TextureRect panel;//用于显示字母的面
	private float vx;
	private float vy;
	private float vz;
	private float xpos;
	private float ypos;
	private float zpos;
	private float  yangle=0;
	private float len;
	
	private char cube_alpha;
	
	public void set_cube_alpha(char ch)
	{
		this.cube_alpha=ch;
	}
	public char get_cube_alpha()
	{
		return this.cube_alpha;
	}
	
	public TexturedCube(float vx,float vy,float vz,float x,float y,float z,int texid,float len)
	{
		this.panel=new TextureRect(texid,len,len);
	    this.vx=vx;
	    this.vy=vy;
	    this.vz=vz;
	    this.xpos=x;
	    this.ypos=y;
	    this.zpos=z;
		this.len=len;
	}
	public void set_tanslate()
	{
		this.xpos+=this.vx;
		this.ypos+=this.vy;
		this.zpos+=this.vz;
	}
	
	public float[] getxyzl()
	{
		float[] position=new float[4];
		position[0]=this.xpos;
		position[1]=this.ypos;
		position[2]=this.zpos;
		position[3]=this.len;
		return position;
	}
	
	
	
	public void drawself(GL10 gl)
	{
		gl.glPushMatrix();
		gl.glTranslatef(this.xpos,this.ypos,this.zpos);
		if(this.yangle<=360)this.yangle+=2;
		else this.yangle=0f;
		gl.glRotatef(this.yangle,0,1,0);
		//正面
		gl.glPushMatrix();
		gl.glTranslatef(0,0,this.len/2);
		this.panel.drawself(gl);
		gl.glPopMatrix();
		//背面
        gl.glPushMatrix();
		gl.glTranslatef(0,0,-this.len/2);
		this.panel.drawself(gl);
		gl.glPopMatrix();
		//左面
		gl.glPushMatrix();
		gl.glTranslatef(-this.len/2,0,0);
		gl.glRotatef(-90f,0,1,0);
		this.panel.drawself(gl);		
		gl.glPopMatrix();
		//右面
		gl.glPushMatrix();
		gl.glTranslatef(this.len/2,0,0);
		gl.glRotatef(90f,0,1,0);
		this.panel.drawself(gl);
		gl.glPopMatrix();
		//上面
		gl.glPushMatrix();
		gl.glTranslatef(0,this.len/2,0);
		gl.glRotatef(-90,1,0,0);
		this.panel.drawself(gl);
		gl.glPopMatrix();
		//下面
		gl.glPushMatrix();
		gl.glTranslatef(0,-this.len/2,0);
		gl.glRotatef(90,1,0,0);
		this.panel.drawself(gl);
		gl.glPopMatrix();
		
		gl.glPopMatrix();
	}

}
