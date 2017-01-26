package com.example.planefight3d;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class sphere {
	
	private FloatBuffer vertexs;
	private FloatBuffer textures;
	private int vcount;
	private int texid;
	
	
	private float row_span;//分每一块的度数
	private float col_span;//每一块的长度
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
		this.vcount=rows*cols*2*3;//顶点数量
	}
	
	public void initAll()
	{
		float []vertexs=new float[vcount*3];
		
		int count=0;
		for(int j=0;j<cols;j++)
		{
			float cur_col_angle=0+j*col_span;//当前列的弧度值
			float next_col_angle=0+(j+1)*col_span;//下一列的弧度值
			for(int i=0;i<rows;i++)
			{
				float cur_row_angle=0+i*row_span;//当前行的弧度值
				float next_row_angle=0+(i+1)*row_span;//下一行圆的弧度值
				
				float cur_radius=(float)(radius*Math.sin(cur_row_angle));//上一行圆的半径
				float next_radius=(float)(radius*Math.sin(next_row_angle));//下一行圆的半径
							
				
				//当前行当前列的点
				vertexs[count++]=(float)(cur_radius*Math.cos(cur_col_angle));
				vertexs[count++]=(float)(radius*Math.cos(cur_row_angle));
				vertexs[count++]=(float)(cur_radius*Math.sin(cur_col_angle));
				//当前行，下一列的点
				vertexs[count++]=(float)(cur_radius*Math.cos(next_col_angle));
				vertexs[count++]=(float)(radius*Math.cos(cur_row_angle));
				vertexs[count++]=(float)(cur_radius*Math.sin(next_col_angle));
				//下一行，下一列的点
				vertexs[count++]=(float)(next_radius*Math.cos(next_col_angle));
				vertexs[count++]=(float)(radius*Math.cos(next_row_angle));
				vertexs[count++]=(float)(next_radius*Math.sin(next_col_angle));
				//下一行，下一列的点
				vertexs[count++]=(float)(next_radius*Math.cos(next_col_angle));
				vertexs[count++]=(float)(radius*Math.cos(next_row_angle));
				vertexs[count++]=(float)(next_radius*Math.sin(next_col_angle));
				//下一行当前列的点
				vertexs[count++]=(float)(next_radius*Math.cos(cur_col_angle));
				vertexs[count++]=(float)(radius*Math.cos(next_row_angle));
				vertexs[count++]=(float)(next_radius*Math.sin(cur_col_angle));
				//当前行当前列的点
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
	
	public float[] init_texture(int rows,int cols)//行数和列数
	{
		float []textures=new float[rows*cols*2*3*2];
		int count=0;
		for(int j=0;j<cols;j++)
		{
			float cur_col_angle=0+j*col_span;//当前列的弧度值
			float next_col_angle=0+(j+1)*col_span;//下一列的弧度值
			for(int i=0;i<rows;i++)
			{
				float cur_row_angle=0+i*row_span;//当前行的弧度值
				float next_row_angle=0+(i+1)*row_span;//下一行圆的弧度值				
				
				//当前行当前列的点
				textures[count++]=(float)(cur_col_angle/Math.PI*2);
				textures[count++]=(float)(cur_row_angle/Math.PI);
				//当前行，下一列的点
				textures[count++]=(float)(next_col_angle/Math.PI*2);
				textures[count++]=(float)(cur_row_angle/Math.PI);
				//下一行，下一列的点
				textures[count++]=(float)(next_col_angle/Math.PI*2);
				textures[count++]=(float)(next_row_angle/Math.PI);
				//下一行，下一列的点
				textures[count++]=(float)(next_col_angle/Math.PI*2);
				textures[count++]=(float)(next_row_angle/Math.PI);
				//下一行当前列的点
				textures[count++]=(float)(cur_col_angle/Math.PI*2);
				textures[count++]=(float)(next_row_angle/Math.PI);
				//当前行当前列的点
				textures[count++]=(float)(cur_col_angle/Math.PI*2);
				textures[count++]=(float)(cur_row_angle/Math.PI);
				
				
			
				
			}
		}
		return textures;
		
	}
	

}
