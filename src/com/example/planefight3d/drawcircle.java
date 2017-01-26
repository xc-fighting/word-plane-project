package com.example.planefight3d;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class drawcircle {
	
	private FloatBuffer vertexs;
	private FloatBuffer texs;
	private int texID;
	private int vcount;
	private float xpos;
	private float ypos;
	private float zpos;
	
	private int slices;//分的块数
	private float radius;//圆的半径
	private float slice_span;//每块的弧度值
	
	public drawcircle(int texid,int slice,float radius)
	{
		this.texID=texid;
		this.slices=slice;
		this.radius=radius;
		this.slice_span=(float)(Math.PI*2/slice);
		this.vcount=slice+1+1;//总的顶点数量
	}
	public void setposXYZ(float []pos)
	{
		this.xpos=pos[0];
		this.ypos=pos[1];
		this.zpos=pos[2];
	}
	public float[] getPosXYZ()
	{
		float[] pos=new float[3];
		pos[0]=this.xpos;
		pos[1]=this.ypos;
		pos[2]=this.zpos;
		return pos;
	}
	public void initAll()
	{
		float []vertex=new float [vcount*3];//顶点数量
		int count=0;
		//将圆心点坐标添加进来
		vertex[count++]=0f;
		vertex[count++]=0f;
		vertex[count++]=0f;
		for(int i=0;i<this.slices;i++)
		{
			vertex[count++]=(float) (this.radius*Math.cos(i*this.slice_span));
			vertex[count++]=(float) (this.radius*Math.sin(i*this.slice_span));
			vertex[count++]=0f;
			
		}
		vertex[count++]=this.radius;
		vertex[count++]=0f;
		vertex[count++]=0f;
		this.vertexs=bufferutil.array2buffer(vertex);
		this.texs=bufferutil.array2buffer(this.init_textures());
	}
	public float[] init_textures()
	{
		float[] texs=new float[vcount*2];
		int count=0;
		texs[count++]=0.5f;
		texs[count++]=0.5f;
		for(int i=0;i<this.slices;i++)
		{
			texs[count++]=(float)(0.5+0.5*Math.cos(i*slice_span));
			texs[count++]=(float)(0.5+0.5*Math.sin(slice_span));
			
		}
		texs[count++]=1f;
		texs[count++]=0.5f;
		return texs;
		
	}
	public void drawself(GL10 gl)
	{
		gl.glPushMatrix();
        gl.glTranslatef(xpos, ypos, zpos);
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,texs);
		gl.glBindTexture(GL10.GL_TEXTURE_2D,texID);
		gl.glVertexPointer(3,GL10.GL_FLOAT,0,vertexs);		
		gl.glDrawArrays(GL10.GL_TRIANGLE_FAN,0,vcount);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_2D);
		
		gl.glPopMatrix();
	}

}
