package com.example.planefight3d;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;


public class bulletmanager {
	
	public static ArrayList<bullet> player_bullets=new ArrayList<bullet>();
	public static ArrayList<bullet> enemy_bullets=new ArrayList<bullet>();
	
	
	public static void drawself(GL10 gl)
	{
		for(int i=0;i<player_bullets.size();i++)
		{
			gl.glPushMatrix();
			bullet b=player_bullets.get(i);
			b.drawself(gl);
			gl.glPopMatrix();
		}
		for(int i=0;i<enemy_bullets.size();i++)
		{
			gl.glPushMatrix();
			bullet bomb=enemy_bullets.get(i);
			bomb.drawself(gl);
			gl.glPopMatrix();
		}
	}
	
	
	

}
