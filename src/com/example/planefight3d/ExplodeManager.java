package com.example.planefight3d;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

public class ExplodeManager {
	
	public static ArrayList<Explode> explode_list=new ArrayList<Explode>();
	
	public static void drawself(GL10 gl)
	{
		for(int i=0;i<explode_list.size();i++)
		{
			gl.glPushMatrix();
			Explode tmp=explode_list.get(i);
			tmp.drawself(gl);
			gl.glPopMatrix();
		}
	}

}
