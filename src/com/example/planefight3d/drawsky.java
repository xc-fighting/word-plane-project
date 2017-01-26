package com.example.planefight3d;

import javax.microedition.khronos.opengles.GL10;

public class drawsky {
	
	cylinder sky;
	public drawsky(int id,int rows,int cols,float length,float r)
	{
		sky=new cylinder(id,rows,cols,length,r);
		sky.initAll();
	}
	public void drawself(GL10 gl)
	{
		sky.drawself(gl);
	}

}
