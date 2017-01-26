package com.example.planefight3d;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class drawland {
	
	private FloatBuffer vertexs;
	private FloatBuffer textures;
	private int texid;
	private int vcount;
	private float length;
	private float radius;
	private int rows;
	private int cols;
	private float row_span;
	private float col_span;
	
	private float[][] yarrays;
	private float XAngle;
	
	public drawland(int rows,int cols,int texid,float length,float radius,float[][]yarray)
	{
		this.rows=rows;
		this.cols=cols;
		this.texid=texid;
		this.length=length;
		this.radius=radius;
		this.yarrays=yarray;
		this.vcount=rows*cols*2*3;
		this.row_span=(float)(Math.PI*2/rows);
		this.col_span=(float)(this.length/cols);
		
	}
	
	
	
	//仿照案例代码
	public void initAll()
	{
		float []vertexs=new float[vcount*3];
		int count=0;
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
			{
				//求第一个点的坐标
				vertexs[count++]=(float) ((-0.5*length)+j*col_span);//x
				vertexs[count++]=(float)((radius+yarrays[i][j])*Math.sin(i*row_span));//y
				vertexs[count++]=(float)((radius+yarrays[i][j])*Math.cos(i*row_span));//z
				//第二个点的坐标
				vertexs[count++]=(float) ((-0.5*length)+j*col_span);//x
				vertexs[count++]=(float)((radius+yarrays[i+1][j])*Math.sin((i+1)*row_span));//y
				vertexs[count++]=(float)((radius+yarrays[i+1][j])*Math.cos((i+1)*row_span));//z
				//第三个点坐标
				vertexs[count++]=(float) ((-0.5*length)+(j+1)*col_span);
				vertexs[count++]=(float)((radius+yarrays[i][j+1])*Math.sin(i*row_span));
				vertexs[count++]=(float)((radius+yarrays[i][j+1])*Math.cos(i*row_span));
				//第三个点的坐标
				vertexs[count++]=(float) ((-0.5*length)+(j+1)*col_span);
				vertexs[count++]=(float)((radius+yarrays[i][j+1])*Math.sin(i*row_span));
				vertexs[count++]=(float)((radius+yarrays[i][j+1])*Math.cos(i*row_span));
				//第二个点的坐标
				vertexs[count++]=(float) ((-0.5*length)+j*col_span);//x
				vertexs[count++]=(float)((radius+yarrays[i+1][j])*Math.sin((i+1)*row_span));//y
				vertexs[count++]=(float)((radius+yarrays[i+1][j])*Math.cos((i+1)*row_span));//z
				//第四个点的坐标
				vertexs[count++]=(float) ((-0.5*length)+(j+1)*col_span);//x
				vertexs[count++]=(float)((radius+yarrays[i+1][j+1])*Math.sin((i+1)*row_span));//y
				vertexs[count++]=(float)((radius+yarrays[i+1][j+1])*Math.cos((i+1)*row_span));//z
	}
	this.vertexs=bufferutil.array2buffer(vertexs);
	this.textures=bufferutil.array2buffer(this.init_textures());
		
		
	}
	
	
	public float[] init_textures()
	{
		float row_span=(float)1/rows;
		float col_span=(float)1/cols;
		int counts=rows*cols*2*3*2;
		float[] texs=new float[counts];
		int count=0;
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
			{
				//第一个坐标点的st
				texs[count++]=0+col_span*j;//s
				texs[count++]=0+row_span*i;//t
				//第二个坐标点的st
				texs[count++]=0+col_span*j;
				texs[count++]=0+row_span*(i+1);
				//第三个点的坐标
				texs[count++]=0+col_span*(j+1);//s
				texs[count++]=0+row_span*i;//t
				//第三个点的坐标
				texs[count++]=0+col_span*(j+1);//s
				texs[count++]=0+row_span*i;//t
				//第二个坐标点的st
				texs[count++]=0+col_span*j;
				texs[count++]=0+row_span*(i+1);
				//第四个点的坐标st
				texs[count++]=0+col_span*(j+1);
				texs[count++]=0+row_span*(i+1);
			}
		return texs;
	}
	
	public void drawself(GL10 gl)
	{
		gl.glPushMatrix();
		gl.glRotatef(XAngle, 1,0,0);
		XAngle+=0.05f;
		if(XAngle>=360f)XAngle=0f;
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
	
	
	

}
