package com.example.planefight3d;

import javax.microedition.khronos.opengles.GL10;

public class WordsBanner {
	
	private int texid;//ÎÆÀíid
	private float width;//Í¼Æ¬¿í¶È
	private TextureRect pic;//Í¼Æ¬ÎÆÀí¾ØÐÎ
	
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
