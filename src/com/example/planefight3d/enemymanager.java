package com.example.planefight3d;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class enemymanager {
	
	public static ArrayList<enemy> enemy_group=new ArrayList<enemy>();
	public static void drawself(GL10 gl)
	{
		for(int i=0;i<enemy_group.size();i++)
		{
			gl.glPushMatrix();
			enemy temp=enemy_group.get(i);
			temp.drawself(gl);
			gl.glPopMatrix();
			
		}
	}

}
