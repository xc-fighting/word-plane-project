package com.example.planefight3d;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class cylinder {
	
	private FloatBuffer vertexs;
	private FloatBuffer textures;
	private int vcount;
	private int texid;
	
	private float length;//Բ����ĳ���
	private float row_span;//��ÿһ��Ķ���
	private float col_span;//ÿһ��ĳ���
	private float radius;
	private int rows;
	private int cols;
	private float XAngle=0f;
	

	public cylinder(int id,int rows,int cols,float length,float r)
	{
		this.texid=id;
		this.rows=rows;
		this.cols=cols;
		this.length=length;
		this.row_span=(float) Math.toRadians((float)360/rows);
		this.col_span=(float)length/cols;
		this.radius=r;
		this.vcount=rows*cols*2*3;//�ܵĶ������
		
	}
	public void initAll()
	{
		float []vertexs=new float[vcount*3];
		int count=0;
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
			{
				//���һ���������
				vertexs[count++]=(float) ((-0.5*length)+j*col_span);//x
				vertexs[count++]=(float)(radius*Math.sin(i*row_span));//y
				vertexs[count++]=(float)(radius*Math.cos(i*row_span));//z
				//�ڶ����������
				vertexs[count++]=(float) ((-0.5*length)+j*col_span);//x
				vertexs[count++]=(float)(radius*Math.sin((i+1)*row_span));//y
				vertexs[count++]=(float)(radius*Math.cos((i+1)*row_span));//z
				//������������
				vertexs[count++]=(float) ((-0.5*length)+(j+1)*col_span);
				vertexs[count++]=(float)(radius*Math.sin(i*row_span));
				vertexs[count++]=(float)(radius*Math.cos(i*row_span));
				//�������������
				vertexs[count++]=(float) ((-0.5*length)+(j+1)*col_span);
				vertexs[count++]=(float)(radius*Math.sin(i*row_span));
				vertexs[count++]=(float)(radius*Math.cos(i*row_span));
				//�ڶ����������
				vertexs[count++]=(float) ((-0.5*length)+j*col_span);//x
				vertexs[count++]=(float)(radius*Math.sin((i+1)*row_span));//y
				vertexs[count++]=(float)(radius*Math.cos((i+1)*row_span));//z
				//���ĸ��������
				vertexs[count++]=(float) ((-0.5*length)+(j+1)*col_span);//x
				vertexs[count++]=(float)(radius*Math.sin((i+1)*row_span));//y
				vertexs[count++]=(float)(radius*Math.cos((i+1)*row_span));//z
			}
		ByteBuffer vbb=ByteBuffer.allocateDirect(count*4);
		vbb.order(ByteOrder.nativeOrder());
		this.vertexs=vbb.asFloatBuffer();
		this.vertexs.put(vertexs);
		this.vertexs.position(0);
		
		ByteBuffer tbb=ByteBuffer.allocateDirect(count*4);
		tbb.order(ByteOrder.nativeOrder());
		this.textures=tbb.asFloatBuffer();
		this.textures.put(this.init_texture(rows, cols));
		this.textures.position(0);
		
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
	
	public float[] init_texture(int rows,int cols)//����������
	{
		float row_span=(float)1/rows;
		float col_span=(float)1/cols;
		int counts=rows*cols*2*3*2;
		float[] texs=new float[counts];
		int count=0;
		for(int i=0;i<rows;i++)
			for(int j=0;j<cols;j++)
			{
				//��һ��������st
				texs[count++]=0+col_span*j;//s
				texs[count++]=0+row_span*i;//t
				//�ڶ���������st
				texs[count++]=0+col_span*j;
				texs[count++]=0+row_span*(i+1);
				//�������������
				texs[count++]=0+col_span*(j+1);//s
				texs[count++]=0+row_span*i;//t
				//�������������
				texs[count++]=0+col_span*(j+1);//s
				texs[count++]=0+row_span*i;//t
				//�ڶ���������st
				texs[count++]=0+col_span*j;
				texs[count++]=0+row_span*(i+1);
				//���ĸ��������st
				texs[count++]=0+col_span*(j+1);
				texs[count++]=0+row_span*(i+1);
			}
		return texs;
	}
	

}
