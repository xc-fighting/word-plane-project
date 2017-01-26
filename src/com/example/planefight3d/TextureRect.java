package com.example.planefight3d;

import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

public class TextureRect {
	
	private FloatBuffer vertexs;//顶点缓冲区
	private FloatBuffer textures;//纹理缓冲区
	private int vcount;//顶点数量
	private int TexId;//纹理id
	//显示生命值的贴图块的位置
	private float xpos;
	private float ypos;
	private float zpos;
	
	public TextureRect(int texid,float width,float height)
	{
		this.TexId=texid;
		this.vcount=6;
		float[] vertex={
				(float) (-0.5*width),(float) (0.5*height),0,
				(float) (-0.5*width),(float)(-0.5*height),0,
				(float) (0.5*width),(float)(0.5*height),0,
				(float) (-0.5*width),(float)(-0.5*height),0,
				(float) (0.5*width),(float)(-0.5*height),0,
				(float) (0.5*width),(float)(0.5*height),0
		};
	
		this.vertexs=bufferutil.array2buffer(vertex);
		float []texs={
			0,0,
			0,1,
			1,0,
			0,1,
			1,1,
			1,0
		};
		this.textures=bufferutil.array2buffer(texs);
	}
	public void setxyz(float[] pos)
	{
		this.xpos=pos[0];
		this.ypos=pos[1];
		this.zpos=pos[2];
	}
	public float[] getxyz()
	{
		float[] pos=new float[3];
		pos[0]=this.xpos;
		pos[1]=this.ypos;
		pos[2]=this.zpos;
		return pos;
	}
	
	public void drawself(GL10 gl)
	{
		gl.glPushMatrix();
	
		
		gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glEnable(GL10.GL_TEXTURE_2D);
		
		gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
		gl.glTexCoordPointer(2,GL10.GL_FLOAT,0,textures);
		gl.glBindTexture(GL10.GL_TEXTURE_2D,TexId);
		gl.glVertexPointer(3,GL10.GL_FLOAT,0,vertexs);		
		gl.glDrawArrays(GL10.GL_TRIANGLES,0,vcount);
		gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
		gl.glDisableClientState(GL10.GL_TEXTURE_2D);
		
		gl.glPopMatrix();
	}

}
