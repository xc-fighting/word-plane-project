package com.example.planefight3d;

import javax.microedition.khronos.opengles.GL10;


public class playermanager {
	

	public static player single_player;
	public playermanager(player single)
	{
	
		single_player=single;
	}
	
	
	
	public void drawself(GL10 gl)
	{
		if(player.isvisable==true)
		{
			gl.glPushMatrix();
			gl.glTranslatef(0f,0f,-4f);
			//gl.glRotatef(5,1,0,0);
		//	gl.glScalef(0.015f,0.015f,0.015f);
			single_player.drawself(gl);
			gl.glPopMatrix();
		}
	}
	

}
