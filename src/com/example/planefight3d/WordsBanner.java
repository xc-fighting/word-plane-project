package com.example.planefight3d;

import javax.microedition.khronos.opengles.GL10;

public class WordsBanner {
	
	private int texid;//����id
	private float width;//ͼƬ���
	private TextureRect pic;//ͼƬ�������
	
	public WordsBanner(int id,float width)
	{
		this.texid=id;
		this.width=width;
		this.pic=new TextureRect(this.texid,this.width,this.width);
	}
	public void drawself(GL10 gl)
	{
		gl.glPushMatrix();
		this.pic.drawself(gl);
		gl.glPopMatrix();
	}

}
