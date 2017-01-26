package com.example.planefight3d;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class EnemyPlaneManager {
	
	public static ArrayList<enemyplane> enemyplanes=new ArrayList<enemyplane>();
	public static void drawself(GL10 gl)
	{
		for(int i=0;i<enemyplanes.size();i++)
		{
			gl.glPushMatrix();
			enemyplane tmp=enemyplanes.get(i);
			tmp.drawself(gl);
			gl.glPopMatrix();
		}
	}

}
