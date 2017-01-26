package com.example.planefight3d;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class sphere {
	
	private FloatBuffer vertexs;
	private FloatBuffer textures;
	private int vcount;
	private int texid;
	
	
	private float row_span;//��ÿһ��Ķ���
	private float col_span;//ÿһ��ĳ���
	private float radius;
	private int rows;
	private int cols;
	
	public sphere(int id,int rows,int cols,float r)
	{
		this.texid=id;
		this.rows=rows;
		this.cols=cols;
		this.radius=r;
		this.row_span=(float) (Math.PI/rows);
		this.col_span=(float)(Math.PI*2/cols);
		this.vcount=rows*cols*2*3;//��������
	}
	
	public void initAll()
	{
		float []vertexs=new float[vcount*3];
		
		int count=0;
		for(int j=0;j<cols;j++)
		{
			float cur_col_angle=0+j*col_span;//��ǰ�еĻ���ֵ
			float next_col_angle=0+(j+1)*col_span;//��һ�еĻ���ֵ
			for(int i=0;i<rows;i++)
			{
				float cur_row_angle=0+i*row_span;//��ǰ�еĻ���ֵ
				float next_row_angle=0+(i+1)*row_span;//��һ��Բ�Ļ���ֵ
				
				float cur_radius=(float)(radius*Math.sin(cur_row_angle));//��һ��Բ�İ뾶
				float next_radius=(float)(radius*Math.sin(next_row_angle));//��һ��Բ�İ뾶
							
				
				//��ǰ�е�ǰ�еĵ�
				vertexs[count++]=(float)(cur_radius*Math.cos(cur_col_angle));
				vertexs[count++]=(float)(radius*Math.cos(cur_row_angle));
				vertexs[count++]=(float)(cur_radius*Math.sin(cur_col_angle));
				//��ǰ�У���һ�еĵ�
				vertexs[count++]=(float)(cur_radius*Math.cos(next_col_angle));
				vertexs[count++]=(float)(radius*Math.cos(cur_row_angle));
				vertexs[count++]=(float)(cur_radius*Math.sin(next_col_angle));
				//��һ�У���һ�еĵ�
				vertexs[count++]=(float)(next_radius*Math.cos(next_col_angle));
				vertexs[count++]=(float)(radius*Math.cos(next_row_angle));
				vertexs[count++]=(float)(next_radius*Math.sin(next_col_angle));
				//��һ�У���һ�еĵ�
				vertexs[count++]=(float)(next_radius*Math.cos(next_col_angle));
				vertexs[count++]=(float)(radius*Math.cos(next_row_angle));
				vertexs[count++]=(float)(next_radius*Math.sin(next_col_angle));
				//��һ�е�ǰ�еĵ�
				vertexs[count++]=(float)(next_radius*Math.cos(cur_col_angle));
				vertexs[count++]=(float)(radius*Math.cos(next_row_angle));
				vertexs[count++]=(float)(next_radius*Math.sin(cur_col_angle));
				//��ǰ�е�ǰ�еĵ�
				vertexs[count++]=(float)(cur_radius*Math.cos(cur_col_angle));
				vertexs[count++]=(float)(radius*Math.cos(cur_row_angle));
				vertexs[count++]=(float)(cur_radius*Math.sin(cur_col_angle));
				
						
				
			}
		}
		this.vertexs=bufferutil.array2buffer(vertexs);
		this.textures=bufferutil.array2buffer(this.init_texture(rows, cols));
	}
	
	public void drawself(GL10 gl)
	{
		gl.glPushMatrix();
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
		float []textures=new float[rows*cols*2*3*2];
		int count=0;
		for(int j=0;j<cols;j++)
		{
			float cur_col_angle=0+j*col_span;//��ǰ�еĻ���ֵ
			float next_col_angle=0+(j+1)*col_span;//��һ�еĻ���ֵ
			for(int i=0;i<rows;i++)
			{
				float cur_row_angle=0+i*row_span;//��ǰ�еĻ���ֵ
				float next_row_angle=0+(i+1)*row_span;//��һ��Բ�Ļ���ֵ				
				
				//��ǰ�е�ǰ�еĵ�
				textures[count++]=(float)(cur_col_angle/Math.PI*2);
				textures[count++]=(float)(cur_row_angle/Math.PI);
				//��ǰ�У���һ�еĵ�
				textures[count++]=(float)(next_col_angle/Math.PI*2);
				textures[count++]=(float)(cur_row_angle/Math.PI);
				//��һ�У���һ�еĵ�
				textures[count++]=(float)(next_col_angle/Math.PI*2);
				textures[count++]=(float)(next_row_angle/Math.PI);
				//��һ�У���һ�еĵ�
				textures[count++]=(float)(next_col_angle/Math.PI*2);
				textures[count++]=(float)(next_row_angle/Math.PI);
				//��һ�е�ǰ�еĵ�
				textures[count++]=(float)(cur_col_angle/Math.PI*2);
				textures[count++]=(float)(next_row_angle/Math.PI);
				//��ǰ�е�ǰ�еĵ�
				textures[count++]=(float)(cur_col_angle/Math.PI*2);
				textures[count++]=(float)(cur_row_angle/Math.PI);
				
				
			
				
			}
		}
		return textures;
		
	}
	

}
