package com.example.planefight3d;

import javax.microedition.khronos.opengles.GL10;
//«Ú–Œµ–»À£¨‘… Ø
public class enemy {
	
	sphere enemy_entity;
	private float xpos;
	private float ypos;
	private float zpos;
	private float vz;
	private float vx;
	private float vy;
	private float radius;
	private float rotate_angle=0f;
	
	
	public float[] getXYZR()
	{
		float [] info=new float[4];
		info[0]=this.xpos;
		info[1]=this.ypos;
		info[2]=this.zpos;
		info[3]=this.radius;
		return info;
	}
	
	public enemy(int id,int rows,int cols,float r,float x,float y,float z,float vz)
	{
		enemy_entity=new sphere(id,rows,cols,r);
		enemy_entity.initAll();
		this.xpos=x;
		this.ypos=y;
		this.zpos=z;
		this.radius=r;
		this.vz=vz;
	}
	
	public void drawself(GL10 gl)
	{
		gl.glPushMatrix();
		gl.glTranslatef(xpos,ypos,zpos);
		if(this.rotate_angle<=360f)this.rotate_angle+=20;
		else this.rotate_angle=0f;
			
		gl.glRotatef(this.rotate_angle,1,0,0);
		this.enemy_entity.drawself(gl);
		gl.glPopMatrix();
	}
	public boolean isdestroyed(float[]xyz,float radius)//≈–∂œ≈ˆ◊≤
	{
		float dx=Math.abs(xpos-xyz[0]);
		float dy=Math.abs(ypos-xyz[1]);
		float dz=Math.abs(zpos-xyz[2]);
		float dis=dx*dx+dy*dy+dz*dz;
		if(dis<=(radius+this.radius)*(radius+this.radius))return true;
		else return false;
		
	}
	public void calculate_vxy()
	{
		float dx=player.xpos-this.xpos;
		float dy=player.ypos-this.ypos;
		float dis=(float) Math.sqrt(dx*dx+dy*dy);
		if(dis==0)dis=2500;
		this.vx=dx/dis*0.03f;
		this.vy=dy/dis*0.03f;
	}
	
	public void set_translate()
	{
		this.zpos+=this.vz;
		this.xpos+=this.vx;
		this.ypos+=this.vy;
	}
	

	
	

}
