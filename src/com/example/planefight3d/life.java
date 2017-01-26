package com.example.planefight3d;

import javax.microedition.khronos.opengles.GL10;

public class life {
	
	private TextureRect rect;
	
	public life(int texid,float width,float height)
	{
		rect=new TextureRect(texid,width,height);
	}
	
	public void drawself(GL10 gl)
	{
		
			this.rect.drawself(gl);
		
	}
	
}
