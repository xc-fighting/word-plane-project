package com.example.planefight3d;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

import android.content.res.Resources;

public class player {
	
	loadmodels model;
	private int texid;
	private int vcount;
	private FloatBuffer vertexs;
	private FloatBuffer textures;
	public static float xpos,ypos;
	public static boolean isvisable=true;
	public float xmin,xmax,ymin,ymax,zmin,zmax;
	
	
	public player(String filename,Resources res,int id)
	{
		xpos=0f;
		ypos=0f;
		this.model=new loadmodels();
		model.loadfromfile(filename, res);
		this.vertexs=this.model.vertexs_obj;
		this.textures=this.model.texture_obj;
		this.texid=id;
		this.vcount=this.model.vcounts;
		this.model.get_AABBInfo();
		this.xmin=this.model.xmin;
		this.xmax=this.model.xmax;
		this.ymin=this.model.ymin;
		this.ymax=this.model.ymax;
		this.zmin=this.model.zmin;
		this.zmax=this.model.zmax;
		
	}
	
	

	public void drawself(GL10 gl)
	{
		gl.glPushMatrix();		
		gl.glTranslatef(xpos,ypos,0);
		//new
		gl.glScalef(0.015f,0.015f,0.015f);
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
	
	public void fire(int texid)
	{
	//	bullet b=new bullet((xpos+0.15f)*0.015f,(ypos+0.15f)*0.015f,-4f,-0.1f,texid);	
		bullet b=new bullet(xpos,ypos,-4f,-0.1f,texid);
		bulletmanager.player_bullets.add(b);
	}
	
	//���ü򵥵ĺ��ӷ���,�ж������ӵ�����ʯ����,��������������ײ
	public boolean iscollide(float[]xyz,float radius)
	{
		
		//�ж��Ƿ�������ײ
		//AABB�㷨��������,���ȶ����Χ�б߽�,������������������ת�������¼򻯺ͽ��Ʋ���
		float cx_min,cx_max,cy_min,cy_max,cz_min,cz_max;
		float bx_min,bx_max,by_min,by_max,bz_min,bz_max;
		bx_min=xyz[0]-radius;
		bx_max=xyz[0]+radius;
		by_min=xyz[1]-radius;
		by_max=xyz[1]+radius;
		bz_min=xyz[2]-radius;
		bz_max=xyz[2]+radius;
		//�ڽ��м����ʱ����Ҫ������������
		cx_min=this.xmin*0.015f+xpos;
		cx_max=this.xmax*0.015f+xpos;
		cy_min=this.ymin*0.015f+ypos;
		cy_max=this.ymax*0.015f+ypos;
		cz_min=this.zmin*0.015f-4f;
		cz_max=this.zmax*0.015f-4f;
		

		
		float[] over=new float[3];
		over=this.cal_over_all(cx_min, cy_min, cz_min, cx_max, cy_max, cz_max, bx_min, by_min, bz_min, bx_max, by_max, bz_max);
		if(over[0]>0&&over[1]>0f&&over[2]>0)return true;
		else return false;

		
	}
	
	public float cal_over_one(float amax,float amin,float bmax,float bmin)
	{
		float minMax=0f;//�����ϴ�ֵ��С���Ǹ�
		float maxMin=0f;//������Сֵ�д���Ǹ�
		if(amax<bmax)//�����zֵ���о����൱��a��b��ǰ�ߣ���������ϵ���棩
		{
			minMax=amax;
			maxMin=bmin;
		}
		else
		{
			minMax=bmax;
			maxMin=amin;
		}
		if(minMax>maxMin)return minMax-maxMin;//˵���н����������ཻ���ֳ���
		else return 0;//�����ʾû�н���
	}
	
	public float[] cal_over_all(float cx_min,float cy_min,float cz_min,float cx_max,float cy_max,float cz_max,
			float bx_min,float by_min,float bz_min,float bx_max,float by_max,float bz_max)
	{
		float xover=this.cal_over_one(cx_max,cx_min,bx_max,bx_min);
		float yover=this.cal_over_one(cy_max,cy_min,by_max,by_min);
		float zover=this.cal_over_one(cz_max, cz_min, bz_max, bz_min);
		return new float[] {xover,yover,zover};
	}
	
	
	

}
