package com.example.planefight3d;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.res.Resources;

public class enemyplane {

	private loadmodels enemy_model;
	private float scale_factory;
	private int texid;
	private FloatBuffer vertexs;
	private FloatBuffer textures;
	private float xpos,ypos,zpos;
	public float vx,vy,vz;
	private float YAngle=0f;
	private int fire_step=0;
	private int vcount;
	private float xmin,ymin,zmin,xmax,ymax,zmax;
	
	
	public enemyplane(String filename,Resources res,int id,float scaled,float xpos,float ypos,float zpos,float vx,float vy,float vz)
	{
		
		this.enemy_model=new loadmodels();
		this.enemy_model.loadfromfile(filename, res);
		
		this.vertexs=this.enemy_model.vertexs_obj;
		this.textures=this.enemy_model.texture_obj;
		
		this.texid=id;
		this.vcount=this.enemy_model.vcounts;
		
		this.enemy_model.get_AABBInfo();
		this.xmin=this.enemy_model.xmin;
		this.xmax=this.enemy_model.xmax;
		this.ymin=this.enemy_model.ymin;
		this.ymax=this.enemy_model.ymax;
		this.zmin=this.enemy_model.zmin;
		this.zmax=this.enemy_model.zmax;
		
		this.xpos=xpos;
		this.ypos=ypos;
		this.zpos=zpos;
		this.vx=vx;
		this.vy=vy;
		this.vz=vz;
		this.scale_factory=scaled;
		
	}
	
	public float[] get_position()
	{
		float[] position=new float[3];
		position[0]=this.xpos;
		position[1]=this.ypos;
		position[2]=this.zpos;
		return position;
	}
	
	
	
	public void set_translate()
	{
		this.xpos+=this.vx;
		this.ypos+=this.vy;
		this.zpos+=this.vz;
	}
	
	public void drawself(GL10 gl)
	{
		gl.glPushMatrix();
		gl.glTranslatef(xpos, ypos, zpos);
		
		if(this.YAngle>=360f)this.YAngle=0f;
		else this.YAngle+=5f;
		
		gl.glRotatef(this.YAngle,0,1,0);
		
		gl.glScalef(scale_factory, scale_factory, scale_factory);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,textures);
		gl.glBindTexture(GL10.GL_TEXTURE_2D,texid);
		gl.glVertexPointer(3,GL10.GL_FLOAT,0,vertexs);		
		gl.glDrawArrays(GL10.GL_TRIANGLES,0,vcount);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_2D);
		
		
		gl.glPopMatrix();
	}
	//判断玩家子弹是否和敌机发生碰撞
	public boolean iscollide(float []xyz,float radius)
	{
		float cx_min,cx_max,cy_min,cy_max,cz_min,cz_max;
		float bx_min,bx_max,by_min,by_max,bz_min,bz_max;
		bx_min=xyz[0]-radius;
		bx_max=xyz[0]+radius;
		by_min=xyz[1]-radius;
		by_max=xyz[1]+radius;
		bz_min=xyz[2]-radius;
		bz_max=xyz[2]+radius;
		//敌人飞机包围盒的信息
		cx_min=this.xmin*this.scale_factory+this.xpos;
		cx_max=this.xmax*this.scale_factory+this.xpos;
		cy_min=this.ymin*this.scale_factory+this.ypos;
		cy_max=this.ymax*this.scale_factory+this.ypos;
		cz_min=this.zmin*this.scale_factory+this.zpos;
		cz_max=this.zmax*this.scale_factory+this.zpos;
		float[] over=new float[3];
		over=this.cal_over_all(cx_min, cy_min, cz_min, cx_max, cy_max, cz_max, bx_min, by_min, bz_min, bx_max, by_max, bz_max);
		if(over[0]>0&&over[1]>0f&&over[2]>0)return true;
		else return false;
		
	}
	
	
	
	
	public float cal_over_one(float amax,float amin,float bmax,float bmin)
	{
		float minMax=0f;//两个较大值中小的那个
		float maxMin=0f;//两个较小值中大的那个
		if(amax<bmax)//如果用z值进行举例相当于a在b的前边（右手坐标系下面）
		{
			minMax=amax;
			maxMin=bmin;
		}
		else
		{
			minMax=bmax;
			maxMin=amin;
		}
		if(minMax>maxMin)return minMax-maxMin;//说明有交集，返回相交部分长度
		else return 0;//否则表示没有交集
	}
	
	public float[] cal_over_all(float cx_min,float cy_min,float cz_min,float cx_max,float cy_max,float cz_max,
			float bx_min,float by_min,float bz_min,float bx_max,float by_max,float bz_max)
	{
		float xover=this.cal_over_one(cx_max,cx_min,bx_max,bx_min);
		float yover=this.cal_over_one(cy_max,cy_min,by_max,by_min);
		float zover=this.cal_over_one(cz_max, cz_min, bz_max, bz_min);
		return new float[] {xover,yover,zover};
	}
	
	public boolean is_over_boundary()
	{
		if(this.zpos>=0)return true;
		else return false;
	}
	
	public void fire(int texid)
	{
		if(this.fire_step<=50)
		{
			
		 this.fire_step++;
		}
		else
			//float x,float y,float z,float vz,int tex
		{
			bullet bomb=new bullet(this.xpos,this.ypos,this.zpos,this.vz,texid);
		  bulletmanager.enemy_bullets.add(bomb);
		  this.fire_step=0;
			
		}
	}
	//判断敌机是否和player相撞
	public boolean is_collide_with_player(player p)
	{
		float cx_min,cy_min,cz_min,cx_max,cy_max,cz_max;
		float bx_min,by_min,bz_min,bx_max,by_max,bz_max;
		cx_min=p.xmin*0.015f+player.xpos*0.015f;
		cx_max=p.xmax*0.015f+player.xpos*0.015f;
		cy_min=p.ymin*0.015f+player.ypos*0.015f;
		cy_max=p.ymax*0.015f+player.ypos*0.015f;
		cz_min=p.zmin*0.015f-2.6f;
		cz_max=p.zmax*0.015f-2.6f;
		
		bx_min=this.xmin*this.scale_factory+this.xpos;
		bx_max=this.xmax*this.scale_factory+this.xpos;
		by_min=this.ymin*this.scale_factory+this.ypos;
		by_max=this.ymax*this.scale_factory+this.ypos;
		bz_min=this.zmin*this.scale_factory+this.zpos;
		bz_max=this.zmax*this.scale_factory+this.zpos;
		float[] over=new float[3];
		over=this.cal_over_all(cx_min, cy_min, cz_min, cx_max, cy_max, cz_max, bx_min, by_min, bz_min, bx_max, by_max, bz_max);
		if(over[0]>0&&over[1]>0f&&over[2]>0)return true;
		else return false;
		
		
		
	}
	
	
	
}
